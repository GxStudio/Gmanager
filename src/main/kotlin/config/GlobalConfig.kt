package cc.gxstudio.gmanager.config

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig

class GlobalConfig() : AutoSavePluginConfig("global") {
    @Serializable
    data class BasicSettings(
        val enableGroups: List<Long>,
        val enableGroup: Boolean,
        val enablePrivate: Boolean,
        
        val autoDeleteFirend: Boolean,
        val beMutedThanMinLeave: Int,//被禁言多少分钟后自动退群
        val nickAutoChangeTime: Int,//昵称自动更改最小间隔
        
        val messageQueue: MessageQueue,
        
        val eventNotification: EventNotification,
                            ) {
        @Serializable
        data class MessageQueue(
            val enable: Boolean,
            val maxMsgsSize: Int, //最大消息数
            val maxMsgsTime: Int,//最大消息缓存时间
            val splitLine: String//分割线
                               )
        
        @Serializable
        data class EventNotification(
            val joinGroup: Boolean,
            val leaveGroup: Boolean,
            val beKicked: Boolean,
            val beManager: Boolean,
            val cancelManager: Boolean,
            val firendAdded: Boolean
                                    )
        
        @Serializable
        data class NewGroupSettings(
            val importGroupId: Long,
            val createNewReviewProjectByGroup: Boolean,
            val importReviewProject: Int,//TODO:创建监控方案enum类型
                                   )
        
        @Serializable
        data class FirendRequest(
            val handle: HandleTypes,
            val msgAfterAdded: String
                                )
        
        @Serializable
        data class InviteToGroup(
            val handle: HandleTypes,
            val msgAfterJoin: String,//加入群后发送的消息
            val msgAfterReject: String//拒绝后的私聊消息
                                )
        
        @Serializable
        data class FirendAddedMsg(
            val message: String
                                 )//好友添加后发送的消息,仅发送一次
        

    }
}

@Serializable
enum class SettingFrom {
    DEFAULT, BYGROUP, USERCHOICE
}