package cc.gxstudio.gmanager.http.api.routes

import cc.gxstudio.gmanager.logutil.Log
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.callbackRouting() {
    route("/callback") {
        post {
            Log.e("", "POST")
                .v(call.receiveText(), "posttest")
        }
    }
    route("/api") {
        route("/set") {
            post {
                call.receiveText()
            }
        }
        route("/get") {
            post {
                
                call.respondText { "" }
            }
        }
        post { call.respond(HttpStatusCode.OK, "OK") }
    }
}

