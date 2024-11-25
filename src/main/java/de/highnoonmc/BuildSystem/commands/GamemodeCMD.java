package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                switch (args[0]) {
                    case "1":
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendActionBar(Constants.PREFIX.append(Constants.OWN_GM_CHANGED_MSG(1)));
                        return true;
                    case "2":
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendActionBar(Constants.PREFIX.append(Constants.OWN_GM_CHANGED_MSG(2)));
                        return true;
                    case "3":
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendActionBar(Constants.PREFIX.append(Constants.OWN_GM_CHANGED_MSG(3)));
                        return true;
                    default:
                        return true;
                }
            } else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (target.hasPlayedBefore()) {
                        if (target.isOnline()) {
                            switch (args[0]) {
                                case "1":
                                    target.setGameMode(GameMode.CREATIVE);
                                    target.sendActionBar(Constants.PREFIX.append(Constants.OWN_GM_CHANGED_MSG(1)));
                                    player.sendMessage(Constants.PREFIX.append(Constants.OTHER_GM_CHANGED_MSG(1, target)));
                                    return true;
                                case "2":
                                    target.setGameMode(GameMode.ADVENTURE);
                                    target.sendActionBar(Constants.PREFIX.append(Constants.OWN_GM_CHANGED_MSG(2)));
                                    player.sendMessage(Constants.PREFIX.append(Constants.OTHER_GM_CHANGED_MSG(2, target)));
                                    return true;
                                case "3":
                                    target.setGameMode(GameMode.SPECTATOR);
                                    target.sendActionBar(Constants.PREFIX.append(Constants.OWN_GM_CHANGED_MSG(3)));
                                    player.sendMessage(Constants.PREFIX.append(Constants.OTHER_GM_CHANGED_MSG(3, target)));
                                    return true;
                            }
                        }
                    }
                }
            } else {
                player.sendMessage(Constants.PREFIX.append(Constants.GM_CMD_USAGE));
            }
        }
        return true;
    }
}
