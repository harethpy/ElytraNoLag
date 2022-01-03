package com.blockstackers;

import com.blockstackers.commands.AdminCommands;
import com.blockstackers.events.ElytraListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class elytranolag extends JavaPlugin {


    @Override
    public void onEnable() {

        // Plugin startup logic
        this.saveDefaultConfig();
        this.reloadConfig();

        // Listeners
        getServer().getPluginManager().registerEvents(new ElytraListener(this), this);

        // Commands
        this.getCommand("enl").setExecutor(new AdminCommands(this));

        Bukkit.getServer().getConsoleSender().sendMessage("Enabled ElytraNoLag");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getServer().getConsoleSender().sendMessage("Disabled ElytraNoLag");
    }
}
