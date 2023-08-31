package cc.crystalcavernsdungeons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.getPickaxe;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendTitle("\uDBEA\uDDE8", "", 0, 10, 10);
        p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        p.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
        p.getInventory().setItem(1, getPickaxe());
        p.getInventory().setItem(2, new ItemStack(Material.STONE_AXE));
        p.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
    }
}