package cc.gxstudio.gmanager.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object KtorConfig: AutoSavePluginConfig("KtorConfig") {
    @ValueDescription("ip/域名地址，默认：0.0.0.0")
    var ip by value<String>("0.0.0.0")
    @ValueDescription("端口号,请填写1-65535的端口，否则出错概不负责。默认：23366")
    var port by value<Int>(23366)
}