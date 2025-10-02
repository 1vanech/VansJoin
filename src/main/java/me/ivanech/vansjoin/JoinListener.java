package me.ivanech.vansjoin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public final class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(VansJoin.getInstance(), () -> {
            try {
                if (player == null || !player.isOnline()) {
                    return;
                }

                if (!player.hasPermission("vansjoin.alert")) {
                    return;
                }

                String template = VansJoin.getInstance().getConfig().getString("join-message");
                if (template == null || template.trim().isEmpty()) {
                    template = "&6&lVansJoin &8>> &6{player} &eзашёл на сервер!";
                }

                String prefix = "";
                Plugin papi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
                if (papi != null && papi.isEnabled()) {
                    try {
                        String resolved = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%");
                        if (resolved != null && !resolved.contains("%")) {
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
            Bukkit.getConsoleSender().sendMessage(message);
            player.sendMessage(message);

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online != null && online.isOnline()) {
                        online.playSound(online.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1.0f, 1.0f);
                    }
                }
            } catch (Throwable ignored) {
                // graceful fallback: ничего не делаем, чтобы не уронить тик
            }
        }, 2L);
    }
}
