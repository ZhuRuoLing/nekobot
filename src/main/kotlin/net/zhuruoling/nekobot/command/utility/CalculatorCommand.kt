package net.zhuruoling.nekobot.command.utility

import com.github.murzagalin.evaluator.Evaluator
import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse

class CalculatorCommand : Command() {

    override val commandPrefix: String
        get() = "!calc"

    override val helpMessage: String
        get() = "!calc <expression>"

    private val evaluator = Evaluator()

    override fun handle(commandMessage: CommandMessage): Message {
        return MessageResponse(commandMessage.scene, commandMessage.from) {
            val expression = commandMessage.args.run {
                if (isEmpty()){
                    +helpMessage
                    return@MessageResponse
                }
                joinToString(" ")
            }
            try {
                +(expression + " = " + evaluator.evaluateDouble(expression).toString())
            } catch (e: Exception) {
                +expression
                +"~~~"
                +(e.message ?: "Expected expression")
            }
        }.toMessage()
    }
}