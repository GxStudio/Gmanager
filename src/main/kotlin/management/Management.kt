package cc.gxstudio.gmanager.management

import cc.gxstudio.gmanager.command.Commands
import cc.gxstudio.gmanager.command.sendGroupOrOtherMessage
import cc.gxstudio.gmanager.command.varOfGroupExist
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.contact.User

object Management {
    suspend fun kickMember(member: NormalMember, reason: String?) {
        var packageReason =""
        if (reason == null){
            packageReason = ""//todo:默认踢出原因
        }
        member.kick(packageReason)
        //todo:踢出在群内发布信息（判断）
    }
    suspend fun CommandSender.cleanScreen(){
        sendGroupOrOtherMessage("")//todo:清屏内容
    }
    suspend fun muteMember (member:NormalMember,time:Int){
        member.mute(time)
        //todo:禁言在群内发送消息
        
    }
    suspend fun CommandSender.openManage(group: Group?){
        if (Commands.varOfGroupExist(group)) {
            //todo:完成群管开启指令
            sendGroupOrOtherMessage("群管已开启")
        } else sendGroupOrOtherMessage("群号提供错误，请重新提供群号")
    }
    suspend fun CommandSender.closeManage(group:Group?){
        if (Commands.varOfGroupExist(group)) {
            //todo:完成群管关闭指令
            sendGroupOrOtherMessage("此群群管已关闭")
        } else sendGroupOrOtherMessage("群号提供错误，请重新提供群号")
    
    }
    fun checkUserPermission(user:User):String{
    return  ""
    }
    
}