package com.blockstackers.config;

import com.blockstackers.elytranolag;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public  class ConfigManager {
    //TODO: Add Config file instead of list

    public elytranolag plugin;
    FileConfiguration config;

    List<String> playerNAMElist = new ArrayList<>();

    public ConfigManager(elytranolag plugin)
    {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }


    public  void addPlayer(String player)
    {
        playerNAMElist = config.getStringList("whitelist.name");
        if(playerNAMElist.contains(player))
            return;
        playerNAMElist.add(player);
        config.set("whitelist.name", playerNAMElist);
        saveCfg();
    }

    public void removePlayer(String player)
    {
        playerNAMElist = config.getStringList("whitelist.name");

        if(!playerNAMElist.contains(player))
            return;

        playerNAMElist.remove(player);
        config.set("whitelist.name", playerNAMElist);

        saveCfg();

    }

    public  boolean existPlayer(String player)
    {
        return config.getStringList("whitelist.name").contains(player);
    }

    public List<String> getNameList()
    {
        return config.getStringList("whitelist.name");
    }

    private void saveCfg()
    {
        plugin.saveConfig();
    }

    public void reloadCfg()
    {
        plugin.reloadConfig();
    }

}
