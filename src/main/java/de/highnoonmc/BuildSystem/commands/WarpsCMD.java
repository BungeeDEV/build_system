package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.BuildSystem;
import de.highnoonmc.BuildSystem.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class WarpsCMD implements CommandExecutor {
    
    private final JavaPlugin plugin;
    
    public WarpsCMD(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ArrayList<String> list = new ArrayList<>();
    Inventory inventory = Bukkit.createInventory(null, 9*6, "Warps");

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (player.hasPermission("bauteam.team")) {
                if (args.length == 0) {
                    inventory.clear();
                    if(plugin.getConfig().getConfigurationSection("Warp") != null) {
                        list.addAll(plugin.getConfig().getConfigurationSection("Warp").getKeys(false));
                        for (int i = 0; i < list.size(); i++) {
                            final ItemStack itemStack = BuildSystem.h.clone();
                            final SkullMeta im = (SkullMeta)itemStack.getItemMeta();
                            im.setDisplayName("§e" + list.get(i));
                            itemStack.setItemMeta(im);
                            inventory.setItem(i, itemStack);
                        }
                        player.openInventory(inventory);
                        list.clear();
                        return true;
                    } else {
                        player.sendMessage(Constants.PREFIX.append(Constants.WARPS_NO_WARPS));
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
