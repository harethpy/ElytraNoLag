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

    // Returns Cooldown Period from config
    public int getCooldownPeriod()
    {
        return config.getInt("cooldown.period");
    }


    public void setCooldownPeriod(int period)
    {
        config.set("cooldown.period", period);
        saveCfg();
    }

    public String getCooldownMsg()
    {
        String msg = config.getString("cooldown.message");
        assert msg != null;
        msg = msg.replace("&", "§");
        return msg;
    }

    public void setCooldownMsg(String message)
    {
        message = message.replace("&", "§");
        config.set("cooldown.message", message);
        saveCfg();
    }




    private void saveCfg()
    {
        plugin.saveConfig();
    }

    public void reloadCfg()
    {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

}
