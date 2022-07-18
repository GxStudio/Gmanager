package cc.gxstudio.gmanager

import cc.gxstudio.gmanager.command.Commands
import cc.gxstudio.gmanager.config.KtorConfig
import cc.gxstudio.gmanager.config.MysqlConfig
import cc.gxstudio.gmanager.globalvar.enable
import cc.gxstudio.gmanager.globalvar.namespace
import cc.gxstudio.gmanager.http.api.startServer
import cc.gxstudio.gmanager.initialize.InitializeGroup
import cc.gxstudio.gmanager.logutil.Log
import cc.gxstudio.gmanager.permission.LuckPerms
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.*

/**
 * 使用 kotlin 版请把
 * `src/main/resources/META-INF.services/net.mamoe.mirai.console.plugin.jvm.JvmPlugin`
 * 文件内容改成 `org.example.mirai.plugin.PluginMain` 也就是当前主类全类名
 *
 * 使用 kotlin 可以把 java 源集删除不会对项目有影响
 *
 * 在 `settings.gradle.kts` 里改构建的插件名称、依赖库和插件版本
 *
 * 在该示例下的 [JvmPluginDescription] 修改插件名称，id和版本，etc
 *
 * 可以使用 `src/test/kotlin/RunMirai.kt` 在 ide 里直接调试，
 * 不用复制到 mirai-console-loader 或其他启动器中调试
 */

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "cc.gxstudio.gmanager",
        name = "G群管",
        version = "2022.0"
                        ) {
        dependsOn("io.github.karlatemp.luckperms-mirai")
        dependsOn("net.mamoe.mirai.console.chat-command")
        author("作者名称或联系方式")
        info(
            """
            这是一个测试插件, 
            在这里描述插件的功能和用法等.
        """.trimIndent()
        
            )
        // author 和 info 可以删除.
    }
                                ) {
    override fun onEnable() {
        namespace = parentPermission.id.namespace
        //_______________________________________
        KtorConfig.reload()
        MysqlConfig.reload()
        //___________________________________________
        Commands.regCommand()
        //CommandManager.unregisterCommand(GroupCommand())
        //___________________________________________
        
        //_________________________________________
        Log.i("配置文件目录：${dataFolder.absolutePath}", "插件已加载")
        //_____________________________________________________
        LuckPerms.loadLuckPermsApi()
        LuckPerms.createGroups()
        //_____________________________
        LuckPerms.setDefaultPermission()
        //_______________________________
        startServer()
        //___________________________________
        enable = true
        //______________________________________
        val eventChannel = GlobalEventChannel.parentScope(this)
        eventChannel.subscribeAlways<GroupMessageEvent> {//群消息事件
        
        }
        eventChannel.subscribeAlways<FriendMessageEvent> { //好友信息事件
        }
        eventChannel.subscribeAlways<NewFriendRequestEvent> { //好友申请事件
            accept()
        }
        eventChannel.subscribeAlways<BotInvitedJoinGroupRequestEvent> { //加群申请事件
            accept()
        }
        eventChannel.subscribeAlways<BotJoinGroupEvent> { //加入群事件
            Log.i("机器人加入群，初始化群中", "BotJoinGroupEvent")
            InitializeGroup.main(group)
        }
        
        eventChannel.subscribeAlways<BotOnlineEvent> {
            Log.i("收到机器人登录，初始化中", "BotOnlineEvent")
            InitializeGroup.list(bot.groups)
        }
        
    }
    
    
}
