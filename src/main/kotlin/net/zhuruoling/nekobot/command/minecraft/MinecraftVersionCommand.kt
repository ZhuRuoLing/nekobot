package net.zhuruoling.nekobot.command.minecraft

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.mcversion.MinecraftVersion
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse

class MinecraftVersionCommand: Command() {

    override val commandPrefix: String
        get() = "!mv"

    override val helpMessage: String
        get() = "!mv Optional[<version> | latest | latestStable]"

    override fun handle(commandMessage: CommandMessage): Message {
        return MessageResponse(commandMessage.scene, commandMessage.from){
            +"**Minecraft Version**"
            +""
            val version = commandMessage[0] ?: run {
                +"**Latest Stable Version:** ${MinecraftVersion.latestStableVersion}"
                +"**Latest Snapshot Version:** ${MinecraftVersion.latestVersion}"
                return@MessageResponse
            }
            val minecraftVersion = versionRepository.resolve(version) ?: run {
                +"Expected version: [<version> | latest | latestStable]"
                return@MessageResponse
            }
            val versionData = MinecraftVersion[minecraftVersion]!!
            + "**Version:** ${versionData.id}"
            +"**Version Type:** ${versionData.type}"
            +"**Release Time:** ${versionData.releaseTime}"
            +"**Version Json Download Url:** ${versionData.url}"
        }.toMessage()
    }

    override fun prepare() {
    }

    override fun finish() {
    }
}