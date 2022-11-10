package cc.gxstudio.gmanager.management

import cc.gxstudio.gmanager.command.checkGroupExist
import cc.gxstudio.gmanager.command.sendGroupOrOtherMessage
import cc.gxstudio.gmanager.config.getGConfig
import cc.gxstudio.gmanager.data.getGData
import cc.gxstudio.gmanager.extension.disableManage
import cc.gxstudio.gmanager.extension.enableManage
import cc.gxstudio.gmanager.extension.groupExist
import cc.gxstudio.gmanager.extension.manageEnabled
import cc.gxstudio.gmanager.logutil.Log
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.contact.nameCardOrNick
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
        if (checkGroupExist()){
        for (msg in originalMessage) if (msg is QuoteReply) {
            this.sender.subject as Group
            msg.source.recall()
            sender.sendGroupOrOtherMessage("已撤回消息")
            //todo:完成撤回消息通知管理
            return
        }}
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
}