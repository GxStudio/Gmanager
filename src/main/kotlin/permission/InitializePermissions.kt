package cc.gxstudio.gmanager.permission

import cc.gxstudio.gmanager.globalvar.namespace
import cc.gxstudio.gmanager.logutil.Log
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.console.permission.PermissionService
import net.mamoe.mirai.contact.Group

object InitializePermissions {
    fun groupPermission(group:Group){
        for (a in PERMISSION_LIST_COMMAND_NORMAL){
            Log.v("${group.id}.${a.key}","InitializePermissions-groupPermission")
            PermissionService.INSTANCE.register(PermissionId(namespace,"${group.id}.${a.key}"),"测试权限")
        }
  }
}