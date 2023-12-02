package dev.spozap.mvhomes.commands

import dev.spozap.mvhomes.MvHomes
import dev.spozap.mvhomes.inventories.HomeMenuInventoryHolder
import dev.spozap.mvhomes.managers.HomesManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class HomesCommand : TabExecutor {

    private val homesManager: HomesManager = MvHomes.homesManager
    private val plugin: MvHomes = MvHomes.plugin

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        TODO("Not yet implemented")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        if (sender !is Player) return false

        if (!sender.hasPermission(Permissions.HomesPermissions.permission)) {
            sender.sendMessage("No tienes permisos para ejecutar este comando")
        }

        val homes = homesManager.getUserHomes(sender)

        val homeInvHolder = HomeMenuInventoryHolder(homes)

        sender.openInventory(homeInvHolder.inventory)

        return true
    }


}