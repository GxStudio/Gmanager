package cc.gxstudio.gmanager

import cc.gxstudio.gmanager.PluginMain.reload
import cc.gxstudio.gmanager.config.GlobalConfig
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Group
///////////////////////////////////////////////////////////////////////////
//这个文件内应存放未归类的函数，以及一些全局变量，提交release前必须进行归类
///////////////////////////////////////////////////////////////////////////

fun loadTest(){
    GlobalConfig.basicSettings.enableGroups.add(732700499)
}
