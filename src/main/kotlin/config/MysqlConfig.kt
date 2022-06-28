package cc.gxstudio.gmanager.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object MysqlConfig: AutoSavePluginConfig("MysqlConfig") {

    @ValueDescription("ip地址，默认本机：127.0.0.1")
    var ip by value<String>("127.0.0.1")
    @ValueDescription("端口号,请填写1-65535的端口，否则出错概不负责。默认：3306")
    var port by value<Int>(3306)
    @ValueDescription("数据库名称，填空自动创建名为gmanager的数据库")
    var database by value<String>()
    @ValueDescription("用户名，默认：root")
    var user by value<String>("root")
    @ValueDescription("密码,请务必手动填写。仅支持Mysql8")
    var password by value<String>()
}
