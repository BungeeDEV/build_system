package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.BuildSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldsCMD implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            BuildSystem.openWorldMenu(player);
        } else {
            return true;
        }
        return true;
    }
}
