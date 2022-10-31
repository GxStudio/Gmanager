package cc.gxstudio.gmanager.management

import kotlinx.serialization.Serializable

@Serializable
sealed class Penalties{
    data class Mute(val time: Int): Penalties()
    data class Kick(val reason: String): Penalties()
    data class Ban(val time: Int): Penalties()
    data class Warn(val reason: String): Penalties()
}

