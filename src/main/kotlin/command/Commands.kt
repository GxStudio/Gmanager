package cc.gxstudio.gmanager.command

import cc.gxstudio.gmanager.PluginMain
import cc.gxstudio.gmanager.extension.dontHasNormalCommandPermission
import cc.gxstudio.gmanager.http.PostUtil
import cc.gxstudio.gmanager.management.Management
import cc.gxstudio.gmanager.management.Management.cleanScreen
import cc.gxstudio.gmanager.management.Management.closeManage
import cc.gxstudio.gmanager.management.Management.openManage
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.contact.announcement.Announcement.Companion.publishAnnouncement
import net.mamoe.mirai.contact.announcement.AnnouncementImage
import net.mamoe.mirai.contact.announcement.AnnouncementParametersBuilder
import net.mamoe.mirai.contact.nameCardOrNick
import net.mamoe.mirai.message.data.AtAll
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.queryUrl
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
                CleanCommand(),
                KickCommand(),
                OpenManagerCommand(),
                OffManagerCommand(),
                AtAllCommand(),
                CheckCommand(),
                SendAnnouncementCommand()
                //SendGroupMessageCommand()
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
    
    
    class MuteCommand : SimpleCommand(
        PluginMain, "mute",
        description = "基础指令-禁言群成员"
                                     ) {
        @Handler
        suspend fun CommandSender.onCommand(target: NormalMember, duration: Int) {
            
            if (dontHasNormalCommandPermission(this@MuteCommand, target.group)) return
            Management.muteMember(target, duration)
            sendGroupOrOtherMessage("已禁言${duration}秒")
        }
    }
    
    class KickCommand : SimpleCommand(
        PluginMain, "kick",
        description = "基础指令-踢出群成员"
                                     ) {
        @Handler
        suspend fun CommandSender.onCommand(target: NormalMember, reason: String? = null) {
            if (dontHasNormalCommandPermission(this@KickCommand, target.group)) return
            Management.kickMember(target, reason)
            sendGroupOrOtherMessage("已踢出${target.nameCardOrNick},原因：${reason}")//todo:自定义内容
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
    
    class SendAnnouncementCommand : SimpleCommand(
        PluginMain, "sendAnnouncement",
        description = "基础指令-发送群公告"
                                                 ) {
        @Handler
        suspend fun CommandSender.onCommand(
            group: Group? = getGroupOrNull(),//test
            text: String,
            
            isPinned: Boolean = false,
            sendToNewMember: Boolean = false,
            showEditCard: Boolean = false,
            showPopup: Boolean = false,
            requireConfirmation: Boolean = false, pic: Image? = null
        
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
                        PostUtil().build { url(url) }.toByteArray()
                            .toExternalResource()
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
    
    
}


suspend fun CommandSender.sendGroupOrOtherMessage(message: String, group: Group? = getGroupOrNull()) {
    if (Commands.varOfGroupNotExist(group)) sendMessage(message) else group!!.sendMessage(message)
}

/**检查指令参数是否错误提供群组
 * 一般用于检查私聊/控制台使用指令时是否提供群号
 * @see Commands.varOfGroupExist
 * */
fun Commands.varOfGroupNotExist(group: Group?): Boolean = !varOfGroupExist(group)

/**检查指令参数是否正确提供群组
 *
 * 一般用于检查私聊/控制台使用指令时是否提供群号
 *@sample Commands.OpenManagerCommand
 *  */
fun Commands.varOfGroupExist(group: Group?): Boolean = (group != null)

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
