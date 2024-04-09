package fr.lyxiz.brainffa.listener

import fr.lyxiz.brainffa.*
import fr.mrmicky.fastboard.FastBoard
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack


class PlayerListener : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val player = e.player

        val board = FastBoard(player)
        board.updateTitle("§cBrainFFA")
        boards.put(player, board)

        player.respawn()
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        val player = e.player

        boards.remove(player)?.delete()
    }

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        val victim = e.entity as Player

        victim.inventory.clear()

        /*if (victim.lastDamageCause.cause == EntityDamageEvent.DamageCause.VOID) {
            e.deathMessage = "kill void"
        } else*/ if (victim.lastDamageCause.cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK && victim.killer is Player) {
            e.deathMessage = "kill ${victim.killer}"
        } else {
            e.deathMessage = ""
        }

        playerStatsMap.getOrPut(victim) { PlayerStats() }.deaths.inc()
        playerStatsMap.getOrPut(victim) { PlayerStats() }.killstreak = 0
        playerStatsMap.getOrPut(victim.killer) { PlayerStats() }.kills.inc()
        playerStatsMap.getOrPut(victim.killer) { PlayerStats() }.killstreak.inc()

        victim.killer.health = victim.killer.maxHealth
        victim.respawn()
    }

    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        if (e.item == ItemStack(Material.NETHER_STAR)) {
            e.player.injectToFFA()
        }
    }

    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        if (e.to.y < 70) {
            playerStatsMap.getOrPut(e.player) { PlayerStats() }.deaths.inc()
            Bukkit.broadcastMessage("kill void")
            e.player.respawn()
        }
    }
}
