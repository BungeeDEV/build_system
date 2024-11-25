package de.highnoonmc.BuildSystem.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardAPI {

    public static void setScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.getObjective("aaa");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("aaa", "bbb");
        }

        Team leitung = getTeam(scoreboard, "0000Admin", "§cHead §7┃ ", "§7");
        Team bauteam = getTeam(scoreboard, "0001Bauteam", "§6Build §7┃ ", "§7");
        Team visitor = getTeam(scoreboard, "0002Besucher", "§7Visit §7┃ ", "§7");

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("bauteam.leitung")) {
                leitung.addPlayer(players);
            } else if (players.hasPermission("bauteam.team")) {
                bauteam.addPlayer(players);
            } else {
                visitor.addPlayer(players);
            }
        }

        objective.setDisplayName("§6§lHigh§eNoonMC");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore(" ").setScore(9);
        objective.getScore("§c✎ §7┃ Name").setScore(8);
        objective.getScore("§8» §6" + player.getName()).setScore(7);
        objective.getScore("  ").setScore(6);
        objective.getScore("§c✎ §7┃ Rang").setScore(5);
        if(player.hasPermission("bauteam.leitung")) {
            objective.getScore(updateTeam(scoreboard, "Rang", "§8» §c", "Head", ChatColor.RED)).setScore(4);
        } else if (player.hasPermission("bauteam.team")) {
            objective.getScore(updateTeam(scoreboard, "Rang", "§8» §6", "Build", ChatColor.GOLD)).setScore(4);
        } else {
            objective.getScore(updateTeam(scoreboard, "Rang", "§8» §7", "Visit", ChatColor.GRAY)).setScore(4);
        }
        objective.getScore("§a").setScore(3);
        objective.getScore("§c⚒ §7┃ World").setScore(2);
        objective.getScore("§8» §6" + player.getWorld().getName()).setScore(1);

        objective.getScore("§4").setScore(0);
        player.setScoreboard(scoreboard);
    }

    public static void setPrefix(Scoreboard scoreboard, Player player) {
        String team = "";

        if (player.hasPermission("bauteam.leitung")) {
            team = "0000Leitung";
        } else if (player.hasPermission("bauteam.team")) {
            team = "0001Team";
        } else {
            team = "0002Besucher";
        }
        scoreboard.getTeam(team).addPlayer(player);
        player.setDisplayName(scoreboard.getTeam(team).getPrefix() + player.getName());

        Bukkit.getOnlinePlayers().forEach(all -> {
            all.setScoreboard(scoreboard);
        });
    }

    public static Team getTeam(Scoreboard scoreboard, String Team, String prefix, String suffix) {
        Team team = scoreboard.getTeam(Team);
        if (team == null) {
            team = scoreboard.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setAllowFriendlyFire(false);
        return team;

    }

    public static String updateTeam(Scoreboard scoreboard, String Team, String prefix, String suffix, ChatColor entry) {
        Team team = scoreboard.getTeam(Team);
        if (team == null) {
            team = scoreboard.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.addEntry(entry.toString());

        return entry.toString();
    }

}
