package cc.gxstudio.gmanager.data.classes

import cc.gxstudio.gmanager.extension.getGroup
import cc.gxstudio.gmanager.logutil.Log
import kotlinx.serialization.Serializable
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.MessageSource.Key.recall

@Serializable
data class GroupMessageData(
    val time: Int,
    val botid: Long,
    val targetUin: Long,
    val senderPerm: MemberPermission,
    val botPerm: MemberPermission,
    //val message: Message,
    val ids: IntArray,
    val internalIds: IntArray,
    val sendFromUin: Long,
    val groupid: Long,
   // val msgSource: MessageSource
                           ) {
    suspend fun recall(): Boolean {
        val group = getGroup(groupid)
        val bot = Bot.getInstance(botid)
        if (group != null) {
            val msgSource =
                bot.buildMessageSource(MessageSourceKind.GROUP) {
                    time(this@GroupMessageData.time)
                    internalId(*this@GroupMessageData.internalIds)
                    id(*this@GroupMessageData.ids)
                    sender(this@GroupMessageData.sendFromUin)
                    target(this@GroupMessageData.targetUin)
                }
            Log.v("群组存在，开始撤回。", "GroupMessage-recall")
            if (recalled) {
                
                Log.v("消息已撤回", "GroupMessage-recall")
                return true
            }
            Log.v(senderPerm.name)
            Log.v(botPerm.name)
            return if (senderPerm < botPerm) {
                Log.v("进行撤回", "GroupMessage-recall")
                msgSource.recall()
                messageRecalled = true
                true
            } else {
                Log.v("权限不足，无法撤回", "GroupMessage-recall")
                false
            }
        } else {
            Log.v("群组不存在，无法撤回", "GroupMessage-recall")
            return false
        }
        return false
    }
    
    private var messageRecalled: Boolean = false
    private val recalled: Boolean
        get() = messageRecalled
}