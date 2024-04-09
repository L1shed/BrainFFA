package fr.lyxiz.brainffa

import fr.lyxiz.brainffa.listener.PlayerListener
import fr.mrmicky.fastboard.FastBoard
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI
import me.filoghost.holographicdisplays.api.hologram.Hologram
import me.filoghost.holographicdisplays.api.internal.HolographicDisplaysAPIProvider
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Statistic
import org.bukkit.plugin.java.JavaPlugin

class BrainFFA : JavaPlugin() {
    companion object {
        lateinit var instance: BrainFFA
    }

    override fun onEnable() {
        instance = this
        server.pluginManager.registerEvents(PlayerListener(),this)
        val taskId = server.scheduler.scheduleSyncRepeatingTask(this, {
            for (board in boards.values) {
                board.update()
            }
        }, 0, 20)

        killstreakHologram = HolographicDisplaysAPI.get(this).createHologram(Location(Bukkit.getWorld("world"), 0.0, 85.0, 0.0))
        killstreakHologram.refresh()
    }

    private fun FastBoard.update() {
        val playerStats = playerStatsMap.getOrPut(player) { PlayerStats() }
        updateLines(
            "",
            "Kills: " + (playerStatsMap[player]?.kills ?: 0),
            "Morts: " + (playerStatsMap[player]?.deaths ?: 0),
            "Killstreak: " + (playerStatsMap[player]?.killstreak ?: 0),
            ""
        )
    }
}