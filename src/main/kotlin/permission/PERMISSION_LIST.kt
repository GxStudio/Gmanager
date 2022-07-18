package cc.gxstudio.gmanager.permission

import cc.gxstudio.gmanager.command.Commands
import cc.gxstudio.gmanager.extension.permissionIdString
import net.mamoe.mirai.console.command.Command

val PERMISSIONS_LIST = mutableMapOf<String, String>().apply {
    putAll(PERMISSION_LIST_COMMAND_NORMAL)
    putAll(PERMISSION_LIST_COMMAND_GLOBAL)
}

val PERMISSION_LIST_COMMAND_NORMAL: Map<String, String>
    get() = commandToMapOf(Commands.COMMAND_LIST)

val PERMISSION_LIST_COMMAND_GLOBAL: Map<String, String>
    get() = commandToMapOf(Commands.COMMAND_LIST_GLOBAL)

private fun commandToMapOf(list: List<Command>): MutableMap<String, String> {
    val map = mutableMapOf<String, String>()
    for (command in list) {
        map[command.primaryName] = command.description
    }
    return map
}
