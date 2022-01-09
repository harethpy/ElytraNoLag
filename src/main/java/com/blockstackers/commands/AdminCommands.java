package com.blockstackers.commands;

import com.blockstackers.config.ConfigManager;
import com.blockstackers.elytranolag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminCommands implements CommandExecutor {

    elytranolag plugin;
    ConfigManager configManager;

    public AdminCommands(elytranolag plugin)
    {
        this.plugin = plugin;
        this.configManager = new ConfigManager(plugin);
    }

    String[] helpMessage =
            {
                    "§e§l--------- §rElytraNoLag Help §r§e§l-----",
                    "§6/enl addlist <player>: §fAdds Player to the Unaffected List",
                    "§6/enl dellist <player>: §Removes Player from the Unaffected List",
                    "§6/enl showlist: §fShows Unaffected Players List.",
                    "§6/enl cdperiod <second(s)>: §fSet Cooldown Period.",
                    "§6/enl cdmsg <message>: §fSet Cooldown Message.",
                    "§6/enl reload: §fReloads the Plugin",
                    "§e§l--------- §rElytraNoLag Help §r§e§l-----"
            };


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))
            return true;

        Player player = (Player) sender;

        if(args.length > 0)
        {
            // ------ Display usable Commands ------
            if(args[0].equalsIgnoreCase("help"))
                player.sendMessage(String.join("\n", helpMessage));

            // ------ Reloads Config ------
            if(player.hasPermission("com.blockstackers.enl.reload")
                    && args[0].equalsIgnoreCase("reload"))
            {
                configManager.reloadCfg();
                player.sendMessage("§5§lElytraNoLag§r - §aReloaded!");
            }

            // ------ Display Unaffected Players List ------
            if(args[0].equalsIgnoreCase("showlist"))
            {
                List<String> tempWhitelist = configManager.getNameList();
                player.sendMessage("§e§l--------- §rElytraNoLag§r Unaffected List §r§e§l-----");
                for (String s : tempWhitelist)
                {
                    if(Bukkit.getServer().getPlayerExact(s) != null)
                        player.sendMessage(ChatColor.GREEN + s);
                }
                for (String s : tempWhitelist)
                {
                    if(Bukkit.getServer().getPlayerExact(s) == null)
                        player.sendMessage(ChatColor.RED + s);
                }
                player.sendMessage("§e§l--------- §rElytraNoLag§r Unaffected List §r§e§l-----");
            }

            if(args.length > 1)
            {

                if(args[0].equalsIgnoreCase("addlist"))
                {
                    configManager.addPlayer(args[1]);
                    player.sendMessage("Player (" + args[1] + ") §a§lHave been added to the List");
                }

                if(args[0].equalsIgnoreCase("dellist"))
                {
                    configManager.removePlayer(args[1]);
                    player.sendMessage("Player (" + args[1] + ") §c§lHave been removed from the List");
                }

                // Note:
                // Not sure If it's better to put int or string, String in the meantime for safe
                if(args[0].equalsIgnoreCase("cdperiod"))
                {
                    try
                    {
                        configManager.setCooldownPeriod(Integer.parseInt(args[1]));
                        player.sendMessage("Cooldown changed to " + args[1] +" second(s)");
                    } catch (NumberFormatException e)
                    {
                        player.sendMessage("§6/enl cdperiod <seconds>");
                    }

                }
                if(args[0].equalsIgnoreCase("cdmsg"))
                {
                    if(String.join(",", args).contains("%cooldown%"))
                    {
                        StringBuilder msg = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            msg.append(args[i]).append(" ");
                        }
                        System.out.println(msg);
                        configManager.setCooldownMsg(String.valueOf(msg));
                        player.sendMessage("§aCooldown Message changed to §r\n" + msg);

                    }
                    else
                    {
                        player.sendMessage("§cInvalid Config, no %cooldown% in message. Make sure it's configured right");
                    }
                }
            }
            // super secret
            if(args[0].equalsIgnoreCase("test") && player.hasPermission("com.blockstackers.enl.supersecretcommand"))
            {
                ItemStack elytra = new ItemStack(Material.ELYTRA);
                ItemStack rockets = new ItemStack(Material.FIREWORK_ROCKET);
                rockets.setAmount(64);
                player.getInventory().addItem(elytra);
                player.getInventory().addItem(rockets);

                player.sendMessage("Woosh! Given testing gear!");
            }
        }
        else
        {
            player.sendMessage(String.join("\n", helpMessage));
        }


        return true;
    }
}
