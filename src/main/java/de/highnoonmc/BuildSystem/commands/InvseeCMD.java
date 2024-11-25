package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.BuildSystem;
import de.highnoonmc.BuildSystem.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (player.hasPermission("bauteam.leitung")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != player) {
                        if (target != null && target.isOnline()) {
                            player.openInventory(target.getInventory());
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                            return true;
                        } else {
                            player.sendMessage(Constants.PLAYER_DOES_NOT_EXIST);
                            return true;
                        }
                    }
                } else {
                    player.sendMessage(Constants.PREFIX.append(Constants.INVSEE_CMD_USAGE));
                }
            }
        }
        return true;
    }
}
