package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.BuildSystem;
import de.highnoonmc.BuildSystem.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public class VisitCMD implements CommandExecutor, Listener {

    public static ArrayList<UUID> visit = new ArrayList<>();
    private final JavaPlugin plugin;

    public VisitCMD(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player player)) return true;
        if (player.hasPermission("bauteam.visit")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    player.sendMessage(Constants.PREFIX.append(Constants.VISIT_PLAYERS_CAN_JOIN));
                    visit.forEach(visitors -> {
                        player.sendMessage(Constants.PREFIX.append(Component.text("      - §6§l" + visitors)));
                    });
                    return true;
                } else {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (visit.contains(target.getUniqueId())) {
                        visit.remove(target.getUniqueId());
                        player.sendMessage(Constants.PREFIX.append(Constants.VISIT_PLAYER_CANT_JOIN(target)));
                        return true;
                    } else {
                        visit.add(target.getUniqueId());
                        player.sendMessage(Constants.PREFIX.append(Constants.VISIT_PLAYER_CAN_JOIN(target)));
                        return true;
                    }
                }
            } else {
                player.sendMessage(Constants.PREFIX.append(Constants.VISIT_CMD_USAGE));
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("bauteam.team")) {
            if (visit.contains(event.getPlayer().getUniqueId())) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.showPlayer(plugin, player);
                }
                player.setGameMode(GameMode.CREATIVE);
                player.setFlying(true);
            } else {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.hasPermission("bauteam.team")) {
                        all.sendMessage(Constants.PREFIX.append(Constants.VISIT_CMD_TRIED_JOIN(player)));
                    }
                    all.hidePlayer(plugin, player);
                }
                player.kick(Constants.PREFIX.append(Component.text("Dieser Server ist nur für das Bauteam zugänglich!")));
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandSend(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (visit.contains(player.getUniqueId())) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.hasPermission("bauteam.spy")) {
                    all.sendMessage("§7[§4DEBUG§7] ┃ §c" + player.getDisplayName() + " §7used » " + message);
                } else {
                    return;
                }
            }
        }
    }
}
