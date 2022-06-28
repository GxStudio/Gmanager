package cc.gxstudio.gmanager.http.api

import cc.gxstudio.gmanager.config.KtorConfig
import cc.gxstudio.gmanager.http.api.routes.callbackRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun startServer() {
    val port = KtorConfig.port
    val host = KtorConfig.ip
    
    embeddedServer(Netty, 33899, "127.0.0.1") {
        configureRouting()
    }.start()
}

fun Application.configureRouting() {
    routing {
        callbackRouting()
    }
}


