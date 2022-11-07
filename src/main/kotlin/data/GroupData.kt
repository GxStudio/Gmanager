package cc.gxstudio.gmanager.data

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.SingleMessage

fun getGmanageData(group: Long): GroupData = groupDataList[group]!!

var groupDataList:MutableMap<Long, GroupData> = mutableMapOf()

fun Group.getGData(): GroupData {
    return getGmanageData(this.id)
}
class GroupData(group:Long):AutoSavePluginData("group/$group"){
    //todo:完成群成员数据导入
    val msgList = listOf<MessageChain>()
    val MemberList = mutableMapOf<Long, ManagableMemeber>()
    
    @Serializable
    data class ManagableMemeber(val name:String,val id : Long,val warnTimes:Int){}
}