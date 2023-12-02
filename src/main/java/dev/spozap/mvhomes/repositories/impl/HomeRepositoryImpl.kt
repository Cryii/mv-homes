package dev.spozap.mvhomes.repositories.impl

import dev.spozap.mvhomes.models.Home
import dev.spozap.mvhomes.repositories.HomesRepository
import dev.spozap.mvhomes.utils.ConfigurationFile
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import java.util.UUID

class HomeRepositoryImpl : HomesRepository {

    private val homesYmlData = ConfigurationFile("data")
    private var homesConfigurationFile: FileConfiguration


    init {
        homesYmlData.setup()
        homesConfigurationFile = homesYmlData.fileConfiguration

        if (homesConfigurationFile.getConfigurationSection("players-data") == null) {
            homesConfigurationFile.createSection("players-data")
        }
    }

    override fun getPlayerHomes(player: Player): List<Home> {
        val playerPath = "players-data.${player.uniqueId}"

        val homes = mutableListOf<Home>()

        if (homesConfigurationFile.getConfigurationSection(playerPath) == null) {
            return homes
        }

        val playerSection = homesConfigurationFile.getConfigurationSection(playerPath)
        val homeKeys = playerSection?.getKeys(false)

        homeKeys?.map { homeKey ->

            val homeSection = playerSection.getConfigurationSection(homeKey)


            val x = homeSection!!.getDouble("x")
            val y = homeSection.getDouble("y")
            val z = homeSection.getDouble("z")
            val worldName  = homeSection.getString("world")

            Bukkit.getWorld(worldName!!).let {
                println("$homeKey $x $y $z")
                val location = Location(it, x, y, z)
                val home = Home(homeKey, location)
                homes.add(home)
            }

        }

        return homes

    }

    override fun savePlayerHomes(uuid: UUID, homes: List<Home>) {
        if (homes.isEmpty()) return

        val playerPath = "players-data.$uuid"
        val playerConfigSection = homesConfigurationFile.getConfigurationSection(playerPath)
                ?: homesConfigurationFile.createSection(playerPath)

        val currentKeys = playerConfigSection.getKeys(false)

        currentKeys.filter { key -> homes.none { it.id == key } }
                .forEach { key -> playerConfigSection.set(key, null) }

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
        homesYmlData.save()
    }


}