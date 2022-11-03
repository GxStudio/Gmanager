package cc.gxstudio.gmanager.config

import cc.gxstudio.gmanager.management.Penalties
import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig

class GroupConfig(groupId: String, fatherGroup:Long) : AutoSavePluginConfig("group/$groupId") {
    @Serializable
    data class GroupData(val CollectGroupData: Boolean)
    
    @Serializable
    data class GroupSettings(
        val voteSettings: VoteSettings,
        val warnSettings: WarnSettings,
        val otherSettings: OtherSettings
                                   ) {
        @Serializable
        data class VoteSettings(
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
        data class WarnSettings(
            val muteTime: Long,
            val enableKick: Boolean,
            val maxWarn: Int,
            val afterKickClearWarn: Boolean,
            val afterKickBlackList: Boolean
                                      )
        
        @Serializable
        data class OtherSettings(
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
    data class GroupHintMsg(
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
    data class GroupMsgSpamCheck(
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
    data class GroupMemberAutoNick(
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
    data class GroupJoinMemberReview(
        val refuseReason: RefuseReason
                                           ) {
        @Serializable
        data class RefuseReason(
            val defaultReason: String,
            val customReason: Boolean,
            val customReasonList: Map<Int, String>
                               )
        
        @Serializable
        data class ReviewSettings(
            val reviewType: ReviewType,
            
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
            data class ReviewType(
                val type: ReviewTypes
                                 )
            
            @Serializable
            data class RefuseOverlapJoin(
                val enable: Boolean,
                val groupList: List<Long>
                                        )
            
            @Serializable
            data class RefuseGenders(
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
                
                val userLevel: Int,
                val userIDLength: Int,
                val userGroupRank: Int,
                val userGroupJoinMonths: Int,
                val haveJoinReason: Int,
                
                val dontRefuse: Boolean
                                   )
            
            
        }
    }
    
    @Serializable
    data class GroupInviteStatistics(
        val Enable: Boolean
    
                                           )
    
    @Serializable
    data class GroupScheduledTask(
        val taskList: List<GroupTask>
                                        ) {
        
        
        @Serializable
        data class GroupTask(
            val taskAction: GroupTaskAction,
            val taskTrigger: TaskTriggers.TaskTrigger
                            ) {
            interface GroupTaskAction
            
            @Serializable
            data class SendMessageType(
                val msgText: String,
                                      ) : GroupTaskAction
            
        }
        
        
        @Serializable
        class TaskTriggers {
            @Serializable
            data class CycleTask(
                override val enable: Boolean,
                override val taskList: List<GroupTask>,
                val cycleInterval: Int,//循环间隔，单位为分钟
                override val timeLimitInterval: TimeInterval,
                
                ) :
                TaskTrigger
            
            @Serializable
            data class TimingTask(
                override val enable: Boolean,
                override val taskList: List<GroupTask>,
                val time: Long,//时间戳，单位为毫秒
                override val timeLimitInterval: TimeInterval,
                                        ) :
                TaskTrigger
            
            
            interface TaskTrigger {
                val enable: Boolean
                val taskList: List<GroupTask>
                val timeLimitInterval: TimeInterval
            }
        }
        
    }
    
    @Serializable
    data class GroupKickAndMuteAction(
        val autoMuteAll: AutoMuteAll,
        val kickSilenceJoinMember: KickSilenceJoinMember,
        val kickOverlapJoin: KickOverlapJoin,
        val controlMaxMember: ControlMaxMember
                                            ) {
        @Serializable
        data class AutoMuteAll(
            val muteActionList: List<MuteAction>
                                     ) {
            @Serializable
            data class MuteAction(
                val mode: Boolean, //true为禁言，false为解除禁言
                val time: Int//执行时间
                                 )
            
        }
        
        @Serializable
        data class KickSilenceJoinMember(
            val enable: Boolean,
            val time: Int,//执行时间,单位为分钟
            
            val remindSpeak: RemindSpeak,
            
            val blackListAfterKick: Boolean
                                               ) {
            @Serializable
            data class RemindSpeak(
                val enable: Boolean,
                val time: Int//执行时间,单位为分钟
                                  )
        }
        
        @Serializable
        data class KickOverlapJoin(
            val enable: Boolean,
            val blackListAfterKick: Boolean,
            val inspectionInterval: Int,//检查间隔，单位为分钟
            val checkGroupList: List<Long>
                                         )
        
        @Serializable
        data class ControlMaxMember(
            val enable: Boolean,
            val maxMember: Int,
            val inspectionInterval: Int,//检查间隔，单位为分钟
            val sendMsgBeforeKick: String,
            
            val newMemberProtect: NewMemberProtect,
            val kickWhenMemberJoin: Boolean,
            val blackListAfterKick: Boolean,
            val kickCondition: KickCondition,
            
            val kickNoSpeechForLongestTime: Boolean
                                          ) {
            @Serializable
            data class NewMemberProtect(
                val enable: Boolean,
                val time: Int//保护时间，单位为分钟
                                       )
            
            @Serializable
            data class KickCondition(
                val silenceJoin: SilenceJoin,
                val getPointInDays: GetPointInDays,
                val kickJoinInTimeInterval: KickJoinInTimeInterval
                                    ) {
                @Serializable
                data class SilenceJoin(
                    val enable: Boolean,
                    val time: Int//时间段，默认为天数
                                      )
                
                @Serializable
                data class GetPointInDays(
                    val enable: Boolean,
                    val point: Int,
                    val time: Int //指定时间内，单位为日
                                         )
                
                @Serializable
                data class KickJoinInTimeInterval(
                    val enable: Boolean,
                    val timeInterval: TimeInterval
                                                 )
                
            }
            
        }
    }
    
    @Serializable
    data class QandA(val enable: Boolean, val qaList: List<qa>) {
        @Serializable
        data class qa(
            val Q: String,
            val A: String,
            val enable: Boolean,
            val matchPattern: TextMatchPattern
                            )
    }
    
}

@Serializable
data class TimeInterval(
    val start: Int,
    val end: Int
                       )

@Serializable
enum class TextMatchPattern {
    FUZZY,//模糊
    PERFECT,//完全
    REGULAR//正则
}
@Serializable
enum class HandleTypes{
    ACCESS,
    REFUSE,
    IGNORE,
    MANUAL
}

@Serializable
enum class ReviewTypes {
    ACCESS,
    REFUSE,
    IGNORE,
    MANUAL,
    AUTOCHECK
}