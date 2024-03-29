package cc.gxstudio.gmanager.command

import cc.gxstudio.gmanager.PluginMain
import cc.gxstudio.gmanager.extension.dontHasNormalCommandPermission
import cc.gxstudio.gmanager.extension.getAllJoinedGroups
import cc.gxstudio.gmanager.extension.warn
import cc.gxstudio.gmanager.http.PostUtil
import cc.gxstudio.gmanager.logutil.Log
import cc.gxstudio.gmanager.management.Management.changeCorrectNameCard
import cc.gxstudio.gmanager.management.Management.changeNameCard
import cc.gxstudio.gmanager.management.Management.cleanScreen
import cc.gxstudio.gmanager.management.Management.clearGroupWarn
import cc.gxstudio.gmanager.management.Management.clearWarn
import cc.gxstudio.gmanager.management.Management.closeManage
import cc.gxstudio.gmanager.management.Management.findRepeatMembers
import cc.gxstudio.gmanager.management.Management.kickMember
import cc.gxstudio.gmanager.management.Management.muteAll
import cc.gxstudio.gmanager.management.Management.muteMember
import cc.gxstudio.gmanager.management.Management.openManage
import cc.gxstudio.gmanager.management.Management.recallRecentMsg
import cc.gxstudio.gmanager.management.Management.recallmsg
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.contact.announcement.Announcement.Companion.publishAnnouncement
import net.mamoe.mirai.contact.announcement.AnnouncementImage
import net.mamoe.mirai.contact.announcement.AnnouncementParametersBuilder
import net.mamoe.mirai.message.data.AtAll
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource


/**
 * 规定：[Commands]中所有指令的[SimpleCommand.primaryName]需与权限名相同
 * */
object Commands {
    //TODO:如果需要输入空格在参数使用"/ "
    val COMMAND_LIST: MutableList<Command>
        get() {
            val mlist = mutableListOf<Command>(
                MuteCommand(),
                UnMuteCommand(),
                CleanCommand(),
                KickCommand(),
                OpenManagerCommand(),
                OffManagerCommand(),
                AtAllCommand(),
                
                RecallMessageCommand(),
                changeMemberNickCommand(), MuteAllCommand()
                //SendAnnouncementCommand()
                //SendGroupMessageCommand()
                , RecallMessageRecentCommand(),
                CheckRepeatMemberCommand()
                                              )
            mlist.addAll(COMMAND_LIST_GLOBAL)
            return mlist
        }
    
    val COMMAND_LIST_GLOBAL: List<Command> = listOf()
    fun regCommand() {
        for (command in COMMAND_LIST) {
            CommandManager.registerCommand(command)
        }
    }
    
    //禁言操作
    class MuteCommand : SimpleCommand(
        PluginMain, "mute", description = "基础指令-禁言群成员"
                                     ) {
        @Handler
        suspend fun CommandSender.onCommand(target: NormalMember, duration: Int) {
            
            if (dontHasNormalCommandPermission(this@MuteCommand, target.group)) return
            muteMember(target, duration)
            
        }
    }
    
    class UnMuteCommand : SimpleCommand(
        PluginMain, "unmute",
        description = "基础指令-解除禁言"
                                       ) {
        @Handler
        suspend fun CommandSender.onCommand(target: NormalMember) {
            if (dontHasNormalCommandPermission(this@UnMuteCommand, target.group)) return
            muteMember(target, 0)
        }
    }
    
