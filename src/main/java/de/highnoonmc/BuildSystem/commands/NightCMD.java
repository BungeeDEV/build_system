package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NightCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("bauteam.team")) {
                player.getWorld().setTime(20000);
                player.sendActionBar(Constants.TIME_CHANGED_NIGHT);
                return true;
            }
        }
        return true;
    }
}