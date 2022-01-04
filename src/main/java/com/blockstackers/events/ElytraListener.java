package com.blockstackers.events;

import com.blockstackers.CooldownManager;
import com.blockstackers.config.ConfigManager;
import com.blockstackers.elytranolag;
import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class ElytraListener implements Listener {

    elytranolag plugin;
    ConfigManager configManager;

    public ElytraListener(elytranolag plugin) {

        this.plugin = plugin;
        this.configManager = new ConfigManager(plugin);
    }

    CooldownManager cooldown = new CooldownManager();

    @EventHandler
    public void onFireworkGlide(PlayerElytraBoostEvent e) {

        Player p = e.getPlayer();

        if (configManager.existPlayer(p.getName()))
            return;


        // Cancel Event & Set Cooldown
        if (cooldown.checkExist(p.getUniqueId()) && cooldown.timeLeft(p.getUniqueId()) > 0) {
            e.setCancelled(true);
        } else {
            cooldown.add(p.getUniqueId());
        }

        // Updates Cooldown message


        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {

                if (cooldown.timeLeft(p.getUniqueId()) > 0) {
                    cooldown.displayCooldown(p);
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}
