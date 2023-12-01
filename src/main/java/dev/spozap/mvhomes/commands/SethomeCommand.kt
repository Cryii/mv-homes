package dev.spozap.mvhomes.commands

import dev.spozap.mvhomes.MvHomes
import dev.spozap.mvhomes.managers.HomesManager
import dev.spozap.mvhomes.models.Home
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class SethomeCommand : TabExecutor {

    private val homesManager : HomesManager = MvHomes.homesManager

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) return false

        if (!sender.hasPermission(Permissions.SethomePermissions.permission)) {
            sender.sendMessage("No tienes permisos para ejecutar este comando")
        }

        if (args.size != 1) {
            sender.sendMessage("Argumentos inválidos")
        }

        val homeName = args[0]
        val playerLocation = sender.location
        val home = Home(homeName, playerLocation)

        sender.sendMessage("Location: $playerLocation")

        homesManager.addHome(sender, home)

        return true

    }
}