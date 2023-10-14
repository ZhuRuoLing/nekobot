package net.zhuruoling.nekobot.command.minecraft

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse

class VersionCacheCommand : Command() {

    override val commandPrefix: String
        get() = "!vc"

    override val helpMessage: String
        get() = "!vc [p | {[a | add] [<version> | latest | latestStable]}]"

    override fun handle(commandMessage: CommandMessage): Message {
        return MessageResponse(commandMessage.scene, commandMessage.from) {
            val scene = commandMessage[0] ?: run {
                +helpMessage
                return@MessageResponse
            }
            when (scene) {
                "a","add" -> {
                    val version = versionRepository.resolve(commandMessage[1]) ?: run {
                        + "Expected version: [<version> | latest | latestStable]"
                        return@MessageResponse
                    }
                    if (version !in mappingRepository.cachedVersions) {
                        mappingRepository.getMappingData(version)
                        +"Created Mapping cache for version $version"
                        return@MessageResponse
                    }
                    +"Version $version already cached."
                }
                "p" -> {
                    +"**Cached Version Mapping Data**"
                    +""
                    for (version in mappingRepository.cachedVersions) {
                        +version
                    }
                }
                else -> +helpMessage
            }
        }.toMessage()
    }

    override fun prepare() {
    }

    override fun finish() {
    }
}