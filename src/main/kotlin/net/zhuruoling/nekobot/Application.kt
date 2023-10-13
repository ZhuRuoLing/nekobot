package net.zhuruoling.nekobot

import io.ktor.server.application.*
import net.zhuruoling.nekobot.command.CommandManager
import net.zhuruoling.nekobot.command.minecraft.YarnClassCommand
import net.zhuruoling.nekobot.command.minecraft.mappingRepository
import net.zhuruoling.nekobot.command.minecraft.versionRepository
import net.zhuruoling.nekobot.command.status.StatusCommand
import net.zhuruoling.nekobot.plugins.*
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

val executor = Executors.newFixedThreadPool(4)
val scheduler = Executors.newScheduledThreadPool(2)
private val logger = LoggerFactory.getLogger("Main")
fun main(args: Array<String>) {
    logger.info("Updating Mapping version.")
    mappingRepository.updateVersion()
    logger.info("Updating Minecraft version.")
    versionRepository.update()
    scheduler.scheduleWithFixedDelay(
        {
            mappingRepository.updateVersion()
            versionRepository.update()
        },
        0,
        2,
        TimeUnit.MINUTES
    )
    logger.info("Registering Command.")
    CommandManager.apply {
        register(StatusCommand())
        register(YarnClassCommand())
    }
    logger.info("Launching Application Engine.")
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureSockets()
    configureRouting()
}
