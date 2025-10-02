package me.ivanech.vansjoin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        String version = getDescription().getVersion();
        String msg = ChatColor.translateAlternateColorCodes('&', "&a[VansJoin]&r VansJoin by ivanech version " + version);
        sender.sendMessage(msg);
        return true;
    }
}
