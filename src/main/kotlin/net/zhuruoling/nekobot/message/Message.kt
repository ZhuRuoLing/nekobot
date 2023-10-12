package net.zhuruoling.nekobot.message

import kotlinx.serialization.Serializable

@Serializable
data class Message(val scene: String, val messageType: MessageType, val messagePlain: String, val status:Boolean = true)

enum class MessageType {
    GROUP, PRIVATE
}

class MessageResponse(val scene: String, val messageType: MessageType) {
    val messageText = StringBuilder()

    operator fun String.unaryPlus(){
        messageText.append(this + "\n")
    }

    fun append(string: String){
        messageText.append(string)
    }

    fun toMessage() = Message(scene, messageType, messageText.toString())

    fun toMessage(status: Boolean) = Message(scene, messageType, messageText.toString(), status)
}

fun MessageResponse(scene: String, messageType: MessageType, fn: MessageResponse.() -> Unit):MessageResponse {
    return MessageResponse(scene, messageType).apply(fn)
}