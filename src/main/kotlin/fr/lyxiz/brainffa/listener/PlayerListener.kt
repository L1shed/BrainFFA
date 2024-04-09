package fr.lyxiz.brainffa.listener

import fr.lyxiz.brainffa.*
import fr.mrmicky.fastboard.FastBoard
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
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

        if (victim.lastDamageCause.cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK && victim.killer != null) {
            e.deathMessage = "kill ${victim.killer}"
        } else {
            e.deathMessage = ""
        }

        e.keepInventory = true
        victim.respawn()
        playerStatsMap.getOrPut(victim) { PlayerStats() }.deaths++
        playerStatsMap.getOrPut(victim) { PlayerStats() }.killstreak = 0
        victim.sendMessage(playerStatsMap.toString())

        if (victim.killer != null) {
            victim.killer.health = victim.killer.maxHealth
            playerStatsMap.getOrPut(victim.killer) { PlayerStats() }.kills++
        }
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
            Bukkit.broadcastMessage("kill void")
            e.player.respawn()
            playerStatsMap.getOrPut(e.player) { PlayerStats() }.deaths++
            playerStatsMap.getOrPut(e.player) { PlayerStats() }.killstreak = 0
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.cause == EntityDamageEvent.DamageCause.FALL) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlace(e: BlockPlaceEvent) {
        if (e.blockPlaced.y > 100) {
            e.isCancelled = true
            return
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(BrainFFA.instance, {
            e.blockPlaced.type = Material.AIR
        }, 10*20)
    }

    @EventHandler
    fun onHunger(e: FoodLevelChangeEvent) {
        e.isCancelled = true
    }
}
