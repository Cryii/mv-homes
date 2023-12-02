package dev.spozap.mvhomes.inventories

import dev.spozap.mvhomes.MvHomes
import dev.spozap.mvhomes.models.Home
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class HomeMenuInventoryHolder(homes: List<Home>) : InventoryHolder {

    private var homesMenu: Inventory = Bukkit.createInventory(this, InventoryType.CHEST, Component.text("Mis homes"))

    init {
        homes.forEachIndexed { index, home ->
            val homeItem = ItemStack(Material.BLACK_BED)
            val homeItemMeta = homeItem.itemMeta
            val homeItemPdc = homeItemMeta.persistentDataContainer

            homeItemPdc.set(NamespacedKey(MvHomes.plugin, "mvhomes_id"), PersistentDataType.STRING, home.id)

            homeItemMeta.displayName(Component.text(home.id).color(NamedTextColor.AQUA))
            homeItem.setItemMeta(homeItemMeta)

            inventory.setItem(index, homeItem)

        }

    }

    override fun getInventory(): Inventory {
        return this.homesMenu
    }



}