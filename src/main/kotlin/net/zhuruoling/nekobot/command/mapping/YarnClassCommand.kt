package net.zhuruoling.nekobot.command.mapping

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.message.Message

class YarnClassCommand : Command() {
    override val commandPrefix: String = "!yc"
    override val helpMessage: String = "!yc <className> Optional[<version>]"
    override fun handle(commandMessage: CommandMessage): Message {
        TODO("Not yet implemented")
    }
}