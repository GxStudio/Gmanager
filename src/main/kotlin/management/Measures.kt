package cc.gxstudio.gmanager.management

import kotlinx.serialization.Serializable

@Serializable
data class Penalties (val type:PenaltyType,val time:Long = 10,val reason:String="")

@Serializable
enum class PenaltyType{
    MUTE,KICK,BAN,WARN,INGORE
}