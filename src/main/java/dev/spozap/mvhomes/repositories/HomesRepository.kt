package dev.spozap.mvhomes.repositories

import dev.spozap.mvhomes.models.Home
import org.bukkit.entity.Player
import java.util.UUID

interface HomesRepository {

    fun getPlayerHomes(player: Player) : List<Home>

    fun savePlayerHomes(uuid: UUID, homes: List<Home>) : Unit

    fun deletePlayerHome() : Unit

    fun saveConfig() : Unit

}