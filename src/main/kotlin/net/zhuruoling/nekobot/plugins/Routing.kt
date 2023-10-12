package net.zhuruoling.nekobot.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.zhuruoling.nekobot.message.Message

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/ping") {
            call.respondText("PONG")
        }
        route("/nekobot"){
            get("status") {
                return@get call.respondText("${System.currentTimeMillis()} NekoBot RUNNING")
            }
            get {
                return@get call.respondText("${System.currentTimeMillis()}")
            }
            post("message"){
                val message = call.receive<Message>()

            }
        }
    }
}
