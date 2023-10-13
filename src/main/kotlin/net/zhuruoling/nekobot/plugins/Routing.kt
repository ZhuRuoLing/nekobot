package net.zhuruoling.nekobot.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.zhuruoling.nekobot.command.CommandManager
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.util.getVersionInfoString
import org.slf4j.LoggerFactory

fun Application.configureRouting() {
    val logger = LoggerFactory.getLogger("Route")
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/ping") {
            call.respondText("PONG")
        }
        route("/nekobot") {
            get {
                return@get call.respondText("${System.currentTimeMillis()} NekoBot ${getVersionInfoString()}")
            }
            get("commands") {
                return@get call.respond(CommandManager.commandPrefixes)
            }
            post("message") {
                try {
                    val message = call.receive<Message>()
                    val response = CommandManager.run(message)
                    return@post call.respond(response)
                } catch (e: Exception) {
                    logger.error(e.stackTraceToString())
                }
            }
        }
    }
}
