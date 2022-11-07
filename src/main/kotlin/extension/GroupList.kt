package cc.gxstudio.gmanager.extension

import cc.gxstudio.gmanager.config.GlobalConfig
import cc.gxstudio.gmanager.config.GroupConfig
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.ContactList
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.GroupSettings
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.contact.active.GroupActive
import net.mamoe.mirai.contact.announcement.Announcements
import net.mamoe.mirai.contact.file.RemoteFiles
import net.mamoe.mirai.utils.MiraiInternalApi
import net.mamoe.mirai.utils.RemoteFile
import kotlin.coroutines.CoroutineContext

lateinit var joinedGroups:List<Group>



@OptIn(MiraiInternalApi::class)
fun getAllJoinedGroups(): List<Group> {
    return ContactList<Group>().toList()
    
    
/*另一种实现：
  val bots = Bot.instances
    val groups = mutableListOf<Group>()
    for (bot in bots) {
        for (group in bot.groups) {
            if (groups.contains(group).not()) groups.add(group)
        }
    }
    return groups*/
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