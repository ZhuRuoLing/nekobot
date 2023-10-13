package net.zhuruoling.nekobot.command.minecraft

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse

class YarnMethodCommand: Command() {

    override val commandPrefix: String
        get() = "!ym"

    override val helpMessage: String
        get() = "!ym <methodName> Optional[<version>]"

    override fun handle(commandMessage: CommandMessage): Message {
        return MessageResponse(commandMessage.scene, commandMessage.from){
            + "Yarn Method"
        }.toMessage()
    }

    override fun prepare() {
    }

    override fun finish() {
    }
}