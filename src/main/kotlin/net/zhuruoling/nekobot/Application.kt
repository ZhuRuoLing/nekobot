package net.zhuruoling.nekobot

import io.ktor.server.application.*
import net.zhuruoling.nekobot.command.CommandManager
import net.zhuruoling.nekobot.command.status.StatusCommand
import net.zhuruoling.nekobot.plugins.*

fun main(args: Array<String>) {
    CommandManager.apply {
        register(StatusCommand())
    }
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureSockets()
    configureRouting()
}
