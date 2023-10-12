package net.zhuruoling.nekobot.command.status

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse
import net.zhuruoling.nekobot.util.getVersionInfoString

class StatusCommand : Command() {

    override val commandPrefix: String = "!stat"
    override val helpMessage: String = "!stat"

    override fun handle(commandMessage: CommandMessage): Message =
        MessageResponse(commandMessage.scene, commandMessage.from) {
            + "${System.currentTimeMillis()} NekoBot ${getVersionInfoString()}"
        }.toMessage()

}