package dev.spozap.mvhomes

import dev.spozap.mvhomes.commands.SethomeCommand
import dev.spozap.mvhomes.managers.HomesManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class MvHomes : JavaPlugin() {

    companion object {
        lateinit var plugin: MvHomes
        lateinit var homesManager: HomesManager
    }


    override fun onEnable() {
        // Plugin startup logic
        plugin = this

        saveDefaultConfig()

        homesManager = HomesManager()

        Bukkit.getPluginCommand("sethome")!!.setExecutor(SethomeCommand())

    }

    override fun onDisable() {
        homesManager.savePlayersData()
        // Plugin shutdown logic
    }



}
