package net.zhuruoling.nekobot.command.minecraft

import net.zhuruoling.nekobot.mapping.MappingData
import net.zhuruoling.nekobot.mapping.MappingRepository
import net.zhuruoling.nekobot.mcversion.McVersionRepo
import java.nio.file.Path

private var prepared = false
val mappingRepository = MappingRepository(Path.of("./data"))
val versionRepository = McVersionRepo()
val namespaces = mutableListOf("official","intermediary","yarn")
fun mappingPrepare(){
    if (!prepared)return

    prepared = true
}

fun getMappingData(repo: MappingRepository, mcVersion: String): MappingData? {
    return repo.getMappingData(mcVersion)
}

fun main() {
    mappingRepository.updateVersion()
    val version = "1.19.2"
    val data = mappingRepository.getMappingData(version)
    val className = "Text"

    val results = data.findClasses(className, data.resolveNamespaces(namespaces, false))
    for (result in results) {
        for (namespace in namespaces) {
            println("$namespace: ${result.getName(namespace)}")
        }
        println("Yarn access widener: accessible class ${result.getName("yarn")}")
    }

}
