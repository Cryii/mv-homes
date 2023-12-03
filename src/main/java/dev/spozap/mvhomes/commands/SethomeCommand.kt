package dev.spozap.mvhomes.commands

import dev.spozap.mvhomes.MvHomes
import dev.spozap.mvhomes.managers.HomesManager
import dev.spozap.mvhomes.models.Home
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class SethomeCommand : TabExecutor {

    private val homesManager: HomesManager = MvHomes.homesManager

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String> {
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        if (sender !is Player) return true

        val sethomePerm: String = sender.effectivePermissions.find { ep -> ep.permission.contains(Permissions.SethomePermissions.permission) }?.permission ?: ""

        if (sethomePerm.isEmpty() && !sender.isOp && !sender.hasPermission("*")) {
            sender.sendMessage("No tienes permisos para ejecutar este comando")
            return true
        }

        if (args.size != 1) {
            sender.sendMessage("Argumentos inválidos")
        }

        val numHomes = sethomePerm.split(".").lastOrNull()
        val playerHomes = homesManager.getUserHomes(sender)
        val homeId = args[0]

        val playerLocation = sender.location
        val home = Home(homeId, playerLocation)

        // Permission is mvhomes.sethome, unlimited homes
        if (numHomes?.toIntOrNull() == null) {
            homesManager.addHome(sender, home)
            return true
        }

        if (playerHomes.size >= numHomes.toInt() && !homesManager.isHomeRepeated(sender, homeId)) {
            sender.sendMessage("Has llegado al máximo de homes")
            return true
        }

        sender.sendMessage("Location: $playerLocation")
        homesManager.addHome(sender, home)

        return true

    }
}