package com.blockstackers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager{

    private final HashMap<UUID, Long> playersCooldownList = new HashMap<UUID, Long>();

    public HashMap<UUID, Long> getList()
    {
        return playersCooldownList;
    }

    public void add(UUID playerID, long cooldown)
    {
        playersCooldownList.put(playerID, System.currentTimeMillis() + (cooldown * 1000));
    }

    public boolean checkExist(UUID playerID)
    {
        return playersCooldownList.containsKey(playerID);
    }

    public long timeLeft(UUID playerID)
    {
        return ((playersCooldownList.get(playerID) / 1000) - (System.currentTimeMillis() / 1000));
    }

    public void displayCooldown(Player player, String message)
    {
        player.sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(
                message.replace("%cooldown%",
                        String.valueOf(timeLeft(player.getUniqueId())))));
    }
}
