package cc.gxstudio.gmanager.data

import cc.gxstudio.gmanager.data.classes.GroupMessageData
import cc.gxstudio.gmanager.logutil.Log
import cc.gxstudio.gmanager.PluginMain.reload as reloadData
import kotlinx.serialization.Serializable
import net.mamoe.mirai.Mirai
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.source

fun getGmanageData(group: Long): GroupData = groupDataList[group]!!

var groupDataList: MutableMap<Long, GroupData> = mutableMapOf()
fun GroupData.load(): GroupData {
    this.reloadData()
    return this
}

fun Group.getGData(): GroupData {
    return getGmanageData(this.id)
}

class GroupData(group: Long) : AutoSavePluginData("group/$group") {
    //todo:完成群成员数据导入
    
    
    val messageEventList by value<MutableList<GroupMessageData>>()
    
    
    @Serializable
    val MemberList by value(mutableMapOf<Long, ManagableMemeber>())
    
    
    @Serializable
    data class ManagableMemeber(val name: String, val id: Long, val warnTimes: Int)
    
    
    fun addMessage(msg: GroupMessageEvent) {
        val source = msg.message.source
        Log.v(source.botId, "GroupData-addMessage")
        Log.v(source.ids, "GroupData-addMessage")
        Log.v(source.internalIds, "GroupData-addMessage")
        Log.v(source.time, "GroupData-addMessage")
        Log.v(source.targetId, "GroupData-addMessage")
        
        
        
        
        
        
        messageEventList.add(
            GroupMessageData(
                time = msg.time,
                botid = msg.bot.id,
                targetUin = source.targetId,
                senderPerm = msg.sender.permission,
                botPerm = msg.group.botPermission,
                //msg.message,
                ids = source.ids,
                internalIds = source.internalIds,
                sendFromUin = source.fromId,
                groupid = msg.group.id,
                            )
                            )//,message.sender.permission
        // if (messageEventList.size > 100) messageEventList.removeAt(0)
    }
    
}


