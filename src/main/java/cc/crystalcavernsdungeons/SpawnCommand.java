package cc.crystalcavernsdungeons;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.plugin;

public class SpawnCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly a player can execute this command!");
            return false;
        }
        player.sendTitle("\uDBEA\uDDE8","",10,40,10);
        Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"warp Spawn " + player.getName()), 20L);
        return false;
    }
}