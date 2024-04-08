package me.lished.brainffa.listener

import me.lished.brainffa.injectToFFA
import me.lished.brainffa.randomTp
import me.lished.brainffa.respawn
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
        respawn(e.player)
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
    }

    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        if (e.item == ItemStack(Material.NETHER_STAR)) {
            e.player.injectToFFA()
        }
    }
}
