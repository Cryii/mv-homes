package dev.spozap.mvhomes.repositories.impl

import dev.spozap.mvhomes.models.Home
import dev.spozap.mvhomes.repositories.HomesRepository
import dev.spozap.mvhomes.utils.ConfigurationFile
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import java.util.UUID

class HomeRepositoryImpl : HomesRepository {

    private val homesData = ConfigurationFile("data")
    private var homesConfigurationFile: FileConfiguration

    init {
        homesData.setup()
        homesConfigurationFile = homesData.fileConfiguration

        if (homesConfigurationFile.getConfigurationSection("players-data") == null) {
            homesConfigurationFile.createSection("players-data")
        }
    }

    override fun getPlayerHomes(player: Player): List<Home> {
        return listOf()
    }

    override fun savePlayerHomes(uuid: UUID, homes: List<Home>) {
        if (homes.isEmpty()) return

        val playerPath = "players-data.$uuid"
        val playerConfigSection = homesConfigurationFile.getConfigurationSection(playerPath)
                ?: homesConfigurationFile.createSection(playerPath)

        homes.forEach { home ->
            val homePath = home.id

            val homeSection = playerConfigSection.getConfigurationSection(homePath)
                    ?: playerConfigSection.createSection(homePath)

            homeSection.set("x", home.location.x)
            homeSection.set("y", home.location.y)
            homeSection.set("z", home.location.z)
            homeSection.set("world", home.location.world.name)

        }

    }




    override fun deletePlayerHome() {
        TODO("Not yet implemented")
    }

    override fun saveConfig() {
        homesData.save()
    }


}