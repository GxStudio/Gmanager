package cc.gxstudio.gmanager.management

import kotlinx.serialization.Serializable

@Serializable
sealed class Penalties {
    interface Penaltie
    @Serializable
    class Mute(val time: Int) :Penaltie
    
    @Serializable
    class Kick(val reason: String) :Penaltie
    
    @Serializable
    class Ban(val time: Int) :Penaltie
    
    
    @Serializable
    class Warn(val reason: String) :Penaltie
    
    @Serializable
    class INGORE  :Penaltie
    
}

