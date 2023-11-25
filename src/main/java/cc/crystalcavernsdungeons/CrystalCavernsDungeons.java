package cc.crystalcavernsdungeons;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public final class CrystalCavernsDungeons extends JavaPlugin {
    public static Connection connection;
    private String host;
    private String database;
    private String username;
    private String password;
    private int port;
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(),this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new CloseInventory(), this);
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(getCommand("crystalcaverns")).setExecutor(new MainCommand());
        host = this.getConfig().getString("host");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");
        port = this.getConfig().getInt("port");
        setupDatabase();
        getLogger().info("Crystal Caverns Dungeons plugin loaded successfully!");
        getLogger().info(database + " " + username + " " + password + " " + port);
    }
    @Override
    public void onDisable() {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void setupDatabase() {
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
                Bukkit.getConsoleSender().sendMessage("Database Connected!");
                Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
                    try {
                        if (getConnection() != null) {
                            if (getConnection().isClosed()) {
                                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
                                Bukkit.getConsoleSender().sendMessage("Database reconnected!");
                            } else {
                                getConnection().createStatement().executeQuery("/* ping */ SELECT 1");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }, 6000L, 6000L);
                
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS virtualchest (uuid TEXT, itemstack BLOB)");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return connection;
    }
    public static void setConnection(Connection connection) {
        CrystalCavernsDungeons.connection = connection;
    }
    public static JavaPlugin plugin;
    
//    public void createParty(Collection<? extends String> party) {
//        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
//            for (String party_member : party) {
//                Player player = Bukkit.getPlayer(party_member);
//                if (Objects.requireNonNull(player).isOnline()) {
//                    BufferedImage image;
//                    try {
//                        image = getPlayerAvatar(player);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    for (int y = 0; y <= 140; y = y + 20) {
//                        String character = getString(y);
//                        for (int x = 0; x <= 140; x = x + 20) {
//                            Color color = new Color(image.getRGB(x, y), true);
//                            String hex = "&#" + Integer.toHexString(color.getRGB()).substring(2) + "&";
//                            message.add(hex + character + "\uDBED\uDC5C");
//                        }
//                        message.add("\uDBE5\uDF70");
//                    }
//                    message.add("   ");
//                }
//            }
//            int size = party.size();
//            if (size < 4) {
//                String blank = getBlank(size);
//                message.add(blank);
//            }
//            String message1 = message.toString().replace("[", "");
//            String message2 = message1.replace("]", "");
//            String message3 = message2.replace(", ", "");
//            TextComponent formatted_message = new TextComponent(MineDown.parse(message3));
//            Bukkit.getScheduler().runTaskTimer(plugin, () -> {
//                for (Player player : Bukkit.getOnlinePlayers()) {
//                    if (toSend.contains(player.getUniqueId())) {
//                        player.sendMessage(ChatMessageType.ACTION_BAR, formatted_message);
//                    }
//                }
//            }, 0L, 20L);
//        });
//    }
    
//    @NotNull
//    private static String getBlank(int size) {
//        String blank = "#333333\uDBFC\uDD95\uDBED\uDC5C\uDBFC\uDD95\uDBED\uDC5C\uDBFC\uDD95\uDBED\uDC5C\uDBFC\uDD95\uDBED\uDC5C\uDBFC\uDD95\uDBED\uDC5C\uDBFC\uDD95\uDBED\uDC5C\uDBFC\uDD95\uDBED\uDC5C\uDBFC\uDD95\uDBED\uDC5C\uDBE5\uDF70\uDBEB\uDCE5\uDBED\uDC5C\uDBEB\uDCE5\uDBED\uDC5C\uDBEB\uDCE5\uDBED\uDC5C\uDBEB\uDCE5\uDBED\uDC5C\uDBEB\uDCE5\uDBED\uDC5C\uDBEB\uDCE5\uDBED\uDC5C\uDBEB\uDCE5\uDBED\uDC5C\uDBEB\uDCE5\uDBED\uDC5C\uDBE5\uDF70\uDBDF\uDCA4\uDBED\uDC5C\uDBDF\uDCA4\uDBED\uDC5C\uDBDF\uDCA4\uDBED\uDC5C\uDBDF\uDCA4\uDBED\uDC5C\uDBDF\uDCA4\uDBED\uDC5C\uDBDF\uDCA4\uDBED\uDC5C\uDBDF\uDCA4\uDBED\uDC5C\uDBDF\uDCA4\uDBED\uDC5C\uDBE5\uDF70\uDBD9\uDD34\uDBED\uDC5C\uDBD9\uDD34\uDBED\uDC5C\uDBD9\uDD34\uDBED\uDC5C\uDBD9\uDD34\uDBED\uDC5C\uDBD9\uDD34\uDBED\uDC5C\uDBD9\uDD34\uDBED\uDC5C\uDBD9\uDD34\uDBED\uDC5C\uDBD9\uDD34\uDBED\uDC5C\uDBE5\uDF70\uDBD8\uDFBC\uDBED\uDC5C\uDBD8\uDFBC\uDBED\uDC5C\uDBD8\uDFBC\uDBED\uDC5C\uDBD8\uDFBC\uDBED\uDC5C\uDBD8\uDFBC\uDBED\uDC5C\uDBD8\uDFBC\uDBED\uDC5C\uDBD8\uDFBC\uDBED\uDC5C\uDBD8\uDFBC\uDBED\uDC5C\uDBE5\uDF70\uDBC0\uDCF0\uDBED\uDC5C\uDBC0\uDCF0\uDBED\uDC5C\uDBC0\uDCF0\uDBED\uDC5C\uDBC0\uDCF0\uDBED\uDC5C\uDBC0\uDCF0\uDBED\uDC5C\uDBC0\uDCF0\uDBED\uDC5C\uDBC0\uDCF0\uDBED\uDC5C\uDBC0\uDCF0\uDBED\uDC5C\uDBE5\uDF70\uDBE2\uDF7F\uDBED\uDC5C\uDBE2\uDF7F\uDBED\uDC5C\uDBE2\uDF7F\uDBED\uDC5C\uDBE2\uDF7F\uDBED\uDC5C\uDBE2\uDF7F\uDBED\uDC5C\uDBE2\uDF7F\uDBED\uDC5C\uDBE2\uDF7F\uDBED\uDC5C\uDBE2\uDF7F\uDBED\uDC5C\uDBE5\uDF70\uDBF6\uDC0D\uDBED\uDC5C\uDBF6\uDC0D\uDBED\uDC5C\uDBF6\uDC0D\uDBED\uDC5C\uDBF6\uDC0D\uDBED\uDC5C\uDBF6\uDC0D\uDBED\uDC5C\uDBF6\uDC0D\uDBED\uDC5C\uDBF6\uDC0D\uDBED\uDC5C\uDBF6\uDC0D\uDBED\uDC5C   ";
//        return blank.repeat(4 - size);
//    }
    
//    @NotNull
//    private static BufferedImage getPlayerAvatar(Player player) throws IOException {
//        URL url = new URL("https://crafatar.com/avatars/" + player.getUniqueId());
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//        return ImageIO.read(connection.getInputStream());
//    }
//    @Nullable
//    private static String getString(int y) {
//        String character = null;
//        if (y == 0) {
//            character = "\uDBFC\uDD95";
//        }
//        if (y == 20) {
//            character = "\uDBEB\uDCE5";
//        }
//        if (y == 40) {
//            character = "\uDBDF\uDCA4";
//        }
//        if (y == 60) {
//            character = "\uDBD9\uDD34";
//        }
//        if (y == 80) {
//            character = "\uDBD8\uDFBC";
//        }
//        if (y == 100) {
//            character = "\uDBC0\uDCF0";
//        }
//        if (y == 120) {
//            character = "\uDBE2\uDF7F";
//        }
//        if (y == 140) {
//            character = "\uDBF6\uDC0D";
//        }
//        return character;
//    }
}