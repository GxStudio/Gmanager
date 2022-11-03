package cc.gxstudio.gmanager.config

import cc.gxstudio.gmanager.config.GroupConfig.GroupSettings.VoteSettings.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupHintMsg.SendInGroupMsg.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupHintMsg.SendInPrivateMsg.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupHintMsg.SendMailMsg.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupMemberAutoNick.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupMemberAutoNick.NickChangeSettings.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupJoinMemberReview.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupJoinMemberReview.ReviewSettings.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupMsgSpamCheck.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupMsgSpamCheck.MsgFloodReview.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupKickAndMuteAction.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupKickAndMuteAction.KickSilenceJoinMember.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupKickAndMuteAction.ControlMaxMember.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupKickAndMuteAction.ControlMaxMember.KickCondition.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupSettings.*
import cc.gxstudio.gmanager.config.GroupConfig.GroupHintMsg.*
import cc.gxstudio.gmanager.management.Penalties
import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

class GroupConfig(groupId: String, fatherGroup: Long) : AutoSavePluginConfig("group/$groupId") {
    
    val groupData by value(GroupData(false))
    val groupSettings by value(
        GroupSettings(
            VoteSettings(
                0, 0, 0,
                VoteMute(false, 0, 0),
                VoteKick(false, 0, false)
                        ),
            WarnSettings(
                0, false, 3,
                afterKickClearWarn = true,
                afterKickBlackList = false
                        ),
            OtherSettings(
                quitToBlackList = false,
                kickToBlacklist = false,
                afterKickDeleteMsgAmount = 5,
                BlackListToGlobal = false,
                joinWithMuteTime = 5,
                stopRepeat = false,
                stopRepeatAmount = 5
                         )
                     )
                              )
    
    val groupHintMsg by value(
        GroupHintMsg(
            SendInGroupMsg(
                JoinMsg(false, "", false),
                QuitMsg(false, "", false),
                KickMsg(false, "", false),
                FileUploadMsg(false, "", false),
                AddManagerMsg(false, "", false),
                RemoveManagerMsg(false, "", false),
                ""
                          ),
            SendInPrivateMsg(
                MemberJoinMsg(false, "", false),
                MemberBeManagerMsg(false, "", false),
                ManagerCancelledMsg(false, "", false)
                            ),
            SendMailMsg(
                JoinGroup(false, "")
                       )
                    )
                             )
    val groupMsgSpamCheck by value(
        GroupMsgSpamCheck(
            ContentReview(
                false, -1,
                useGlobalProject = false,
                checkTraditionalChinese = false,
                ocrCheck = false,
                similarTextCheck = false,
                qrCodeCheck = false,
                deleteMsg = false,
                voiceCheck = false
                         ),
            MsgFloodReview(
                false,
                sendMsgLimit = SendMsgLimit(false, 3, 3),
                textLengthMaxlimit = 4500,
                lineMaxLimit = 12,
                repeatLimit = 114514,
                noBlankText = false,
                imagePerMsg = 5,
                continuouslySendImageTimes = 5,
                penaltie = Penalties.INGORE(),
                deleteMsg = false,
                deleteFirstFloodMsg = false,
                clearScreen = false,
                shortMsgCode = false
                                            ),
            FileUploadReview(
                fileFormatCheckMode = false,
                fileFormat = listOf("avi", "exe", "rar"), fileSizeLimit = 1024 * 1024 * 1024,
                fileNameNotAllowed = listOf("国产"),
                fileUploadLimit = 100,
                penaltie = Penalties.INGORE(),
                deleteFile = false
                                              ),
            AutoDeleteReview(
                tellAdmin = true
                                              )
                         )
                                  )
    val groupMemberAutoNick by value(
        GroupMemberAutoNick(
            NickFormartSettings(
                formartText = "[G]-{name}",
                useRemarkInfo = false,
                maxLength = 20,
                blankLocationReplace = "地球",
                manName = "男",
                womanName = "女",
                sexLessReplace = "？"
                                                   ),
            NickChangeSettings(
                detectionCycleTime = 20,
                NickCorrectionTrigger(
                    timingCorrection = false,
                    joinCorrection = false,
                    msgSendCorrection = false
                                                                            ),
                NickWhiteList(listOf(""), listOf(0)),
                NickMonitorSettings(listOf("卖片"), "")
                                                  )
                           )
                                    )
    val groupJoinMemberReview by value(
        GroupJoinMemberReview(
            RefuseReason(
                defaultReason = "",
                customReason = false,
                customReasonList = mapOf()
                                              ),
            ReviewSettings(
                ReviewType(ReviewTypes.IGNORE),
                RefuseOverlapJoin(false, listOf()),
                RefuseGenders(
                    refuseMan = false,
                    refuseWoman = false,
                    refuseSexLess = false
                                                                  ),
                refuseBlackList = false,
                allowWhiteList = true,
                refuseNeverSpeakMemberInvite = true,
                refuseAgeLessThan = 0,
                refuseAgeMoreThan = 99,
                ReasonCheck(false, listOf()),
                refuseNameWithWords = listOf(),
                refuseSignWithWords = listOf(),
                WeightAccess(
                    false,
                    limitWeight = 0,
                    userLevel = 0,
                    userIDLength = 0,
                    userGroupRank = 0,
                    userGroupJoinMonths = 0,
                    haveJoinReason = 0,
                    dontRefuse = true
                                                                 )
            
            
                                                )
                             )
                                      )
    val groupInviteStatistics by value(GroupInviteStatistics(false))
    val groupScheduledTask by value(GroupScheduledTask(listOf()))
    val groupKickAndMuteAction by value(
        GroupKickAndMuteAction(
            
            AutoMuteAll(listOf()),
            KickSilenceJoinMember(
                false, 60,
                RemindSpeak(false, 3),
                blackListAfterKick = false
                                 ),
            KickOverlapJoin(
                enable = false,
                blackListAfterKick = false,
                inspectionInterval = 5,
                checkGroupList = listOf()
                                                  ),
            ControlMaxMember(
                enable = false, maxMember = 5000,
                inspectionInterval = 5,
                sendMsgBeforeKick = "",
                NewMemberProtect(false, 5),
                kickWhenMemberJoin = true,
                blackListAfterKick = false,
                KickCondition(
                    SilenceJoin(false, 3),
                    GetPointInDays(false, 10, 3),
                    KickJoinInTimeInterval(
                        false, TimeInterval(1, 5)
                                                        ),
                             ),
                kickNoSpeechForLongestTime = true
                                                   )
                              )
                                       )
    val qANDa by value(QandA(false, listOf()))
    
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
            val voteMute: VoteMute,
            val voteKick: VoteKick
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
            val joinWithMuteTime: Int,//加群时禁言时间
            
