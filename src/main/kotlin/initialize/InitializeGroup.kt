package cc.gxstudio.gmanager.initialize

import cc.gxstudio.gmanager.permission.InitializePermissions
import cc.gxstudio.gmanager.permission.LuckPerms
import net.mamoe.mirai.contact.ContactList
import net.mamoe.mirai.contact.Group

object InitializeGroup {
    private val initializedGroup = arrayListOf<Group>()
    fun list(group: ContactList<Group>) {
        for (i in group) if (!initializedGroup.contains(i)) {
            main(i)
        }
    }
    
    fun main(group: Group) {
        InitializePermissions.groupPermission(group)
        LuckPerms.createQQPermGroups(group.id)
        initializedGroup.add(group)
    }
    fun permission(group: Group){
    
    
    }
    
    
}

