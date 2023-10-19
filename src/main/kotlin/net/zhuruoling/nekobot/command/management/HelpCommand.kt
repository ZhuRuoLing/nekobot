package net.zhuruoling.nekobot.command.management

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandManager
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.config.GroupRuleSetting
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse
import net.zhuruoling.nekobot.message.MessageType

class HelpCommand :Command() {
    override val commandPrefix: String
        get() = "!help"

    override val helpMessage: String
        get() = "!help"

    override fun handle(commandMessage: CommandMessage): Message {
//        if (commandMessage.from != MessageType.PRIVATE || commandMessage.scene !in config.operator) {
//            return Message(commandMessage.scene, commandMessage.from, "", status = false, forward = false)
//        }
        return MessageResponse(commandMessage.scene, commandMessage.from){
            + "**Commands**"
            +""
            CommandManager.commands.forEach {
                if (commandMessage.from == MessageType.PRIVATE || GroupRuleSetting.commandEnabledFor(commandMessage.scene, it.key)) {
                    +it.value.helpMessage
                }
            }
        }.toMessage()
    }
}