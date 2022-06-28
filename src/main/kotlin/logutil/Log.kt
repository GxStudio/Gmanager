package cc.gxstudio.gmanager.logutil

import cc.gxstudio.gmanager.PluginMain

@Suppress("unused")
object Log {
    
    private const val VERBOSE = 1
    private const val DEBUG = 2
    private const val INFO = 3
    private const val WARN = 4
    private const val ERROR = 5
    private var level = VERBOSE
    
    private val MiraiLog = PluginMain.logger
    
    /** 细节*/
    fun v(msg: String, title: String? = null): Log {
        if (level <= VERBOSE) MiraiLog.verbose(cw(msg, title))
        return this
    }
    
    /** 调试*/
    fun d(msg: String, title: String? = null): Log {
        if (level <= DEBUG) MiraiLog.debug(cw(msg, title))
        return this
    }
    
    /** 信息*/
    fun i(msg: String, title: String? = null): Log {
        if (level <= INFO) MiraiLog.info(cw(msg, title))
        return this
    }
    
    /** 警告*/
    fun w(msg: String, title: String? = null): Log {
        if (level <= WARN) MiraiLog.warning(cw(msg, title))
        return this
    }
    
    /** 报错*/
    fun e(msg: String, title: String? = null): Log {
        if (level <= ERROR) MiraiLog.error(cw(msg, title))
        return this
    }
    
    private fun cw(msg: String, title: String?): String = if (title == null) msg else "[$title]$msg"
    
    
}

