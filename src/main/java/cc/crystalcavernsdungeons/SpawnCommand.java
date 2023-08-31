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
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly a player can execute this command!");
        }
        boolean is_op1 = sender.isOp();
        try {
            sender.setOp(true);
            plugin.getServer().dispatchCommand(sender,"gizmo fade 10 100 10");
            sender.setOp(is_op1);
        } catch (Exception exception) {
            sender.setOp(is_op1);
            sender.sendMessage("SUPER RARE ERROR! PLEASE REPORT IMMEDIATELY! ID: SpawnCommand Rare Error");
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            boolean is_op2 = sender.isOp();
            try {
                sender.setOp(true);
                plugin.getServer().dispatchCommand(sender,"warp Spawn");
                sender.setOp(is_op2);
            } catch (Exception exception) {
                sender.setOp(is_op2);
                sender.sendMessage("SUPER RARE ERROR! PLEASE REPORT IMMEDIATELY! ID: SpawnCommand Rare Error");
            }}, 20L);
        return false;
    }
}