package cc.crystalcavernsdungeons;

import me.rockyhawk.commandpanels.api.Panel;
import me.rockyhawk.commandpanels.openpanelsmanager.PanelPosition;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        p.sendTitle("\uDBEA\uDDE8", "", 0, 10, 10);
        p.getInventory().clear();
        p.teleport(new Location(p.getWorld(),0,0,0,0,0));
        File file = new File("/home/container/plugins/CommandPanels/panels/start_dungeon.yml");
        Panel panel = new Panel(file, "start_dungeon");
        panel.open(p, PanelPosition.Top);
    }
}