package me.ivanech.vansjoin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class VansJoin extends JavaPlugin implements CommandExecutor {

    private static VansJoin instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        if (getCommand("vj") != null) {
            getCommand("vj").setExecutor(this);
        }
    }

    @Override
    public void onDisable() {
        // no-op
    }

    public static VansJoin getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            // Консоль тоже может использовать команды, но проверка пермишена одинакова
        }

        if (args.length == 0) {
            String msg = ChatColor.translateAlternateColorCodes('&', "&e[VansJoin] &6VansJoin by ivanech version &8" + getDescription().getVersion());
            sender.sendMessage(msg);
            return true;
        }

        String sub = args[0].toLowerCase();

        if (sub.equals("help")) {
            if (!sender.hasPermission("vansjoin.command")) {
                return true;
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6VansJoin by ivanech &8(1.0.0-beta)"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8• &e/vj &7— show plugin info"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8• &e/vj reload &7— reload config"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8• &e/vj help &7— show this help"));
            return true;
        }

        if (sub.equals("reload")) {
            if (!sender.hasPermission("vansjoin.reload")) {
                return true;
            }
            reloadConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[VansJoin] &7Config reloaded!"));
            return true;
        }

        String msg = ChatColor.translateAlternateColorCodes('&', "&e[VansJoin] &6VansJoin by ivanech version &8" + getDescription().getVersion());
        sender.sendMessage(msg);
        return true;
    }
}
