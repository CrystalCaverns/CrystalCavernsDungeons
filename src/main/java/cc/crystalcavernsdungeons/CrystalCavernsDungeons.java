package cc.crystalcavernsdungeons;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;

public final class CrystalCavernsDungeons extends JavaPlugin {
    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(getCommand("crystalcaverns")).setExecutor(new MainCommand());
        getLogger().info("Crystal Caverns Dungeons plugin loaded successfully!");
    }
    public static JavaPlugin plugin;
    @NotNull
    public static ItemStack getPickaxe() {
        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta meta = pickaxe.getItemMeta();
        meta.setCanDestroy(Collections.singleton(Material.SPAWNER));
        pickaxe.setItemMeta(meta);
        return pickaxe;
    }
}