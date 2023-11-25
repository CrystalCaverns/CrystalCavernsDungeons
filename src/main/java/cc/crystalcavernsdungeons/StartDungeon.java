package cc.crystalcavernsdungeons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.plugin;
import static cc.crystalcavernsdungeons.FinishDungeonMenu.finishDungeonMenu;

public class StartDungeon {
    public static void startDungeonRun(Player p) {
        p.sendTitle("\uDBEA\uDDE8", "", 0, 10, 10);
        p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        p.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
        p.getInventory().setItem(1, getPickaxe());
        p.getInventory().setItem(2, new ItemStack(Material.STONE_AXE));
        p.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
        BossBar bossBar = Bukkit.getServer().createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        bossBar.setVisible(true);
        bossBar.addPlayer(p);
        AtomicInteger timer = new AtomicInteger(10);
        String clock = "\uDBCA\uDEA2\uDBE3\uDC05";
        Bukkit.getScheduler().runTaskTimer(plugin, (task) -> {
            int seconds = Integer.parseInt(timer.toString());
            int M = (seconds / 60);
            int S = seconds % 60;
            if (M <= 9 && S <= 9) {
                bossBar.setTitle(clock + "0" + M + ":" + "0" + S);
            } else if (M <= 9) {
                bossBar.setTitle(clock + "0" + M + ":" + S);
            } else if (S <= 9) {
                bossBar.setTitle(clock + M + ":" + "0" + S);
            } else {
                bossBar.setTitle(clock + M + ":" + S);
            }
            if (timer.get() <= 0) {
                finishDungeonMenu(p);
                task.cancel();
            }
            timer.set(timer.get() - 1);
        }, 20L,20L);
    }
    @NotNull
    public static ItemStack getPickaxe() {
        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta meta = pickaxe.getItemMeta();
        meta.setCanDestroy(Collections.singleton(Material.SPAWNER));
        pickaxe.setItemMeta(meta);
        return pickaxe;
    }
}
