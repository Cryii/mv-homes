package dev.spozap.mvhomes.managers

import dev.spozap.mvhomes.models.Home
import dev.spozap.mvhomes.repositories.HomesRepository
import dev.spozap.mvhomes.repositories.impl.HomeRepositoryImpl
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

class HomesManager {

    private val homeRepository: HomesRepository = HomeRepositoryImpl()
    private val data: MutableMap<UUID, MutableList<Home>> = mutableMapOf()

    fun loadPlayerInfo(player: Player) {

        val homes = homeRepository.getPlayerHomes(player)
        data[player.uniqueId] = homes.toMutableList()

    }

    fun savePlayersData() {
        data.forEach { (uuid, homes) -> homeRepository.savePlayerHomes(uuid, homes) }
        homeRepository.saveConfig()
    }

    fun savePlayerData(player: Player) {
        data[player.uniqueId].let {
            homeRepository.savePlayerHomes(player.uniqueId, it!!.toList())
            data.remove(player.uniqueId)
            homeRepository.saveConfig()
        }
    }

    fun addHome(player: Player, home: Home) {
        val homeList = data.getOrPut(player.uniqueId) { mutableListOf() }

        val existingHomeIndex = homeList.indexOfFirst { it.id == home.id }

        if (existingHomeIndex != -1) {
            homeList[existingHomeIndex] = home
        } else {
            homeList.add(home)
        }

        data[player.uniqueId]!!.map { home -> println("El home tiene nombre ${home.id} y coords ${home.location}") }

    }

    fun getUserHomes(player: Player): List<Home> {
        return data[player.uniqueId] ?: emptyList()
    }

    fun teleportToHome(player: Player, homeId: String) {
        val homes = data[player.uniqueId] ?: emptyList()
        println("la id del home $homeId")
        val homeIndex = homes.indexOfFirst { it.id == homeId }

        if (homeIndex == -1) {
            player.sendMessage("Home does not exist")
            return
        }

        val home = data[player.uniqueId]!![homeIndex]
        player.teleport(home.location)

    }


}