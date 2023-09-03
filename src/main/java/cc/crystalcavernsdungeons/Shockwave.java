package cc.crystalcavernsdungeons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.plugin;

public class Shockwave {
    public static void start(@NotNull Location location) {
        ItemStack itemstack = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta itemMeta = itemstack.getItemMeta();
        itemMeta.setCustomModelData(5);
        itemstack.setItemMeta(itemMeta);
        BukkitScheduler scheduler = Bukkit.getScheduler();
        for (int i = 0; i <= 3; i++) {
            ItemDisplay itemdisplay = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
            itemdisplay.setItemStack(itemstack);
            itemdisplay.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.HEAD);
            itemdisplay.setRotation(90 * i,0);
            scheduler.runTaskLater(plugin, () -> {
                itemdisplay.setInterpolationDuration(120);
                itemdisplay.setInterpolationDelay(-1);
                Transformation transformation = itemdisplay.getTransformation();
                transformation.getScale().set(1,1,40);
                itemdisplay.setTransformation(transformation);
            }, 2L);
            int id = scheduler.runTaskTimer(plugin, () -> {
                Location item_location = itemdisplay.getLocation();
                Vector item_vector = itemdisplay.getFacing().getDirection().add(new Vector(0,0,0.05));
                itemdisplay.teleport(item_location.add(item_vector));
            }, 2L,1L).getTaskId();
            scheduler.runTaskLater(plugin, () -> {
                itemdisplay.remove();
                scheduler.cancelTask(id);
            }, 120L);
        }
    }
}
