package cc.gxstudio.gmanager.command

import cc.gxstudio.gmanager.PluginMain
import cc.gxstudio.gmanager.extension.dontHasCommandPermission
import cc.gxstudio.gmanager.logutil.Log
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.message.data.MessageChain


object Commands {
    
    val COMMAND_LIST: MutableList<Command>
        get() {
            val mlist = mutableListOf<Command>(
                MuteCommand(),
                CleanCommand(),
                KickCommand(),
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
            if (dontHasCommandPermission(this@MuteCommand)) return
            target.mute(duration)
            sendGroupOrOtherMessage("已禁言${duration}秒")
        }
    }
    
    class KickCommand : SimpleCommand(
        PluginMain, "kick",
        description = "基础指令-踢出群成员"
                                     ) {
        @Handler
        suspend fun CommandSender.onCommand(target: NormalMember, reason: String) {
            if (dontHasCommandPermission(this@KickCommand)) return
            target.kick(reason)
            sendGroupOrOtherMessage("已踢出${reason}秒")
        }
    }
    
    class CleanCommand : SimpleCommand(
        PluginMain, "clean",
        description = "基础指令-清屏"
                                      ) {
        @Handler
        suspend fun CommandSender.onCommand() {
            if (dontHasCommandPermission(this@CleanCommand)) return
            sendGroupOrOtherMessage("刷屏内容")//todo:刷屏内容
        }
    }


}


suspend fun CommandSender.sendGroupOrOtherMessage(message: String) {
    if (getGroupOrNull() == null) sendMessage(message) else getGroupOrNull()!!.sendMessage(message)
}


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
