package dev.spozap.mvhomes.managers

import dev.spozap.mvhomes.models.Home
import dev.spozap.mvhomes.repositories.HomesRepository
import dev.spozap.mvhomes.repositories.impl.HomeRepositoryImpl
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
        data.forEach{ (uuid, homes) -> homeRepository.savePlayerHomes(uuid, homes) }
        homeRepository.saveConfig()
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


}