package net.zhuruoling.nekobot.command.management

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.config.GroupRuleSetting
import net.zhuruoling.nekobot.config.config
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse
import net.zhuruoling.nekobot.message.MessageType

class GroupCommand : Command() {

    override val commandPrefix: String
        get() = "!g"

    override val helpMessage: String
        get() = "!g [enable | disable | e | d] <group> "

    override fun handle(commandMessage: CommandMessage): Message {
        if (commandMessage.from != MessageType.PRIVATE || commandMessage.scene !in config.operator) {
            return Message(commandMessage.scene, commandMessage.from, "", status = false, forward = false)
        }
        return MessageResponse(commandMessage.scene, commandMessage.from) {
            if (commandMessage.args.isEmpty()) {
                +helpMessage
                return@MessageResponse
            }
            val action = commandMessage[0] ?: run {
                +"Expected action: [enable | disable | e | d]"
                return@MessageResponse
            }
            val group = commandMessage[1] ?: run {
                +"Expected group"
                return@MessageResponse
            }
            when (action) {
                "e", "enable" -> {
                    GroupRuleSetting.enableBotForGroup(group)
                    +"Enabled bot for group $group"
                }

                "d", "disable" -> {
                    GroupRuleSetting.disableBotForGroup(group)
                    +"Disabled bot for group $group"
                }

                else -> {
                    +"Expected action: [enable | disable | e | d]"
                }
            }
        }.toMessage()
    }
}