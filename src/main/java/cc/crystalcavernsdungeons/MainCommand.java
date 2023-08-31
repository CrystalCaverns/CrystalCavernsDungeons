package cc.crystalcavernsdungeons;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 1) {
            if ("boss_shockwave".equals(args[0])) {
                Shockwave.start(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getLocation());
            }
        }
        return false;
    }
}
