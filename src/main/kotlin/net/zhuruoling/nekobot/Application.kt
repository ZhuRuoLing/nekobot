package net.zhuruoling.nekobot

import io.ktor.server.application.*
import net.zhuruoling.nekobot.plugins.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureSockets()
    configureAdministration()
    configureRouting()
}
