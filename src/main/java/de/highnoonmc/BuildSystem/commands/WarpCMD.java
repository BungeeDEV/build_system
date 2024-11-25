package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.BuildSystem;
import de.highnoonmc.BuildSystem.utils.Constants;
import de.highnoonmc.BuildSystem.utils.TitleAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpCMD implements CommandExecutor {

    private final JavaPlugin plugin;
    
    public WarpCMD(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("bauteam.team")) {
                if (args.length == 1) {
                    String warp = args[0];
                    if (plugin.getConfig().get("Warp." + warp) == null) {
                        player.sendMessage(Constants.PREFIX.append(Constants.WARP_NOT_EXIST));
                        return false;
                    }

                    double x = plugin.getConfig().getDouble("Warp." + warp + ".X");
                    double y = plugin.getConfig().getDouble("Warp." + warp + ".Y");
                    double z = plugin.getConfig().getDouble("Warp." + warp + ".Z");
                    float yaw = (float) plugin.getConfig().getDouble("Warp." + warp + ".Yaw");
                    float pitch = (float) plugin.getConfig().getDouble("Warp." + warp + ".Pitch");
                    String world = plugin.getConfig().getString("Warp." + warp + ".World");
                    Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

                    player.teleport(location);
                    TitleAPI titleAPI = new TitleAPI();
                    titleAPI.sendTitle(player, Component.text("§7» §6Warp §7«"), Component.text("§7").append(Component.text(warp)), 1, 1, 1);
                    return true;

                } else if (args.length == 2) {
                    String subCommand = args[0];
                    if (subCommand.equalsIgnoreCase("set")) {
                        //SET WARP
                        String warp = args[1];
                        if (plugin.getConfig().get("Warp." + warp) != null) {
                            player.sendMessage(Constants.PREFIX.append(Constants.WARP_ALREADY_EXIST));
                            return false;
                        }
                        Location location = player.getLocation();

                        plugin.getConfig().set("Warp." + warp + ".World", location.getWorld().getName());
                        plugin.getConfig().set("Warp." + warp + ".X", location.getX());
                        plugin.getConfig().set("Warp." + warp + ".Y", location.getY());
                        plugin.getConfig().set("Warp." + warp + ".Z", location.getZ());
                        plugin.getConfig().set("Warp." + warp + ".Yaw", location.getYaw());
                        plugin.getConfig().set("Warp." + warp + ".Pitch", location.getPitch());

                        plugin.saveConfig();
                        // player.sendTitle("§7» §6Warp §7«", "§agespeichert", 15, 15, 15);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        return true;

                    } else if (subCommand.equalsIgnoreCase("delete")) {
                        //DELETE WARP
                        String warp = args[1];

                        if (plugin.getConfig().get("Warp." + warp) == null) {
                            player.sendMessage(Constants.PREFIX.append(Constants.WARP_NOT_EXIST));
                            return false;
                        }

                        plugin.getConfig().set("Warp." + warp, null);
                        plugin.saveConfig();
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        player.sendTitle("§7» §6Warp §7«", "§cgelöscht", 1, 1, 1);
                    } else {
                        player.sendMessage(Constants.PREFIX.append(Constants.WARP_CMD_USAGE));
                        return false;
                    }
                } else {
                    player.sendMessage(Constants.PREFIX.append(Constants.WARP_CMD_USAGE));
                    return false;
                }
            }
        }
        return false;
    }
}
