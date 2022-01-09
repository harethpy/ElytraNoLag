package com.blockstackers.config;

import com.blockstackers.elytranolag;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public  class ConfigManager {

    public elytranolag plugin;
    FileConfiguration config;

    private List<String> playersList = new ArrayList<>();

    public ConfigManager(elytranolag plugin)
    {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public  void addPlayer(String player)
    {
        playersList = config.getStringList("whitelist.name");
        if(playersList.contains(player))
            return;
        playersList.add(player);
        config.set("whitelist.name", playersList);
        saveCfg();
    }

    public void removePlayer(String player)
    {
        playersList = config.getStringList("whitelist.name");

        if(!playersList.contains(player))
            return;

        playersList.remove(player);
        config.set("whitelist.name", playersList);
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
