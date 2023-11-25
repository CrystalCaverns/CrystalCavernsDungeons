package cc.crystalcavernsdungeons;

import me.rockyhawk.commandpanels.api.Panel;
import me.rockyhawk.commandpanels.openpanelsmanager.PanelPosition;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;

public class FinishDungeonMenu {
    public static void finishDungeonMenu(Player p) {
        p.sendTitle("\uDBEA\uDDE8","",10,40,10);
        p.teleport(new Location(p.getWorld(),0,0,0,0,0));
        File file = new File("/home/container/plugins/CommandPanels/panels/finish_dungeon.yml");
        Panel panel = new Panel(file, "finish_dungeon");
        panel.open(p, PanelPosition.Top);
    }
}
