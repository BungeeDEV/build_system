package de.highnoonmc.BuildSystem.commands;

import de.highnoonmc.BuildSystem.BuildSystem;
import de.highnoonmc.BuildSystem.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatClearCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            for (int i = 0; i < 50; i++) {
                Bukkit.broadcast(Component.empty());
            }
            Bukkit.broadcast(Constants.PREFIX.append(Constants.CHAT_CLEARED(player)));
        } else {
            sender.sendMessage("No access!");
            return true;
        }
        return true;
    }
}
