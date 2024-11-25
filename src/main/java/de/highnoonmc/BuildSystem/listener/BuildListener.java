package de.highnoonmc.BuildSystem.listener;

import de.highnoonmc.BuildSystem.commands.BuildCMD;
import de.highnoonmc.BuildSystem.utils.Constants;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (BuildCMD.build.contains(player)) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
            player.sendActionBar(Constants.PREFIX.append(Constants.PLACE_BREAK_BLOCK));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (BuildCMD.build.contains(player)) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
         player.sendActionBar(Constants.PREFIX.append(Constants.PLACE_BREAK_BLOCK));
        }
    }
}
