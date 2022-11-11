package cc.gxstudio.gmanager.data

import cc.gxstudio.gmanager.data.classes.GroupMessageData
import cc.gxstudio.gmanager.PluginMain.reload as reloadData
import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.event.events.GroupMessageEvent

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
    val memberList by value(mutableMapOf<Long, ManagableMemeber>())
    
    
    @Serializable
    data class ManagableMemeber(val name: String, val id: Long, var warnTimes: Int)
    
    
    
    
    
    
    
    
    
    
    
    
    
    fun addMessage(msg: GroupMessageEvent) {
        val source = msg.source
        messageEventList.add(
            GroupMessageData(
                time = msg.time,
                botid = msg.bot.id,
                targetUin = source.targetId,
                senderPerm = msg.sender.permission,
                botPerm = msg.group.botPermission,
                ids = source.ids,
                internalIds = source.internalIds,
                sendFromUin = source.fromId,
                groupid = msg.group.id,
                            )
                            )
    }
    
}


