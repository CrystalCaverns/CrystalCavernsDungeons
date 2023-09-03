package cc.crystalcavernsdungeons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.plugin;

public class VineKnockup {
    public static void start(@NotNull Location location) {
        //Warning Item
        ItemStack warningItem = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta warningMeta = warningItem.getItemMeta();
        warningMeta.setCustomModelData(6);
        warningItem.setItemMeta(warningMeta);
        //Vine Item
        ItemStack vineItem = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta vineMeta = vineItem.getItemMeta();
        vineMeta.setCustomModelData(7);
        vineItem.setItemMeta(vineMeta);
        //Spawn Warning
        ItemDisplay warning = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
        warning.setItemStack(warningItem);
        warning.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.HEAD);
        warning.setRotation(0,0);
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskLater(plugin, () -> {
            //Spawn Vine
            ItemDisplay vine = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
            vine.setItemStack(vineItem);
            vine.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.HEAD);
            vine.setRotation(0,0);
            vine.setCustomName("Narrian Vine");
            scheduler.runTaskLater(plugin, () -> {
                vine.setInterpolationDuration(10);
                vine.setInterpolationDelay(-1);
                Transformation transformation = vine.getTransformation();
                transformation.getTranslation().set(new Vector3f(0,3,0));
                vine.setTransformation(transformation);
                }, 10L);
            scheduler.runTaskLater(plugin, () -> {
                //Damage Players
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (location.distanceSquared(players.getLocation()) <= 1) {
                       players.damage(2,vine);
                       players.setVelocity(new Vector(0,1,0));
                    }
                }
            }, 15L);
            scheduler.runTaskLater(plugin, () -> {
                vine.setInterpolationDuration(10);
                vine.setInterpolationDelay(-1);
                Transformation transformation = vine.getTransformation();
                transformation.getTranslation().set(new Vector3f(0,-3,0));
                vine.setTransformation(transformation);
            }, 30L);
            scheduler.runTaskLater(plugin, vine::remove, 45L);
        }, 20L);
        scheduler.runTaskLater(plugin, warning::remove, 35L);
    }
}
