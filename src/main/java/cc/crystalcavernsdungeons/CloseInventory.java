package cc.crystalcavernsdungeons;

import me.rockyhawk.commandpanels.api.Panel;
import me.rockyhawk.commandpanels.openpanelsmanager.PanelPosition;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static cc.crystalcavernsdungeons.CrystalCavernsDungeons.connection;

public class CloseInventory implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(ChatColor.WHITE + "\uDBC7\uDCB8\uDBE6\uDE62")) {
            Player p = (Player) event.getPlayer();
            String uuid = p.getUniqueId().toString();
            try {
                // Delete existing items
                PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM virtualchest WHERE uuid = ?");
                deleteStatement.setString(1, uuid);
                deleteStatement.executeUpdate();
                // Insert new items
                Inventory inv = event.getInventory();
                for (int i = 0; i < inv.getSize(); i++) {
                    ItemStack item = inv.getItem(i);
                    if (item != null && !item.getType().equals(Material.AIR)) {
                        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO virtualchest(uuid, itemstack) VALUES(?, ?)");
                        insertStatement.setString(1, uuid);
                        insertStatement.setBytes(2, toByteArray(item));
                        insertStatement.executeUpdate();
                        inv.setItem(i, null);
                    }
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
            File file = new File("/home/container/plugins/CommandPanels/panels/finish_dungeon.yml");
            Panel panel = new Panel(file, "finish_dungeon");
            panel.open(p, PanelPosition.Top);
        }
    }
    public static byte[] toByteArray(ItemStack item) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             BukkitObjectOutputStream oos = new BukkitObjectOutputStream(bos)) {
            oos.writeObject(item);
            return bos.toByteArray();
        }
    }
}