    class MuteAllCommand : SimpleCommand(
        PluginMain, "muteall",
        description = "基础指令-全群禁言"
                                        ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = getGroupOrNull()) {//todo:完成修改全群禁言
            if (dontHasNormalCommandPermission(this@MuteAllCommand, group)) return
            muteAll(group)
        }
    }
    
    //踢出成员
    class KickCommand : SimpleCommand(
        PluginMain, "kick",
        description = "基础指令-踢出群成员"
                                     ) {
        @Handler
        suspend fun CommandSender.onCommand(target: NormalMember, reason: String? = null) {
            if (dontHasNormalCommandPermission(this@KickCommand, target.group)) return
            kickMember(target, reason)
            
        }
    }
    
    //发送消息类
    class AtAllCommand : SimpleCommand(
        PluginMain, "atall",
        description = "基础指令-at全体成员"
                                      ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull(), text: String = "") {
            if (dontHasNormalCommandPermission(this@AtAllCommand, group)) return
            group!!.sendMessage(AtAll + text)
        }
    }
    
    class CleanCommand : SimpleCommand(
        PluginMain, "clean",
        description = "基础指令-清屏"
                                      ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@CleanCommand, group)) return
            cleanScreen()
        }
    }
    
    class OpenManagerCommand : SimpleCommand(
        PluginMain, "manageon",
        description = "基础指令-开启群管理"
                                            ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@OpenManagerCommand, group)) return
            openManage(group)
        }
        
    }
    
    class OffManagerCommand : SimpleCommand(
        PluginMain, "manageoff",
        description = "基础指令-关闭群管理"
                                           ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@OffManagerCommand, group)) return
            closeManage(group)
        }
    }
    
    
    //权限查询
    class CheckCommand : SimpleCommand(
        PluginMain, "check",
        description = "基础指令-查询自己的权限"
                                      ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@CheckCommand, group)) return
            //todo:查询权限
        }
    }
    
    //撤回消息
    class RecallMessageCommand : RawCommand(
        PluginMain, "recall",
        description = "基础指令-撤回指定消息",
        usage = "[回复的消息] /recall"
                                           ) {
        
        override suspend fun CommandContext.onCommand(args: MessageChain) {//TODO:完成撤回消息参数
            val group = this.sender.getGroupOrNull()
            if (this.sender.dontHasNormalCommandPermission(this@RecallMessageCommand, group)) return
            recallmsg()
        }
    }
    
    class RecallMessageRecentCommand : SimpleCommand(
        PluginMain, "recallrecent",
        description = "基础指令-撤回最近的N条消息"
                                                    ) {
        @Handler
        suspend fun CommandSender.onCommand(num: Int, group: Group? = this.getGroupOrNull()) {//TODO:完成撤回最近消息参数
            if (dontHasNormalCommandPermission(this@RecallMessageRecentCommand, group)) return
            recallRecentMsg(num)
        }
    }
    
    //改名指令
    class ChangeAllNickCommand : SimpleCommand(
        PluginMain, "changeallnick",
        description = "基础指令-一键改名"
                                              ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@ChangeAllNickCommand, group)) return
            //TODO:完成修改群名片
        }
    }
    
    class stopChangeAllNickCommand : SimpleCommand(
        PluginMain, "stopChangeAllNick",
        description = "基础指令-停止一键改名"
                                                  ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@stopChangeAllNickCommand, group)) return
            //TODO:完成停止修改群名片
        }
    }
    
    class changeMemberNickCommand() : SimpleCommand(
        PluginMain, "changeNick",
        description = "基础指令-修改指定群员名片"
                                                   ) {
        @Handler
        suspend fun CommandSender.onCommand(
            user: NormalMember,
            nick: String,
            
            group: Group? = this.getGroupOrNull()
                                           ) {//TODO：完成参数
            if (dontHasNormalCommandPermission(this@changeMemberNickCommand, group)) return
            changeNameCard(user, nick)
        }
    }
    
    class changeCorrectNickCommand() :
        SimpleCommand(PluginMain, "changecorrectnick", description = "基础指令-格式化指定群员名片") {
        @Handler
        suspend fun CommandSender.onCommand(user: NormalMember, group: Group? = this.getGroupOrNull()) {//TODO：完成参数
            if (dontHasNormalCommandPermission(this@changeCorrectNickCommand, group)) return
            changeCorrectNameCard(user)
        }
    }
    
    //检测/筛选群员
    class CheckSilenceMemberCommand : SimpleCommand(
        PluginMain, "checksilencemember",
        description = "基础指令-检查潜水(不发言)的群员"
                                                   ) {
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {//TODO：完成参数
            if (dontHasNormalCommandPermission(this@CheckSilenceMemberCommand, group)) return
            //TODO:完成检测潜水群员
            //mirai未实现，暂不支持
        }
    }
    
    class CheckRepeatMemberCommand : SimpleCommand(
        PluginMain, "checkrepeatmember",
        description = "基础指令-检查多个群之间重复的群员"
                                                  ) {
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {//TODO：完成参数
            if (dontHasNormalCommandPermission(this@CheckRepeatMemberCommand, group)) return
            findRepeatMembers()
        }
    }
    
    //筛选组操作
    class ChooseToKickCommand : SimpleCommand(
        PluginMain, "choosetokick",
        description = "基础指令-踢出指定筛选组成员"
                                             ) {
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {//TODO：完成参数
            if (dontHasNormalCommandPermission(this@ChooseToKickCommand, group)) return
            //TODO:完成踢出筛选组成员
            
        }
    }
    
    class ChooseToAtCommand : SimpleCommand(
        PluginMain, "choosetoat",
        description = "基础指令-At指定筛选组成员"
                                           ) {
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {//TODO：完成参数
            if (dontHasNormalCommandPermission(this@ChooseToAtCommand, group)) return
            ///TODO:完成At筛选组成员
            
        }
    }
    
    class ChooseToKickAndBlacklistCommand : SimpleCommand(
        PluginMain, "choosetokickandBlacklist",
        description = "基础指令-拉黑指定筛选组成员"
                                                         ) {
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {//TODO：完成参数
            if (dontHasNormalCommandPermission(this@ChooseToKickAndBlacklistCommand, group)) return
            //TODO:完成拉黑筛选组成员
            
        }
    }
    
    //警告功能
    class WarnCommand : SimpleCommand(
        PluginMain, "warn",
        description = "基础指令-警告群成员"
                                     ) {
        @Handler
        suspend fun CommandSender.onCommand(member: NormalMember, group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@WarnCommand, group)) return
            member.warn()
            //完成警告
            
        }
    }
    
    class ClearMemberWarnCommand : SimpleCommand(
        PluginMain, "clrmemberwarn",
        description = "基础指令-清除群成员警告"
                                                ) {
        @Handler
        suspend fun CommandSender.onCommand(member :NormalMember,group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@ClearMemberWarnCommand, group)) return
            clearWarn(member)
            
        }
    }
    
    class ClearGroupWarnCommand : SimpleCommand(
        PluginMain, "clrgroupwarn",
        description = "基础指令-清除群所有成员警告"
                                               ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@ClearGroupWarnCommand, group)) return
            clearGroupWarn()
            
        }
    }
    
    //投票执行
    class VoteKickCommand : SimpleCommand(
        PluginMain, "votekick",
        description = "基础指令-投票踢人"
                                         ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@VoteKickCommand, group)) return
            //TODO:完成投票踢人
            
        }
    }
    
    
    class VoteMuteCommand : SimpleCommand(
        PluginMain, "votemute",
        description = "基础指令-清除群所有成员警告"
                                         ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@VoteMuteCommand, group)) return
            //TODO:完成投票禁言
            
            
        }
    }
    
    class AgreeVoteCommand : SimpleCommand(
        PluginMain, "agreevote",
        description = "基础指令-赞成投票"
                                          ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@AgreeVoteCommand, group)) return
            //TODO:完成赞成投票
        }
    }
    
    class DeleteVoteCommand() : SimpleCommand(
        PluginMain, "deletevotecommand", description = "基础指令-删除投票"
                                             ) {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@DeleteVoteCommand, group)) return
            //TODO:完成删除投票
        }
    }
    
    
    //有问题未定义完整的指令
    class SendAnnouncementCommand : SimpleCommand(
        PluginMain, "sendAnnouncement",
        description = "基础指令-发送群公告"
                                                 ) {
        @Handler
        suspend fun CommandSender.onCommand(
            group: Group? = getGroupOrNull(),
            text: String,
            
            isPinned: Boolean = false,
            sendToNewMember: Boolean = false,
            showEditCard: Boolean = false,
            showPopup: Boolean = false,
            requireConfirmation: Boolean = false,
            pic: Image? = null
        
                                           ) {
            if (dontHasNormalCommandPermission(this@SendAnnouncementCommand, group)) return
            
            runBlocking {
                val url: String? = async {
                    if (pic == null) return@async null else {
                        return@async pic.queryUrl()
                    }
                }.await()
                var uploadedimage: AnnouncementImage? = null
                if (url != null) {
                    uploadedimage = group!!.announcements.uploadImage(
                        PostUtil().build { url(url) }.toByteArray().toExternalResource()
                                                                     )
                }
                
                group!!.publishAnnouncement(text, AnnouncementParametersBuilder().run {
                    if (uploadedimage != null) {
                        image(uploadedimage)
                    }
                    isPinned(isPinned)
                    sendToNewMember(sendToNewMember)
                    showEditCard(showEditCard)
                    showPopup(showPopup)
                    requireConfirmation(requireConfirmation)
                    build()
                })
            }
            
        }
        
    }
    
    class SetMemberTitleCommand() :
        SimpleCommand(PluginMain, "setmembertitle", description = "基础指令-设置群成员头衔") {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@SetMemberTitleCommand, group)) return
            
        }
        
    }
    
    class DeleteMemberTitleCommand() :
        SimpleCommand(PluginMain, "deletemembertitle", description = "基础指令-删除群成员头衔") {
        @Handler
        suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
            if (dontHasNormalCommandPermission(this@DeleteMemberTitleCommand, group)) return
            
        }
        
    }
    
    
    class PermissionCommands() // 完成LP的简单设置处理指令
    
    class ReviewListCommands() {
        // 完成黑白名单的简单设置处理指令
        //全局黑白审查名单
        class GlobalReviewListCommands() {
            class GlobalBlackListCommand() :
                SimpleCommand(PluginMain, "globalblacklist", description = "基础指令-全局黑名单") {
                @Handler
                
                suspend fun CommandSender.onCommand(group: Group? = this.getGroupOrNull()) {
                    if (dontHasNormalCommandPermission(this@GlobalBlackListCommand, group)) return
                    
                }
            }
            
            class GlobalWhiteListCommand() :
                SimpleCommand(PluginMain, "globalwhitelist", description = "基础指令-全局白名单") {
                //todo:修改为子指令合集
            }
        }
        
        //群黑白审查名单
        class GroupReviewListCommands() {
            class GroupBlackListCommand() :
                SimpleCommand(PluginMain, "groupblacklist", description = "基础指令-群黑名单") {
                
            }
            
            class GroupWhiteListCommand() :
                SimpleCommand(PluginMain, "groupwhitelist", description = "基础指令-群白名单") {
                
            }
        }
    }
    
    class ReviewTriggerCommands() {//黑白名单相关触发操作
    
    }
    
}


