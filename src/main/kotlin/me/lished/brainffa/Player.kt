package me.lished.brainffa

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

val playerStatsMap = mutableMapOf<Player, PlayerStats>()

data class PlayerStats(
    var kills: Int = 0,
    var deaths: Int = 0,
    var killstreak: Int = 0
)

fun respawn(player: Player) {
    player.playSound(player.location,Sound.ORB_PICKUP, 100f, 100f)
    player.teleport(Location(player.world, 0.0, 90.0, 0.0))
    player.inventory.clear()
    player.inventory.setItem(4, ItemStack(Material.NETHER_STAR))
}
fun Player.injectToFFA() {
    teleport(Location(world, 0.0, 90.0, 0.0))
    inventory.contents = arrayOf(ItemStack(Material.STONE, 29),ItemStack(Material.STONE, 29),ItemStack(Material.STONE, 29),ItemStack(Material.STONE, 29),ItemStack(Material.STONE, 29),ItemStack(Material.STONE, 29))
}