package cc.crystalcavernsdungeons;

import de.themoep.minedown.MineDown;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class CrystalCavernsDungeons extends JavaPlugin {
    public static final List<UUID> toSend = new ArrayList<>();
    public static final List<String> message = new ArrayList<>();
    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(getCommand("party")).setExecutor(new PartyCommand());
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
    public void createParty(Collection<? extends Player> party) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (Player party_member : party) {
                URL url;
                try {
                    url = new URL("https://crafatar.com/avatars/" + party_member.getUniqueId());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                HttpURLConnection connection;
                try {
                    connection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                BufferedImage image;
                try {
                    image = ImageIO.read(connection.getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (int y = 0; y <= 140; y = y + 20) {
                    String character = getString(y);
                    for (int x = 0; x <= 140; x = x + 20) {
                        Color color = new Color(image.getRGB(x, y), true);
                        String hex = "&#" + Integer.toHexString(color.getRGB()).substring(2) + "&";
                        message.add(hex + character + "\uDBED\uDC5C");
                    }
                    message.add("\uDBE5\uDF70");
                }
                message.add("   ");
            }
            String message1 = message.toString().replace("[", "");
            String message2 = message1.replace("]", "");
            String message3 = message2.replace(", ", "");
            TextComponent formatted_message = new TextComponent(MineDown.parse(message3));
            Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (toSend.contains(player.getUniqueId())) {
                        player.sendMessage(ChatMessageType.ACTION_BAR, formatted_message);
                    }
                }
            }, 20L, 20L);
        });
    }
    public void startDungeonRun(Player p) {
        p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        p.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
        p.getInventory().setItem(1, getPickaxe());
        p.getInventory().setItem(2, new ItemStack(Material.STONE_AXE));
        p.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
        BossBar bossBar = Bukkit.getServer().createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        bossBar.setVisible(true);
        bossBar.addPlayer(p);
        AtomicInteger timer = new AtomicInteger(600);
        String clock = "\uDBCA\uDEA2\uDBE3\uDC05";
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            int seconds = Integer.parseInt(timer.toString());
            int M = (seconds / 60);
            int S = seconds % 60;
            if (M <= 9 && S <= 9) {
                bossBar.setTitle(clock + "0" + M + ":" + "0" + S);
            } else if (M <= 9) {
                bossBar.setTitle(clock + "0" + M + ":" + S);
            } else if (S <= 9) {
                bossBar.setTitle(clock + M + ":" + "0" + S);
            } else {
                bossBar.setTitle(clock + M + ":" + S);
            }
            timer.set(timer.get() - 1);
        }, 20L,20L);
    }
    @Nullable
    private static String getString(int y) {
        String character = null;
        if (y == 0) {
            character = "\uDBFC\uDD95";
        }
        if (y == 20) {
            character = "\uDBEB\uDCE5";
        }
        if (y == 40) {
            character = "\uDBDF\uDCA4";
        }
        if (y == 60) {
            character = "\uDBD9\uDD34";
        }
        if (y == 80) {
            character = "\uDBD8\uDFBC";
        }
        if (y == 100) {
            character = "\uDBC0\uDCF0";
        }
        if (y == 120) {
            character = "\uDBE2\uDF7F";
        }
        if (y == 140) {
            character = "\uDBF6\uDC0D";
        }
        return character;
    }
}