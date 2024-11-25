package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("bauteam.team")) {
                player.getWorld().setTime(0);
                player.sendActionBar(Constants.TIME_CHANGED_DAY);
                return true;
            }
        }
        return true;
    }
}
