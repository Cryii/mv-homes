package dev.spozap.mvhomes.listeners

import dev.spozap.mvhomes.MvHomes
import dev.spozap.mvhomes.inventories.HomeMenuInventoryHolder
import dev.spozap.mvhomes.managers.HomesManager
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.persistence.PersistentDataType

class PlayerListeners : Listener {

    private var homesManager: HomesManager = MvHomes.homesManager

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {

        val player: Player = event.player
        homesManager.loadPlayerInfo(player)

    }

    @EventHandler
    fun onMenuClick(event: InventoryClickEvent) {

        if (event.inventory.holder !is HomeMenuInventoryHolder) {
            return
        }

        event.isCancelled = true

        when (event.currentItem?.type) {
            Material.BLACK_BED -> {
                val homePdc = event.currentItem!!.itemMeta.persistentDataContainer
                val homeId = homePdc.get(NamespacedKey(MvHomes.plugin, "mvhomes_id"), PersistentDataType.STRING) ?: ""

                homesManager.teleportToHome(event.whoClicked as Player, homeId)
            }

            else -> {}

        }

    }


}