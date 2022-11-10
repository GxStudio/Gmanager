package cc.gxstudio.gmanager.extension

import cc.gxstudio.gmanager.command.groupExist
import cc.gxstudio.gmanager.globalvar.namespace
import cc.gxstudio.gmanager.logutil.Log
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.contact.Group

/**@see CommandSender.hasNormalCommandPermission
 * */
suspend fun CommandSender.dontHasNormalCommandPermission(command: Command, group: Group?): Boolean =
    !hasNormalCommandPermission(command, group)

fun CommandSender.groupExist(): Boolean = if (groupExist(this.getGroupOrNull())) {
    Log.v("群组存在且已被开启。")
    true
} else {
    Log.i("群组不存在或未被开启。")
    false
}


/** 用于检查基础指令(指向群的指令)使用者是否拥有权限
 *
 * 顺便检查私聊/控制台情况下是否提供群号
 * */
suspend fun CommandSender.hasNormalCommandPermission(command: Command, group: Group?): Boolean {
    return when {
        isConsole()   -> {//控制台执行此分支
            Log.v("控制台调用，返回正常", "hasNormalCommandPermission-isConsole")
            true
        }
        
        group != null -> {//群不为空(群内用户调用)执行此分支
            
            Log.v("群内用户调用，进行判断", "hasNormalCommandPermission-getGroupOrNull")
            
            val groupid = group.id
            val permissionId = PermissionId("$namespace.$groupid", command.primaryName)
            Log.v(permissionId.toString(), "hasNormalCommandPermission-permissionId.toString()")
            
            val hasPermission = hasPermission(permissionId)
            
            Log.i("用户拥有权限情况:$hasPermission", "hasNormalCommandPermission-getGroupOrNull")
            hasPermission
        }
        
        else          -> {
            Log.v("用户（私聊/控制台）大概率未提供群ID", "hasNormalCommandPermission")
            sendMessage("私聊或控制台调用指令${CommandManager.commandPrefix}${command.primaryName}需要加入群号。")
            false
        }
    }
    
    
}