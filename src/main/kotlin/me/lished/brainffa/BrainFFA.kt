package me.lished.brainffa

import me.lished.brainffa.listener.PlayerListener
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.java.JavaPlugin

class BrainFFA : JavaPlugin() {
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(PlayerListener(),this)
    }
}