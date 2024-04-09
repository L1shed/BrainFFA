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
                updateBoard(board)
            }
        }, 0, 20)

        val hologram = HolographicDisplaysAPI.get(this).createHologram(Location(Bukkit.getWorld("world"), 0.0, 85.0, 0.0))
        val topPlayers = playerStatsMap.entries.sortedByDescending { it.value.killstreak }.take(10)

        topPlayers.forEachIndexed { index, entry ->
            val player = entry.key
            val stats = entry.value
            hologram.lines.appendText("- Top 10 Killstreak -")
            hologram.lines.appendText("${index + 1}. ${player.name} - ${stats.killstreak}")
        }
        hologram.delete()
    }

    private fun updateBoard(board: FastBoard) {
        val playerStats = playerStatsMap.getOrPut(board.player) { PlayerStats() }
        board.updateLines(
            "",
            "Kills: " + playerStats.kills,
            "Morts: " + playerStats.deaths,
            "Killstreak: " + playerStats.killstreak,
            ""
        )
    }
}