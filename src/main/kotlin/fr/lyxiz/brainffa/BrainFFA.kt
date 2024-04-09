package fr.lyxiz.brainffa

import fr.lyxiz.brainffa.listener.PlayerListener
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

class BrainFFA : JavaPlugin() {
    companion object {
        lateinit var instance: BrainFFA
    }

    override fun onEnable() {
        instance = this
        saveConfig()
        server.pluginManager.registerEvents(PlayerListener(),this)
        server.scheduler.scheduleSyncRepeatingTask(this, {
            boards.update()
        }, 0, 20)

        killstreakHologram = HolographicDisplaysAPI.get(this).createHologram(Location(Bukkit.getWorld("world"), 0.0, 85.0, 0.0))
        killstreakHologram.refresh()
    }
}