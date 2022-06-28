package cc.gxstudio.gmanager.permission

import cc.gxstudio.gmanager.globalvar.namespace
import cc.gxstudio.gmanager.logutil.Log
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.group.GroupManager
import net.luckperms.api.node.Node


fun main() {
}

object LuckPerms {
    lateinit var luckPermsApi: net.luckperms.api.LuckPerms
    lateinit var groupmanager: GroupManager
    
    fun loadLuckPermsApi() {
        
        luckPermsApi = LuckPermsProvider.get()
        groupmanager = luckPermsApi.groupManager
        
        
    }
    
    fun createGroups() {
        groupmanager.createAndLoadGroup("root")
    }
    
    fun createQQPermGroups(groupid: Long) {
        groupmanager.apply {
            createAndLoadGroup("$groupid.administrators")//管理员
            createAndLoadGroup("$groupid.owner")//群主
        }
        
    }
    fun setDefaultPermission(){
        for (a in PERMISSION_LIST_COMMAND_NORMAL){
            Log.v(a.toString(),"setDefaultPermission")
            luckPermsApi.groupManager.modifyGroup("default"){
                it.data().add(
                    Node.builder("$namespace.${a.key}").build())
            }
        }
    }
}

