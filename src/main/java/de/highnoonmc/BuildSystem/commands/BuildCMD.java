package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.utils.Constants;
import de.highnoonmc.BuildSystem.utils.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BuildCMD implements CommandExecutor {

    public static ArrayList<Player> build = new ArrayList<>();

    private TitleAPI titleAPI = new TitleAPI();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("bauteam.team")) {
            if (args.length == 0) {
                if (build.contains(player)) {
                    build.remove(player);
                    titleAPI.sendTitle(player, Constants.BUILD_TITLE, Constants.BUILD_SUBTITLE_DEACTIVATED,1, 1, 1);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                } else {
                    build.add(player);
                    titleAPI.sendTitle(player, Constants.BUILD_TITLE, Constants.BUILD_SUBTITLE_ACTIVATED,1, 1, 1);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                }
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (build.contains(target)) {
                    build.remove(target);
                    titleAPI.sendTitle(target, Constants.BUILD_TITLE, Constants.BUILD_SUBTITLE_DEACTIVATED,1, 1, 1);
                    player.sendActionBar(Constants.PREFIX.append(Constants.BUILD_TARGET(false, target)));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                } else {
                    build.add(target);
                    titleAPI.sendTitle(target, Constants.BUILD_TITLE, Constants.BUILD_SUBTITLE_ACTIVATED,1, 1, 1);
                    player.sendActionBar(Constants.PREFIX.append(Constants.BUILD_TARGET(true, target)));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                }
            }
        }
        return false;
    }
}
