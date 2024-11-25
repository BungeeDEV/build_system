package de.highnoonmc.BuildSystem.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;

public class TitleAPI {

    public void sendTitle(Player player, Component mainTitle, Component subTitle, int fadeIn, int duration, int fadeOut) {
        final Title.Times times = Title.Times.times(Duration.ofSeconds(fadeIn), Duration.ofSeconds(duration), Duration.ofSeconds(fadeOut));
        final Title title = Title.title(mainTitle, subTitle, times);
        player.showTitle(title);
    }

}
