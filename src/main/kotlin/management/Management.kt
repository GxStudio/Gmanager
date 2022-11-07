package cc.gxstudio.gmanager.management

import cc.gxstudio.gmanager.command.checkGroupExist
import cc.gxstudio.gmanager.command.sendGroupOrOtherMessage
import cc.gxstudio.gmanager.config.getGConfig
import cc.gxstudio.gmanager.extension.disableManage
import cc.gxstudio.gmanager.extension.enableManage
import cc.gxstudio.gmanager.extension.groupExist
import cc.gxstudio.gmanager.extension.manageEnabled
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.contact.nameCardOrNick

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
        sendGroupOrOtherMessage(this.getGroupOrNull()!!.getGConfig().groupHintMsg.sendInGroupMsg.ClearScreenMessage)//todo:清屏内容
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
        if (checkGroupExist(group)){
        
        }
    }
    
}