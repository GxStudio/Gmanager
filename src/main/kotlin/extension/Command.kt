package cc.gxstudio.gmanager.extension

import net.mamoe.mirai.console.command.Command
import net.mamoe.mirai.console.permission.PermissionId

val Command.fullPermissionIdString: String
    get() = permission.id.run { "$namespace.$name" }
val Command.permissionIdString: String
    get() = permission.id.name
val Command.PermissionId: PermissionId
    get() = permission.id
