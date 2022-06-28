package cc.gxstudio.gmanager.extension

import cc.gxstudio.gmanager.globalvar.namespace
import cc.gxstudio.gmanager.logutil.Log
import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission

fun CommandSender.dontHasCommandPermission(command: Command): Boolean = !hasCommandPermission(command)

fun CommandSender.hasCommandPermission(command: Command): Boolean {
    return if (this.user == null) {
        Log.i("控制台调用，返回正常", "hasCommandPermission")
        true
    } else {
        Log.i("用户调用，进行判断", "hasCommandPermission")
        
        val groupid = this.getGroupOrNull()!!.id
        val permissionId = PermissionId("$namespace.$groupid", command.permissionIdString)
        Log.i(permissionId.toString(), "hasCommandPermission")
        
        val hasPermission = hasPermission(permissionId)
        
        Log.i("用户拥有权限情况:$hasPermission", "hasCommandPermission")
        return hasPermission
    }
}