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
    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerListener(),this)
        server.scheduler.runTaskTimer(this, {
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
            "Kills: " + playerStats.kills,
            "Morts: " + playerStats.deaths,
            "Killstreak: " + playerStats.killstreak,
            ""
        )
    }
}