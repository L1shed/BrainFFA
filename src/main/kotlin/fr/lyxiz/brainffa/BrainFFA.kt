package fr.lyxiz.brainffa

import fr.lyxiz.brainffa.listener.PlayerListener
import fr.mrmicky.fastboard.FastBoard
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