package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.utils.Constants;
import de.highnoonmc.BuildSystem.utils.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class VanishCMD implements CommandExecutor {

    public static ArrayList<String> vanished = new ArrayList<>();
    private final JavaPlugin plugin;
    private final TitleAPI titleAPI = new TitleAPI();

    public VanishCMD(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (player.hasPermission("bauteam.leitung")) {
                if (args.length == 0) {
                    if (vanished.contains(player.getName())) {
                        vanished.remove(player.getName());
                        Bukkit.getOnlinePlayers().forEach(online -> {
                            titleAPI.sendTitle(online, Constants.VANISH_TITLE, Constants.VANISH_SUBTITLE_DEACTIVATED, 1, 1, 1);
                            online.hidePlayer(plugin, player);
                            if (online.hasPermission("bauteam.leitung")) {
                                online.sendMessage(Constants.PREFIX.append(Constants.NO_VANISH(player)));
                            }
                        });
                    } else {
                        vanished.add(player.getName());
                        titleAPI.sendTitle(player, Constants.VANISH_TITLE, Constants.VANISH_SUBTITLE_ACTIVATED, 1, 1, 1);
                        Bukkit.getOnlinePlayers().forEach(online -> {
                            online.showPlayer(plugin, player);
                            if (online.hasPermission("bauteam.leitung")) {
                                online.sendMessage(Constants.PREFIX.append(Constants.VANISH(player)));
                            }
                        });
                    }
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && target.isOnline()) {
                        if (vanished.contains(target.getName())) {
                            vanished.remove(target.getName());
                            titleAPI.sendTitle(target, Constants.VANISH_TITLE, Constants.VANISH_SUBTITLE_DEACTIVATED, 1, 1, 1);
                            Bukkit.getOnlinePlayers().forEach(online -> {
                                online.showPlayer(plugin, target);
                                if (online.hasPermission("bauteam.leitung")) {
                                    online.sendMessage(Constants.PREFIX.append(Constants.NO_VANISH(player)));
                                }
                            });
                        } else {
                            vanished.add(target.getName());
                            titleAPI.sendTitle(target, Constants.VANISH_TITLE, Constants.VANISH_SUBTITLE_ACTIVATED, 1, 1, 1);
                            Bukkit.getOnlinePlayers().forEach(online -> {
                                online.hidePlayer(plugin, target);
                                if (online.hasPermission("bauteam.leitung")) {
                                    online.sendMessage(Constants.PREFIX.append(Constants.VANISH(player)));
                                }
                            });
                        }
                    } else {
                        player.sendMessage(Constants.PLAYER_DOES_NOT_EXIST);
                    }
                }
            }
        }
        return true;
    }
}
