package net.zhuruoling.nekobot.message

import kotlinx.serialization.Serializable

@Serializable
data class Message(val scene: String, val messageType: MessageType, val messagePlain: String)

enum class MessageType {
    GROUP, PRIVATE
}

class MessageResponse(val scene: String, val messageType: MessageType) {
    val messageText = StringBuilder()

    operator fun String.unaryPlus(){
        messageText.append(this)
    }

    fun appendLine(string: String){
        messageText.append(string + '\n')
    }

    fun toMessage() = Message(scene, messageType, messageText.toString())
}

fun <T> MessageResponse(scene: String, messageType: MessageType, fn: MessageResponse.() -> T):T {
    return fn(MessageResponse(scene, messageType))
}