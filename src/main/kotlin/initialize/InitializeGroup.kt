package cc.gxstudio.gmanager.initialize
import cc.gxstudio.gmanager.config.*
import cc.gxstudio.gmanager.PluginMain.reload
import cc.gxstudio.gmanager.config.GroupConfig
import cc.gxstudio.gmanager.config.groupConfigList
import cc.gxstudio.gmanager.data.GroupData
import cc.gxstudio.gmanager.data.groupDataList
import cc.gxstudio.gmanager.data.load
import cc.gxstudio.gmanager.permission.InitializePermissions
import cc.gxstudio.gmanager.permission.LuckPerms
import net.mamoe.mirai.contact.ContactList
import net.mamoe.mirai.contact.Group

object InitializeGroup {
    val Groups = arrayListOf<Group>()
    fun list(group: ContactList<Group>) {
        for (i in group) if (!Groups.contains(i)) {
            main(i)
        }
    }
    
    fun main(group: Group) {
        InitializePermissions.groupPermission(group)
        LuckPerms.createQQPermGroups(group.id)
        if ((group.id in groupConfigList).not()) groupConfigList[group.id] = GroupConfig(group.id).load()
        if ((group.id in groupDataList).not()) groupDataList[group.id] = GroupData(group.id).load()
        
        Groups.add(group)
    }
    
    fun permission(group: Group) {
    
    
    }
    
    
}

