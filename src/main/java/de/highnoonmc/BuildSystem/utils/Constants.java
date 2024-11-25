package de.highnoonmc.BuildSystem.utils;

import de.highnoonmc.BuildSystem.BuildSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Constants {

    private static final FileConfiguration messagesConfig = BuildSystem.getPlugin().configManager.getConfig("messages").getCustomConfig();
    public static Component PREFIX = MiniMessage.miniMessage().deserialize(messagesConfig.getString("prefix"));

    public static Component PLAYER_DOES_NOT_EXIST = PREFIX.append(MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.player-not-existing")));
    public static Component TIME_CHANGED_DAY = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.time_changed.day"));
    public static Component TIME_CHANGED_NIGHT = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.time_changed.night"));

    public static Component FOLLOW_TITLE_FOLLOWER = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.follow.title.follower"));
    public static Component GM_CMD_USAGE = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.gm.usage"));
    public static Component INVSEE_CMD_USAGE = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.invsee.usage"));
    public static Component VANISH_TITLE = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.vanish.title"));
    public static Component VANISH_SUBTITLE_ACTIVATED = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.vanish.subtitle.activated"));
    public static Component VANISH_SUBTITLE_DEACTIVATED = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.vanish.subtitle.deactivated"));
    public static Component VISIT_PLAYERS_CAN_JOIN = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.visit.players-can-join"));
    public static Component VISIT_CMD_USAGE = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.visit.usage"));
    public static Component WARP_NOT_EXIST = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.warp.not-exist"));
    public static Component WARP_ALREADY_EXIST = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.warp.already-exist"));
    public static Component WARP_CMD_USAGE = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.warp.usage"));
    public static Component WARPS_NO_WARPS = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.warps.no-warps"));
    public static Component BUILD_TITLE = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.build.title"));
    public static Component BUILD_SUBTITLE_ACTIVATED = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.build.subtitle-activated"));
    public static Component BUILD_SUBTITLE_DEACTIVATED = MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.build.subtitle-deactivated"));
    public static Component PLACE_BREAK_BLOCK = MiniMessage.miniMessage().deserialize(messagesConfig.getString("listener.place-break-block"));

    public static Component CHAT_CLEARED(Player player) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.chat_cleared"), Placeholder.component("name", player.displayName()));
    }

    public static Component FOLLOW_SUBTITLE(Player target) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.follow.title.subtitle"), Placeholder.component("name", target.displayName()));
    }

    public static Component FOLLOW_MESSAGE_FOLLOWED(Player target) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.follow.message.followed"), Placeholder.component("name", target.displayName()));
    }

    public static Component OWN_GM_CHANGED_MSG(int gamemode) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.gm.own"), Placeholder.component("gm", Component.text(gamemode)));
    }

    public static Component OTHER_GM_CHANGED_MSG(int gamemode, Player target) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.gm.other"), Placeholder.component("gm", Component.text(gamemode)), Placeholder.component("name", target.displayName()));
    }

    public static Component NO_VANISH(Player player) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.vanish.no-vanish"), Placeholder.component("name", player.displayName()));
    }

    public static Component VANISH(Player player) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.vanish.vanish"), Placeholder.component("name", player.displayName()));
    }

    public static Component VISIT_PLAYER_CANT_JOIN(OfflinePlayer player) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.visit.player-cant-join"), Placeholder.component("name", Component.text(player.getName())));
    }

    public static Component VISIT_PLAYER_CAN_JOIN(OfflinePlayer player) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.visit.player-can-join"), Placeholder.component("name", Component.text(player.getName())));
    }

    public static Component VISIT_CMD_TRIED_JOIN(Player player) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.visit.tried-join"), Placeholder.component("name", player.displayName()));
    }

    public static Component LEAVE_MESSAGE(Player player) {
        return PREFIX.append(MiniMessage.miniMessage().deserialize(messagesConfig.getString("leave-message"), Placeholder.component("name", player.displayName())));
    }

    public static Component JOIN_MESSAGE(Player player) {
        return PREFIX.append(MiniMessage.miniMessage().deserialize(messagesConfig.getString("join-message"), Placeholder.component("name", player.displayName())));
    }


    public static Component BUILD_TARGET(boolean state, Player target) {
        return MiniMessage.miniMessage().deserialize(messagesConfig.getString("commands.build.target"), Placeholder.component("state", state ? Component.text("gegeben") : Component.text("entfernt")), Placeholder.component("name", target.displayName()));
    }

}
