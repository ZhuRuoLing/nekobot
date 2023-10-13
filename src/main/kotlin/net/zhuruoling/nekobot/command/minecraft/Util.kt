package net.zhuruoling.nekobot.command.minecraft

import net.zhuruoling.nekobot.mapping.MappingData
import net.zhuruoling.nekobot.mapping.MappingRepository
import net.zhuruoling.nekobot.mcversion.McVersionRepo
import java.nio.file.Path

private var prepared = false
val mappingRepository = MappingRepository(Path.of("./data"))
val versionRepository = McVersionRepo()
val namespaces = mutableListOf("official","intermediary","yarn","mojmap","srg","mcp")
fun mappingPrepare(){
    if (!prepared)return

    prepared = true
}

fun getMappingData(repo: MappingRepository, mcVersion: String): MappingData? {
    return repo.getMappingData(mcVersion)
}
