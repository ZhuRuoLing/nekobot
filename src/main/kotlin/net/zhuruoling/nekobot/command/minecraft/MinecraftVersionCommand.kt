package net.zhuruoling.nekobot.command.minecraft

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse

class MinecraftVersionCommand : Command() {

    override val commandPrefix: String
        get() = "!mcv"

    override val helpMessage: String
        get() = "!mcv Optional[help | latest | latestStable | <version>]"
    override fun handle(commandMessage: CommandMessage): Message {
        if (commandMessage.args.isEmpty()){

        }
        return MessageResponse(commandMessage.scene, commandMessage.from) {

        }.toMessage()
    }

    override fun prepare() {

    }

    override fun finish() {

    }
}