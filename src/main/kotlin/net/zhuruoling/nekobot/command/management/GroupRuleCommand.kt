package net.zhuruoling.nekobot.command.management

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandManager
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.config.GroupRuleSetting
import net.zhuruoling.nekobot.config.config
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse
import net.zhuruoling.nekobot.message.MessageType

class GroupRuleCommand : Command() {

    override val commandPrefix: String
        get() = "!gr"

    override val helpMessage: String
        get() = "!gr [enable | disable | e | d | p] <group> [<command> | ALL]"

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
            if (action == "p"){
                +"**Group Settings**"
                +""
                +"**Enabled Group:** ${config.allowedGroup.joinToString(", ")}"
                +""
                +"**Enabled Commands For Group:**"
                config.groupRules.forEach {
                    +"${it.key}: ${it.value.joinToString(", ")}"
                }
                return@MessageResponse
            }
            val group = commandMessage[1] ?: run {
                +"Expected group"
                return@MessageResponse
            }
            val command = commandMessage[2] ?: run {
                +"Expected command"
                return@MessageResponse
            }

            when (action) {
                "e", "enable" -> {
                    if (command == "ALL") {
                        for (c in CommandManager.commandPrefixes) {
                            GroupRuleSetting.enableCommandForGroup(group, c)
                            +"Enabled command $c for group $group"
                        }
                        return@MessageResponse
                    }
                    if ("+" in command) {
                        command.split("+").forEach {
                            if (it !in CommandManager.commands) {
                                +"Command $it not registered."
                                return@forEach
                            }
                            GroupRuleSetting.enableCommandForGroup(group, it)
                            +"Enabled command $it for group $group"
                        }
                        return@MessageResponse
                    }
                    if (command !in CommandManager.commands) {
                        +"Command $command not registered."
                        return@MessageResponse
                    }
                    GroupRuleSetting.enableCommandForGroup(group, command)
                    +"Enabled command $command for group $group"
                }

                "d", "disable" -> {
                    if (command == "ALL") {
                        for (c in CommandManager.commandPrefixes) {
                            GroupRuleSetting.enableCommandForGroup(group, c)
                            +"Disabled command $c for group $group"
                        }
                        return@MessageResponse
                    }
                    if ("+" in command) {
                        command.split("+").forEach {
                            if (it !in CommandManager.commands) {
                                +"Command $it not registered."
                                return@forEach
                            }
                            GroupRuleSetting.disableCommandForGroup(group, it)
                            +"Disabled command $it for group $group"
                        }
                        return@MessageResponse
                    }
                    if (command !in CommandManager.commands) {
                        +"Command $command not registered."
                        return@MessageResponse
                    }
                    GroupRuleSetting.disableCommandForGroup(group, command)
                    +"Disabled command $command for group $group"
                }
                else -> {
                    +"Expected action: [enable | disable | e | d | p]"
                }
            }
        }.toMessage()
    }

    override fun prepare() {

    }

    override fun finish() {

    }
}