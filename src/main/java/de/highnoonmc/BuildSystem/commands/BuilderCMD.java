package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.methods.BuildMenue;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuilderCMD implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("bauteam.team")) {
                BuildMenue.openBuilderInventory(player);
            }
        }
        return false;
    }
}
