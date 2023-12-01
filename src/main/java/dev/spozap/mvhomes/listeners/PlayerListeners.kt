package dev.spozap.mvhomes.listeners

import dev.spozap.mvhomes.MvHomes
import dev.spozap.mvhomes.managers.HomesManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListeners : Listener {

    private var homesManager : HomesManager = MvHomes.homesManager

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {

        val player : Player = event.player
        homesManager.loadPlayerInfo(player)

    }


}