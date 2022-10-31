package cc.gxstudio.gmanager.config

import cc.gxstudio.gmanager.management.Penalties
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
            val ClearScreenMessage: String
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
    
    @Serializable
    public data class GroupMsgSpamCheck(
        val contentReview: ContentReview,
        val msgFloodReview: MsgFloodReview,
        val fileUploadCheck: FileUploadReview,
        val autoDeleteReview: AutoDeleteReview
                                       ) {
        @Serializable
        data class ContentReview(
            val enable: Boolean,
            val reviewProject: Long,
            val useGlobalProject: Boolean,
            val checkTraditionalChinese: Boolean,
            val ocrCheck: Boolean,
            val similarTextCheck: Boolean,
            val qrCodeCheck: Boolean,
            val deleteMsg: Boolean,
            val voiceCheck: Boolean,
                                )
        
        @Serializable
        data class MsgFloodReview(
            val enable: Boolean,
            val sendMsgLimit: Int,
            val sendMsgPerSecondLimit: Int,
            val TextLengthMaxlimit: Int,
            val lineMaxLimit: Int,
            val repeatLimit: Int,
            val noBlankText: Boolean,
            val imagePerMsg: Int,
            val continuouslySendImageTimes: Int,
            
            val penaltie: Penalties,
            val deleteMsg: Boolean,
            val deleteFirstFloodMsg: Boolean,
            val clearScreen: Boolean,
            
            val shortMsgCode: Boolean,//将收到的图片等Mirai码进行缩短成2长度处理
                                 )
        
        @Serializable
        data class FileUploadReview(
            val fileFormatCheckMode: Boolean,//true为允许上传的文件格式，false为禁止上传的文件格式
            val fileFormat: List<String>,//文件格式列表
            val fileSizeLimit: Long,//文件大小限制，单位为字节
            val fileNameNotAllowed: List<String>,//不允许上传的文件名
            val fileUploadLimit: Int,//每个人每天上传文件的数量限制
            val penaltie: Penalties,
            val deleteFile: Boolean,
                                   )
        
        @Serializable
        data class AutoDeleteReview(val tellAdmin: Boolean)
        
    }
    
    @Serializable
    public data class GroupMemberAutoNick(
        val nickFormartSettings: NickFormartSettings,
        val nickChangeSettings: NickChangeSettings
                                         ) {
        @Serializable
        data class NickFormartSettings(
            val formartText: String,
            val useRemarkInfo: Boolean,
            val maxLength: Int,
            val blankLocationReplace: String,
            val manName: String,
            val womanName: String,
            val sexLessReplace: String
                                      )
        
        @Serializable
        data class NickChangeSettings(
            val detectionCycleTime: Int,
            val nickCorrectionTrigger: NickCorrectionTrigger,
            val nickWhiteList: NickWhiteList,
            val nickMonitorSettings: NickMonitorSettings
                                     ) {
            @Serializable
            data class NickCorrectionTrigger(
                val timingCorrection: Boolean,
                val joinCorrection: Boolean,
                val msgSendCorrection: Boolean,
                                            )
            
            @Serializable
            data class NickWhiteList(
                val ignoreWords: List<String>,
                val whiteList: List<Long>,
                                    )
            
            @Serializable
            data class NickMonitorSettings(
                val kickNameWithWords: List<String>,
                val hintWhenNickChange: String,
                                          )
            
        }
        
        
    }
    
    @Serializable
    public data class GroupJoinMemberReview(val refuseReason: RefuseReason) {
        @Serializable
        data class RefuseReason(
            val defaultReason: String,
            val customReason: Boolean,
            val customReasonList: Map<Int, String>
                               )
        
        @Serializable
        data class ReviewSettings(
            val refuseOverlapJoin: RefuseOverlapJoin,
            val refuseGenders: RefuseGenders,
            val refuseBlackList: Boolean,
            val allowWhiteList: Boolean,
            
            val refuseNeverSpeakMemberInvite: Boolean,
            
            val refuseAgeLessThan: Int,
            val refuseAgeMoreThan: Int,
            
            val reasonCheck: ReasonCheck,
            
            val refuseNameWithWords: List<String>,
            
            val refuseSignWithWords: List<String>,
            
            val weightAccess: WeightAccess,
            
            ) {
            
            @Serializable
            data class RefuseOverlapJoin(
                val enable: Boolean,
                val groupList: List<Long>
                                        )
            
            @Serializable
            public data class RefuseGenders(
                val refuseMan: Boolean,
                val refuseWoman: Boolean,
                val refuseSexLess: Boolean
                                           )
            
            @Serializable
            data class ReasonCheck(
                val mode: Boolean,
                val reasonList: List<String>
                                  )
            
            @Serializable
            data class WeightAccess(
                val enable: Boolean,
                val limitWeight: Int,
                
                val userLevel:Int,
                val userIDLength:Int,
                val userGroupRank:Int,
                val userGroupJoinMonths:Int,
                val haveJoinReason:Int,
                
                val dontRefuse:Boolean
                                   )
            
            
        }
    }
    
    
}