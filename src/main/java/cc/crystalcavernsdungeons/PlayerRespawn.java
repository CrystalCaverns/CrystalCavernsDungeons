package cc.crystalcavernsdungeons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.getPickaxe;
import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.plugin;

public class PlayerRespawn implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            boolean is_op = p.isOp();
            try {
                p.setOp(true);
                plugin.getServer().dispatchCommand(p,"dungeongenerator respawn");
                p.setOp(is_op);
            } catch (Exception exception) {
                p.setOp(is_op);
                p.sendMessage("SUPER RARE ERROR! PLEASE REPORT IMMEDIATELY! ID: RespawnEvent Rare Error");
            }}, 10L);
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