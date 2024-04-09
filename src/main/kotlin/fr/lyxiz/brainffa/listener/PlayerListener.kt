package fr.lyxiz.brainffa.listener

import fr.lyxiz.brainffa.PlayerStats
import fr.lyxiz.brainffa.injectToFFA
import fr.lyxiz.brainffa.playerStatsMap
import fr.lyxiz.brainffa.respawn
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack

class PlayerListener : Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        e.player.respawn()
    }

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        val victim = e.entity as Player

        victim.inventory.clear()

        if (victim.lastDamageCause.cause == EntityDamageEvent.DamageCause.VOID) {
            e.deathMessage = "kill void"
        } else if (victim.lastDamageCause.cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK && victim.killer is Player) {
            e.deathMessage = "kill ${victim.killer}"
        } else {
            e.deathMessage = ""
        }

        playerStatsMap.getOrPut(victim) { PlayerStats() }.deaths.inc()
        playerStatsMap.getOrPut(victim) { PlayerStats() }.killstreak = 0
        playerStatsMap.getOrPut(victim.killer) { PlayerStats() }.kills.inc()
        playerStatsMap.getOrPut(victim.killer) { PlayerStats() }.killstreak.inc()

        victim.respawn()
    }

    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        if (e.item == ItemStack(Material.NETHER_STAR)) {
            e.player.injectToFFA()
        }
    }
}
