package cc.gxstudio.gmanager.config

import cc.gxstudio.gmanager.config.GlobalConfig.BasicSettings.*
import cc.gxstudio.gmanager.config.GlobalConfig.OtherSettings.MailSettings
import cc.gxstudio.gmanager.config.GlobalConfig.ReviewSettings.ReviewProject
import cc.gxstudio.gmanager.management.Penalties
import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

class GlobalConfig() : AutoSavePluginConfig("global") {
    val basicSettings by value(
        BasicSettings(
            enableGroups = listOf(),
            enableGroup = true,
            enablePrivate = true,
            
            autoDeleteFirend = false,
            BeMutedThanMinLeave(false, 1200),
            nickAutoChangeTime = 20,
            MessageQueue(
                true,
                maxMsgsSize = 10,
                maxMsgsTime = 10,
                splitLine = "----------------"
                        ),
            EventNotification(
                joinGroup = true,
                leaveGroup = true,
                beKicked = true,
                beManager = true,
                cancelManager = true,
                firendAdded = false
                             )
                     )
                              )
    
    val reviewSettings by value(
        ReviewSettings(
            globalReviewProject = ReviewProject(listOf()),
            reviewProject = listOf()
                      )
                               )
    
    val commandSettings by value(CommandSettings(listOf()))
    val hintSettings by value(HintSettings(listOf()))
    val otherSettings by value(
        MailSettings(
            false,
            "smtp.qq.com",
            465,
            "",
            "",
            ssl = true
                    )
                              )
    
    @Serializable
    data class BasicSettings(
        val enableGroups: List<Long>,
        val enableGroup: Boolean,
        val enablePrivate: Boolean,
        
        val autoDeleteFirend: Boolean,
        val beMutedThanMinLeave: BeMutedThanMinLeave,//被禁言多少分钟后自动退群
        val nickAutoChangeTime: Int,//昵称自动更改最小间隔
        
        val messageQueue: MessageQueue,
        
        val eventNotification: EventNotification,
                            ) {
        @Serializable
        data class BeMutedThanMinLeave(
            val enable: Boolean,
            val min: Int//分钟
                                      )
        
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
    
    @Serializable
    data class ReviewSettings(
        val globalReviewProject: ReviewProject,
        val reviewProject: List<ReviewProject>
                             ) {
        @Serializable
        data class ReviewProject(
            val reviewMethods: List<ReviewMethod>,
                                ) {
            @Serializable
            data class ReviewMethod(
                val word: String,
                val penalties: Penalties,
                val auditFreeWord: Boolean,
                val tellAdmin: Boolean,
                val clearScreen: Boolean
                                   )
        }
    }
    
    @Serializable
    data class CommandSettings(
        val commadList: List<Command>
                              ) {
        @Serializable
        data class Command(
            val commandPrimaryName: String,
            val alias: List<String>
                          )
    }
    
    @Serializable
    data class HintSettings(val hint: List<HintMsg>) {
        @Serializable
        data class HintMsg(
            val hint: Hints,
            val text: String
                          )
    }
    
    @Serializable
    data class OtherSettings(val mailSettings: MailSettings) {
        @Serializable
        data class MailSettings(
            val enable: Boolean,
            val host: String, val port: Int,
            val mail: String,
            val password: String,
            val ssl: Boolean
                               )
    }
}

enum class Hints {


}

@Serializable
enum class SettingFrom {
    DEFAULT, BYGROUP, USERCHOICE
}