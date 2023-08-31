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
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.plugin;

public class Shockwave {
    public static void start(@NotNull Location location) {
        ItemStack itemstack = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta itemMeta = itemstack.getItemMeta();
        itemMeta.setCustomModelData(5);
        itemstack.setItemMeta(itemMeta);
        BukkitScheduler scheduler = Bukkit.getScheduler();
        for (int i = 0; i <= 7; i++) {
            ItemDisplay itemdisplay = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
            itemdisplay.setItemStack(itemstack);
            itemdisplay.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.HEAD);
            itemdisplay.setRotation(45 * i,0);
            scheduler.runTaskLater(plugin, () -> {
                itemdisplay.setInterpolationDuration(100);
                itemdisplay.setInterpolationDelay(-1);
                Transformation transformation = itemdisplay.getTransformation();
                transformation.getTranslation().set(new Vector3f(8,0,0));
                transformation.getScale().set(1,1,8);
                itemdisplay.setTransformation(transformation);
            }, 10L);
            scheduler.runTaskLater(plugin, itemdisplay::remove, 100L);
        }
    }
}
