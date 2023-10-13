package net.zhuruoling.nekobot.command.management

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandManager
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.config.config
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse
import net.zhuruoling.nekobot.message.MessageType

class CommandCommand :Command() {
    override val commandPrefix: String
        get() = "!c"

    override val helpMessage: String
        get() = "!c"

    override fun handle(commandMessage: CommandMessage): Message {
//        if (commandMessage.from != MessageType.PRIVATE || commandMessage.scene !in config.operator) {
//            return Message(commandMessage.scene, commandMessage.from, "", status = false, forward = false)
//        }
        return MessageResponse(commandMessage.scene, commandMessage.from){
            + "**Commands**"
            +""
            CommandManager.commands.forEach {
                + it.value.helpMessage
            }
        }.toMessage()
    }

    override fun prepare() {

    }

    override fun finish() {

    }
}