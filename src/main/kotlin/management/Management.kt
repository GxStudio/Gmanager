package cc.gxstudio.gmanager.management

import cc.gxstudio.gmanager.command.checkGroupExist
import cc.gxstudio.gmanager.command.sendGroupOrOtherMessage
import cc.gxstudio.gmanager.config.getGConfig
import cc.gxstudio.gmanager.data.getGData
import cc.gxstudio.gmanager.extension.*
import cc.gxstudio.gmanager.logutil.Log
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.data.UserProfile.Sex.*
import net.mamoe.mirai.message.data.MessageSource.Key.recall
import net.mamoe.mirai.message.data.QuoteReply

object Management {
    suspend fun CommandSender.kickMember(member: NormalMember, reason: String?) {
        var packageReason = ""
        if (reason == null) {
            packageReason = ""//todo:默认踢出原因.
        }
        member.kick(packageReason)
        sendGroupOrOtherMessage("已踢出${member.nameCardOrNick},原因：${reason}")//todo:自定义内容
        //todo:踢出在群内发布信息（判断）
    }
    
    suspend fun CommandSender.cleanScreen() {
        if (groupExist()) return
        sendGroupOrOtherMessage(
            this.getGroupOrNull()!!.getGConfig().groupHintMsg.sendInGroupMsg.ClearScreenMessage
                               )//todo:清屏内容
    }
    
    suspend fun CommandSender.muteMember(member: NormalMember, time: Int) {
        member.mute(time)
        //todo:禁言在群内发送消息
        if (time == 0) {
            sendGroupOrOtherMessage("已解除${member.nameCardOrNick}的禁言")
        } else {
            sendGroupOrOtherMessage("已禁言${member.nameCardOrNick}，时长${time}秒")
        }
        
    }
    
    suspend fun CommandSender.openManage(group: Group? = this.getGroupOrNull()) {
        if (checkGroupExist(group)) {
            if (!group!!.manageEnabled()) sendGroupOrOtherMessage("群管已处于开启状态，无需再次开启。")
            else {
                group.enableManage()
                sendGroupOrOtherMessage("群管已开启")
            }
        }
    }
    
    suspend fun CommandSender.closeManage(group: Group?) {
        if (checkGroupExist(group)) {
            //todo:完成群管关闭指令
            if (!group!!.manageEnabled()) sendGroupOrOtherMessage("群管已处于关闭状态，无需再次关闭。")
            else {
                group.disableManage()
                sendGroupOrOtherMessage("此群群管已关闭")
            }
        }
    }
    
    suspend fun CommandSender.muteAll(group: Group?) {
        if (checkGroupExist(group)) {
            group!!.settings.isMuteAll = true
        }
    }
    
    suspend fun CommandContext.recallmsg() {
        if (checkGroupExist()) {
            for (msg in originalMessage) if (msg is QuoteReply) {
                this.sender.subject as Group
                msg.source.recall()
                sender.sendGroupOrOtherMessage("已撤回消息")
                //todo:完成撤回消息通知管理
                return
            }
        }
    }
    
    suspend fun CommandSender.recallRecentMsg(num: Int) {
        if (checkGroupExist()) {
            val group = this.getGroupOrNull()!!
            val messageList = group.getGData().messageEventList
            if (messageList.size == 0) {
                sendGroupOrOtherMessage("最近没有消息发送。")
                return
            }
            var count = 0
            Log.e(messageList.reversed().size)
            Log.e(messageList.size.toString())
            for (msg in messageList.reversed()) {
                if (count == num) break
                if (msg.recall()) {
                    count += 1
                }
            }
            sendGroupOrOtherMessage("已撤回最近 $num 条消息。")
        }
    }
    
    suspend fun CommandSender.changeNameCard(member: NormalMember, nameCard: String) {
        //todo:完成修改群名片
        val group = member.group
        if (checkGroupExist(group)) {
            if (member.group.botPermission == MemberPermission.MEMBER) {
                sendGroupOrOtherMessage("机器人权限不足，无法修改群名片。")
                return
            }
            member.nameCard = nameCard
            sendGroupOrOtherMessage("已修改${member.nameCardOrNick}的群名片为$nameCard")
        }
    }
    
    suspend fun CommandSender.changeCorrectNameCard(member: NormalMember) {
        if (checkGroupExist()) {//todo:完成修改群名片
            val group = member.group
            val nickConfig = group.getGConfig().groupMemberAutoNick
            val nickFormat = nickConfig.nickFormartSettings.formartText
            val formatSetting = nickConfig.nickFormartSettings
            if (member.group.botPermission == MemberPermission.MEMBER) {
                sendGroupOrOtherMessage("机器人权限不足，无法修改群名片。")
                return
            }
            val nickText = nickFormat.apply {
                replace("{name}", member.nick)
                val profile = member.queryProfile()
                replace(
                    "{sex}", when (profile.sex) {
                        MALE    -> formatSetting.manName
                        FEMALE  -> formatSetting.womanName
                        UNKNOWN -> formatSetting.sexLessReplace
                    }
                       )
                replace("{age}", profile.age.toString())
                
                if (formatSetting.useRemarkInfo) {
                    //     replace("{reason}", formatSetting.useRemarkInfo)
                }
                replace("{email}", profile.email)
                replace("{sign}", profile.sign)
                replace("{qlevel}", profile.qLevel.toString())
            }
            member.nameCard = nickText
            sendGroupOrOtherMessage("已完成群名片纠正")
        }
    }
    
    suspend fun CommandSender.findRepeatMembers() {
        if (checkGroupExist()) {
            val group = this.getGroupOrNull()!!
            val checkGroupList =
                group.getGConfig().groupKickAndMuteAction.kickOverlapJoin.checkGroupList
            val thisGroupMembersIds = group.members.map { it.id }
            val repeatMembers = mutableListOf<NormalMember>()
            
            val notJoinGroupList = mutableListOf<Long>()
            
            for (checkGroupId in checkGroupList) {
                val checkGroup = getGroup(checkGroupId)
                if (checkGroup == null) {
                    notJoinGroupList.add(checkGroupId)
                    continue
                }
                val memberIds = checkGroup.members.map { it.id }
                for (member in memberIds) {
                    if (thisGroupMembersIds.contains(member)) {
                        repeatMembers.add(group[member]!!)
                    }
                }
            }
            sendGroupOrOtherMessage(
                """检测到重复群员：${repeatMembers.joinToString { it.nameCardOrNick }}
                |未加入的群：${notJoinGroupList.joinToString { it.toString() }}
            """.trimMargin()
                                   )
            
        }
    }
    
    
    suspend fun CommandSender.clearGroupWarn(){
        if (checkGroupExist()) {
            val group = this.getGroupOrNull()!!
            group.members.onEach { it.warnTimes = 0 }
            sendGroupOrOtherMessage("已清空本群警告列表")
        }
    }
    suspend fun CommandSender.clearWarn(member:NormalMember){
        if (checkGroupExist()) {
            member.warnTimes = 0
        }
    }
}