            val stopRepeat: Boolean,
            val stopRepeatAmount: Int,
                                )
    }
    
    @Serializable
    data class GroupHintMsg(
        val sendInGroupMsg: SendInGroupMsg,
        val sendInPrivateMsg: SendInPrivateMsg,
        val sendMailMessage: SendMailMsg
    
                           ) {
        @Serializable
        data class SendInGroupMsg(
            val joinMsg: JoinMsg,
            val quitMsg: QuitMsg,
            val kickMsg: KickMsg,
            val fileUploadMsg: FileUploadMsg,
            val addManagerMsg: AddManagerMsg,
            val removeManagerMsg: RemoveManagerMsg,
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
        data class SendMailMsg(val joinGroup: JoinGroup) {
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
            val reviewProject: Long,//-1为未定义
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
            val sendMsgLimit: SendMsgLimit,
            val textLengthMaxlimit: Int,
            val lineMaxLimit: Int,
            val repeatLimit: Int,
            val noBlankText: Boolean,
            val imagePerMsg: Int,
            val continuouslySendImageTimes: Int,
            
            val penaltie: Penalties.Penaltie,
            val deleteMsg: Boolean,
            val deleteFirstFloodMsg: Boolean,
            val clearScreen: Boolean,
            
            val shortMsgCode: Boolean,//将收到的图片等Mirai码进行缩短成2长度处理
                                 ) {
            @Serializable
            data class SendMsgLimit(
                val enable: Boolean,
                val limit: Int,
                /**时间长度，单位为秒*/
                val time: Int
                                   )
            
            fun test() {
            
            }
        }
        
        @Serializable
        data class FileUploadReview(
            val fileFormatCheckMode: Boolean,//true为允许上传的文件格式，false为禁止上传的文件格式
            val fileFormat: List<String>,//文件格式列表
            val fileSizeLimit: Long,//文件大小限制，单位为字节
            val fileNameNotAllowed: List<String>,//不允许上传的文件名
            val fileUploadLimit: Int,//每个人每天上传文件的数量限制
            val penaltie: Penalties.Penaltie,
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
            /**循环检测时间，单位为分钟*/
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
        val refuseReason: RefuseReason,
        val reviewSettings: ReviewSettings
                                    ) {
        @Serializable
        data class RefuseReason(
            val defaultReason: String,
            val customReason: Boolean,
            val customReasonList: Map<Int, String>//todo:需要完成的拒绝理由列表
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
                val mode: Boolean,//false为拒绝,true为允许
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
    data class QandA(val enable: Boolean, val qaList: List<QA>) {
        @Serializable
        data class QA(
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
enum class HandleTypes {
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
