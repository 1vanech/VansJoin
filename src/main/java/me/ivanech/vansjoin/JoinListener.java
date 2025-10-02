package me.ivanech.vansjoin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public final class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("vansjoin.alert")) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(VansJoin.getInstance(), () -> {
            String template = VansJoin.getInstance().getConfig().getString(
                    "join-message",
                    "&6&lVansJoin &8>> &6{player} &eзашёл на сервер!"
            );

            String prefix = "";
            Plugin papi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
            if (papi != null && papi.isEnabled()) {
                try {
                    String resolved = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%");
                    if (resolved != null) {
                        prefix = resolved;
                    }
                } catch (Throwable ignored) {
                    prefix = "";
                }
            }

            String message = template
                    .replace("{player}", player.getName())
                    .replace("{prefix}", prefix);

            message = ChatColor.translateAlternateColorCodes('&', message);
            Bukkit.getServer().broadcastMessage(message);

            for (Player online : Bukkit.getOnlinePlayers()) {
                online.playSound(online.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1.0f, 1.0f);
            }
        }, 2L);
    }
}
