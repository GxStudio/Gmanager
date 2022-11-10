package cc.gxstudio.gmanager.extension

import cc.gxstudio.gmanager.config.GlobalConfig
import cc.gxstudio.gmanager.initialize.InitializeGroup.Groups
import cc.gxstudio.gmanager.logutil.Log
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.utils.MiraiInternalApi




@OptIn(MiraiInternalApi::class)
fun getAllJoinedGroups(): List<Group> {
  val bots = Bot.instances
    val groups = mutableListOf<Group>()
    for (bot in bots) {
        Log.v("正在获取机器人${bot.id}的群组列表", "getAllJoinedGroups")
        for (group in bot.groups) {
            Log.i("获取到群组${group.id}", "getAllJoinedGroups")
            if (groups.contains(group).not()) groups.add(group)
        }
    }
    return groups
}

fun getGroup(groupid:Long):Group?{
    for (group in Groups){
        Log.i(group.id.toString(),"joinedGroupsIDS")
        Log.i(groupid.toString(),"groupid")
        if (group.id == groupid) return group
    }
    return null
}

fun getEnabledGroup(groupid:Long):Group?{
    for (group in GlobalConfig.basicSettings.enableGroups){
        if (group == groupid) return getGroup(groupid)
    }
    return null
}

fun joinedGroup(group: Group): Boolean =
    getAllJoinedGroups().contains(group)

fun Group.manageEnabled():Boolean =
    GlobalConfig.basicSettings.enableGroups.contains(this.id)

fun Group.enableManage(){
    if (manageEnabled().not()) GlobalConfig.basicSettings.enableGroups.add(this.id)
}

fun Group.disableManage(){
    if (manageEnabled()) GlobalConfig.basicSettings.enableGroups.remove(this.id)
}