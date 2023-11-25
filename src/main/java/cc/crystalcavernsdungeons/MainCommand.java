package cc.crystalcavernsdungeons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.connection;
import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.getConnection;
import static cc.crystalcavernsdungeons.StartDungeon.startDungeonRun;

public class MainCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player p = Objects.requireNonNull(Bukkit.getPlayer(args[0]));
        if ("startdungeon".equals(args[1])) {
            startDungeonRun(p);
            return false;
        }
        if ("transportloot".equals(args[1])) {
            //Check if connection is open
            try {
                if (getConnection() == null || getConnection().isClosed()) {
                    p.sendMessage(ChatColor.WHITE + "\uDBF7\uDC35 " + ChatColor.RED + "Uh oh, that's an error on our side. Please report this as a bug on our Discord server as soon as possible.");
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Inventory inv = Bukkit.createInventory(p, 9, (ChatColor.WHITE + "\uDBC7\uDCB8\uDBE6\uDE62"));
            //Load items from database
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT itemstack FROM virtualchest WHERE uuid = ?");
                ps.setString(1, p.getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    byte[] itemBytes = rs.getBytes("itemstack");
                    ItemStack item = fromByteArray(itemBytes);
                    inv.addItem(item);
                }
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            p.openInventory(inv);
            return false;
        }
//      if ("shockwave".equals(args[0])) {
//          Shockwave.start(p.getLocation());
//          return false;
//      }
//      if ("knockup".equals(args[0])) {
//          VineKnockup.start(p.getLocation());
//          return false;
//      }
        return false;
    }
    public static ItemStack fromByteArray(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             BukkitObjectInputStream ois = new BukkitObjectInputStream(bis)) {
            return (ItemStack) ois.readObject();
        }
    }
}
