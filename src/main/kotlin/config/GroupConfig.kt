package cc.gxstudio.gmanager.config

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig

class GroupConfig(groupid: String) : AutoSavePluginConfig("group/$groupid") {
    @Serializable
    public data class GroupData(val CollectGroupData: Boolean)
    
    @Serializable
    public data class GroupSettings(
        val voteSettings: VoteSettings,
        val warnSettings: WarnSettings,
        val otherSettings: OtherSettings
                                   ) {
        @Serializable
        public data class VoteSettings(
            val votePerDay: Int,
            val createVotePerDay: Int,
            val voteTime: Int,
            val voteMute: VoteMute
                                      ) {
            @Serializable
            data class VoteMute(
                val enable: Boolean,
                val votetick: Int,
                val time: Int
                               )
            
            @Serializable
            data class VoteKick(
                val enable: Boolean,
                val votetick: Int,
                val toBlacklist: Boolean
                               )
            
            
        }
        
        @Serializable
        public data class WarnSettings(
            val muteTime: Long,
            val enableKick: Boolean,
            val maxWarn: Int,
            val afterKickClearWarn: Boolean,
            val afterKickBlackList: Boolean
                                      )
        
        @Serializable
        public data class OtherSettings(
            val quitToBlackList: Boolean,
            val kickToBlacklist: Boolean,
            val afterKickDeleteMsgAmount: Int,
            val BlackListToGlobal: Boolean,
            val joinWithMuteTime: Int,
            val stopRepeat: Boolean,
            val stopRepeatAmount: Int,
                                       )
    }
    
    @Serializable
    public data class GroupHintMsg(
        val sendInGroupMsg: SendInGroupMsg,
        val sendInPrivateMsg: SendInPrivateMsg,
        val sendMailMessage: SendMailMessage
        
                                  ) {
        @Serializable
        data class SendInGroupMsg(
            val joinMsg: JoinMsg,
            val quitMsg: QuitMsg,
            val kickMsg: KickMsg,
            val ClearScreenMessage:String
                                 ) {
            @Serializable
            data class JoinMsg(val Enable: Boolean, val Msg: String, val ToVoice: Boolean)
            
            @Serializable
            data class QuitMsg(val Enable: Boolean, val Msg: String, val ToVoice: Boolean)
            
            @Serializable
            data class KickMsg(val Enable: Boolean, val Msg: String, val ToVoice: Boolean)
            
            @Serializable
            data class FileUploadMsg(val Enable: Boolean, val Msg: String, val ToVoice: Boolean)
            
            @Serializable
            data class AddManagerMsg(val Enable: Boolean, val Msg: String, val ToVoice: Boolean)
            
            @Serializable
            data class RemoveManagerMsg(val Enable: Boolean, val Msg: String, val ToVoice: Boolean)
        }
        
        
        @Serializable
        data class SendInPrivateMsg(
            val memberJoinMsg: MemberJoinMsg,
            val memberBeManagerMsg: MemberBeManagerMsg,
            val managerCancelledMsg: ManagerCancelledMsg
                                   ) {
            @Serializable
            data class MemberJoinMsg(val Enable: Boolean, val Msg: String, val ToVoice: Boolean)
            
            @Serializable
            data class MemberBeManagerMsg(val Enable: Boolean, val Msg: String, val ToVoice: Boolean)
            
            @Serializable
            data class ManagerCancelledMsg(val Enable: Boolean, val Msg: String, val ToVoice: Boolean)
        }
        
        @Serializable
        data class SendMailMessage(val joinGroup: JoinGroup) {
            @Serializable
            data class JoinGroup(val Enable: Boolean, val Msg: String)
        }
        
    }
    
    

}