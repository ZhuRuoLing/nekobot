package net.zhuruoling.nekobot.command

import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse
import org.slf4j.LoggerFactory

object CommandManager {
    val commands = mutableMapOf<String,Command>()
    val logger = LoggerFactory.getLogger("CommandManager")
    fun register(command: Command) {
        commands[command.commandPrefix] = command
    }

    fun prepareCommands(){
        commands.forEach { _,c -> c.prepare() }
    }

    fun finishCommands(){
        commands.forEach { _,c -> c.finish() }
    }

    fun run(input:Message):Message{
        val commandMessage = CommandMessage(input)
        return if (commands.containsKey(commandMessage.commandPrefix)){
            try{
                commands[commandMessage.commandPrefix]!!(commandMessage)
            }catch (e:Exception){
                logger.error("Exception occurred while running command ${input.messagePlain}", e)
                MessageResponse(input.scene, input.messageType){
                    + "Server Internal Error."
                    + input.messagePlain
                    + "~~~"
                    + e.toString()
                }.toMessage(true)
            }
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