package net.zhuruoling.nekobot.command

import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse

object CommandManager {
    val commands = mutableMapOf<String,Command>()

    fun register(command: Command) {
        commands[command.commandPrefix] = command
    }

    fun run(input:Message):Message{
        val commandMessage = CommandMessage(input)
        return if (commands.containsKey(commandMessage.commandPrefix)){
            commands[commandMessage.commandPrefix]!!(commandMessage)
        }else{
            MessageResponse(input.scene, input.messageType){
                + "Command not found."
                + input.messagePlain
                + "~~~"
            }.toMessage(false)
        }
    }

    val commandPrefixes
        get() = commands.keys
}