package fr.lyxiz.brainffa

import fr.mrmicky.fastboard.FastBoard
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

val playerStatsMap = mutableMapOf<Player, PlayerStats>()
var boards = mutableMapOf<Player, FastBoard>()

data class PlayerStats(
    var kills: Int = 0,
    var deaths: Int = 0,
    var killstreak: Int = 0
)

fun Player.respawn() {
    playSound(player.location,Sound.ORB_PICKUP, 100f, 100f)
    teleport(Location(Bukkit.getWorld("world"), 0.0, 90.0, 0.0))
    inventory.clear()
    inventory.setItem(4, ItemStack(Material.NETHER_STAR))
}
fun Player.injectToFFA() {
    teleport(Location(Bukkit.getWorld("world"), 0.0, 90.0, 0.0))

    inventory.clear()
    inventory.setItem(0, ItemStack(Material.IRON_SWORD).apply { addEnchantment(Enchantment.DAMAGE_ALL, 1) })
    inventory.setItem(1, ItemStack(Material.GOLDEN_APPLE, 16))
    inventory.addItem(ItemStack(Material.SANDSTONE, 64*3))
    inventory.helmet = ItemStack(Material.IRON_HELMET).apply { addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1) }
    inventory.chestplate = ItemStack(Material.IRON_CHESTPLATE).apply { addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1) }
    inventory.leggings = ItemStack(Material.IRON_LEGGINGS).apply { addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1) }
    inventory.boots = ItemStack(Material.IRON_BOOTS).apply { addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1) }
}