public suspend fun CommandSender.sendGroupOrOtherMessage(message: String, group: Group? = getGroupOrNull()) {
    if (groupNotExist(group)) sendMessage(message) else group!!.sendMessage(message)
}

/**检查指令参数是否错误提供群组
 * 一般用于检查私聊/控制台使用指令时是否提供群号
 * @see groupExist
 * */
fun groupNotExist(group: Group?): Boolean = !groupExist(group)

/**检查指令参数是否正确提供群组
 *
 * 一般用于检查私聊/控制台使用指令时是否提供群号
 *@sample Commands.OpenManagerCommand
 *  */


suspend fun CommandSender.checkGroupExist(group: Group? = getGroupOrNull()): Boolean {
    return if (groupExist(group)) true else {
        sendGroupOrOtherMessage("群号提供错误。")
        false
    }
}

suspend fun CommandContext.checkGroupExist(group: Group? = sender.subject as Group): Boolean {
    return if (groupExist(group)) true else {
        sender.subject!!.sendMessage("群号提供错误。")
        false
    }
}


fun groupExist(group: Group?): Boolean =
    if (group != null) {
        if (joinedGroup(group)) {
            Log.i("群组已加入且不为空")
            true
        } else {
            Log.i("群组未加入且不为空")
            false
        }
    } else false


fun joinedGroup(group: Group): Boolean =
    getAllJoinedGroups().contains(group)
/*
object AtParser : CommandValueArgumentParser<Array<NormalMember>> {
    override fun parse(raw: String, sender: CommandSender): Array<NormalMember> {
        Log.i(raw)
        //return raw == "TRUE!"
        return arrayOf()
    }
    
    override fun parse(
        raw: MessageContent,
        sender: CommandSender
                      ): Array<NormalMember> {
        Log.i(raw.contentToString())
        return super.parse(raw, sender)
    }
}*/
