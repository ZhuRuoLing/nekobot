package net.zhuruoling.nekobot.command.minecraft

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse

class YarnClassCommand : Command() {
    override val commandPrefix: String = "!yc"
    override val helpMessage: String = "!yc <className> Optional[<version | latest | latestStable>]"
    override fun handle(commandMessage: CommandMessage): Message {
        return MessageResponse(commandMessage.scene, commandMessage.from) {
            val version = versionRepository.resolve(commandMessage[1]) ?: run {
                +"Expected Minecraft Nersion"
                return@MessageResponse
            }
            val data = mappingRepository.getMappingData(version)
            val className = commandMessage[0] ?: run {
                +"Expected Class Name"
                return@MessageResponse
            }
            val results = data.findClasses(className, data.resolveNamespaces(namespaces, false))
            if (results.isEmpty()){
                + "no matches for the given class name, MC version and query namespace"
                return@MessageResponse
            }
            +"${data.mcVersion} matches"
            for (result in results) {
                +"**Names**"
                +""
                for (namespace in namespaces) {
                    val res = result.getName(namespace)
                    + "**$namespace**: $res"
                }
                +""
                + "**Yarn Access Widener**: accessible\tclass\t${result.getName("yarn")}"
            }
            +"query ns: ${namespaces.joinToString(",")}"
        }.toMessage()
    }

    override fun prepare() {

    }

    override fun finish() {

    }
}