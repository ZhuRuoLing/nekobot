package net.zhuruoling.nekobot.command.minecraft

import net.zhuruoling.nekobot.command.Command
import net.zhuruoling.nekobot.command.CommandMessage
import net.zhuruoling.nekobot.message.Message
import net.zhuruoling.nekobot.message.MessageResponse

class YarnMethodCommand : Command() {

    override val commandPrefix: String
        get() = "!ym"

    override val helpMessage: String
        get() = "!ym <methodName> Optional[<version> | latest | latestStable]"

    override fun handle(commandMessage: CommandMessage): Message {
        return MessageResponse(commandMessage.scene, commandMessage.from) {
            if (commandMessage.args.isEmpty()) {
                +helpMessage
                return@MessageResponse
            }
            val version = versionRepository.resolve(commandMessage[1]) ?: run {
                +"Expected Minecraft Version"
                return@MessageResponse
            }
            val data = mappingRepository.getMappingData(version)
            val className = commandMessage[0] ?: run {
                +"Expected Method Name"
                return@MessageResponse
            }
            val results = data.findMethods(className, data.resolveNamespaces(namespaces, false))
            if (results.isEmpty()) {
                +"no matches for the given method name, MC version and query namespace"
                return@MessageResponse
            }
            +"$version matches"
            for (result in results) {
                +"**Class Names**"
                +""
                for (namespace in namespaces) {
                    +"**$namespace:** ${result.owner.getName(namespace) ?: continue}"
                }
                +""
                +"**Method Names**"
                for (namespace in namespaces) {
                    +"**$namespace:** ${result.getName(namespace) ?: continue}"
                }
                +""
                +String.format(
                    """
                            **Yarn Method Descriptor**

                            %3${'$'}s
                            **Yarn Access Widener**

                            accessible${'\t'}method${'\t'}%1${'$'}s${'\t'}%2${'$'}s${'\t'}%3${'$'}s
                            **Yarn Mixin Target**

                            L%1${'$'}s;%2${'$'}s%3${'$'}s

                            """.trimIndent(),
                    result.owner.getName("yarn")?: continue,
                    result.getName("yarn")?: continue,
                    result.getDesc("yarn")?: continue
                )

            }
            +"query ns: ${namespaces.joinToString(",")}"
        }.toMessage(forward = true)
    }
}