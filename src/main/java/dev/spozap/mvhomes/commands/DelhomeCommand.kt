package dev.spozap.mvhomes.commands

import dev.spozap.mvhomes.MvHomes
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class DelhomeCommand : TabExecutor {

    val homesManager = MvHomes.homesManager
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        TODO("Not yet implemented")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) return false

        val player: Player = sender

        if (!player.hasPermission(Permissions.DelhomePermissions.permission)) {
            player.sendMessage("No tienes permisos para ejecutar este comando")
            return true
        }

        if (args.size != 1) {
            player.sendMessage("Demasiados argumentos, usa /delhome <home>")
            return true
        }

        val homeId = args[0]

        homesManager.removeHome(player, homeId)

        return true

    }

}