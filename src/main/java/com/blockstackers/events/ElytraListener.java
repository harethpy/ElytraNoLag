package com.blockstackers.events;

import com.blockstackers.CooldownManager;
import com.blockstackers.config.ConfigManager;
import com.blockstackers.elytranolag;
import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class ElytraListener implements Listener {

    elytranolag plugin;
    ConfigManager configManager;

    public ElytraListener(elytranolag plugin) {

        this.plugin = plugin;
        this.configManager = new ConfigManager(plugin);
    }

    CooldownManager cooldown = new CooldownManager();

    // Disable Elytra Fly damage during cooldown
    @EventHandler
    public void onDamageEvent(PlayerItemDamageEvent event)
    {
        Player player = event.getPlayer();
        if(cooldown.checkExist(player.getUniqueId()))
        {
            if ((cooldown.timeLeft(player.getUniqueId()) > 0) && player.isGliding())
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFireworkGlide(PlayerElytraBoostEvent e) {

        Player p = e.getPlayer();

        if (configManager.existPlayer(p.getName()))
            return;

        // Cancel Event
        if (cooldown.checkExist(p.getUniqueId()) && cooldown.timeLeft(p.getUniqueId()) > 0) {
            e.setCancelled(true);
        } else {

            // Create Cooldown
            cooldown.add(p.getUniqueId(), configManager.getCooldownPeriod());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (cooldown.timeLeft(p.getUniqueId()) > 0) {
                        cooldown.displayCooldown(p, configManager.getCooldownMsg());
                    } else {
                        // Clear Action bar
                        p.sendActionBar(new TextComponent(""));
                        this.cancel();
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 0L, 20L);

        }
    }
}
