package de.highnoonmc.BuildSystem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

     @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
         Player player = event.getPlayer();
         String message = event.getMessage();
         if (message.contains("%")) {
             message = message.replaceAll("%", "Prozent");
         }

         if (player.hasPermission("bauteam.leitung")) {
             event.setFormat("§cHead §7┃ " + player.getName() + " » " + message);
         } else if (player.hasPermission("bauteam.team")) {
             event.setFormat("§6Build §7┃ " + player.getName() + " » " + message);
         } else {
             event.setFormat("§7" + player.getName() + " » " + message);
         }
     }
}
