package de.highnoonmc.BuildSystem.listener;


import de.highnoonmc.BuildSystem.BuildSystem;
import de.highnoonmc.BuildSystem.commands.BuildCMD;
import de.highnoonmc.BuildSystem.commands.VanishCMD;
import de.highnoonmc.BuildSystem.scoreboard.ScoreboardAPI;
import de.highnoonmc.BuildSystem.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

import static de.highnoonmc.BuildSystem.commands.VisitCMD.visit;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.CREATIVE);
        if (player.hasPermission("bauteam.team")) {
            event.joinMessage(Constants.JOIN_MESSAGE(player));
        } else {
            event.joinMessage(null);
        }

        //SCOREBOARD
        ScoreboardAPI.setScoreboard(player);
        Bukkit.getOnlinePlayers().forEach(ScoreboardAPI::setScoreboard);
    }

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        if (event.getPlayer().hasPermission("bauteam.team")) {
            event.allow();
        } else {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Constants.PREFIX.append(Component.text("Dieser Server ist nur für das Bauteam zugänglich!")));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("bauteam.team") || visit.contains(player.getUniqueId())) {
            event.quitMessage(Constants.LEAVE_MESSAGE(player));
        } else {
            event.quitMessage(null);
        }
        BuildCMD.build.remove(player);
        VanishCMD.vanished.remove(player);
    }
}
