package de.highnoonmc.BuildSystem;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.highnoonmc.BuildSystem.commands.*;
import de.highnoonmc.BuildSystem.listener.*;
import de.highnoonmc.BuildSystem.utils.ConfigManager;
import de.highnoonmc.talerapi.TalerAPI;
import de.highnoonmc.talerapi.TalerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.codehaus.plexus.util.Base64;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BuildSystem extends JavaPlugin {

    public static String prefix = "§8§l» §6§lBauSystem §r§7× ";
    public static ItemStack h;
    public static ItemStack h2;
    private static BuildSystem instance;
    private TalerAPI talerAPI;
    public ConfigManager configManager;

    public static BuildSystem getPlugin() {
        return instance;
    }

    public static void openWorldMenu(final Player player) {
        final List<ItemStack> l = new ArrayList<>();
        final ItemStack forwardItem = BuildSystem.h2.clone();
        final SkullMeta forwardMeta = (SkullMeta) forwardItem.getItemMeta();
        forwardMeta.displayName(Component.text("Weiter...", TextColor.color(255, 255, 85), TextDecoration.BOLD));
        forwardItem.setItemMeta(forwardMeta);

        final ItemStack glassStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        final ItemMeta glassStackMeta = glassStack.getItemMeta();
        glassStackMeta.displayName(Component.text(" ", TextColor.color(0, 0, 0)));
        glassStack.setItemMeta(glassStackMeta);

        for (final World w : Bukkit.getWorlds()) {
            final ItemStack i = BuildSystem.h.clone();
            final SkullMeta im = (SkullMeta) i.getItemMeta();
            im.displayName(Component.text(w.getName(), TextColor.color(255, 255, 85)));
            i.setItemMeta(im);
            l.add(i);
        }
        Inventory inv = null;
        inv = Bukkit.createInventory(null, 9 * 6, Component.text("Betretbare Welten", TextColor.color(255, 170, 0)));

        for (int i = 36; i <= 44; i++) {
            inv.setItem(i, glassStack);
        }

        int i = 0;
        for (ItemStack itemStack : l) {
            if (i <= 36) {
                inv.addItem(itemStack);
                i++;
            }
        }

        inv.setItem(50, forwardItem);
        player.openInventory(inv);
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
    }

    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        talerAPI = new TalerAPI();
        talerAPI.init(this);
        saveDefaultConfig();
        init();
    }

    public void onDisable() {

    }

    private void init() {
        registerCommands();
        registerEvents();
        setupConfigs();
        BuildSystem.h = getSkull("http://textures.minecraft.net/texture/4ef4874eb050a3d3e46f69ae40c3b59a3596f71664a0db6ca600d5c67f47e4", "\ufffdeEMPTY");
        BuildSystem.h2 = getSkull("https://textures.minecraft.net/texture/5a92ad456f76ec7caa3595922d5fcc38dca928dc6715f752e74c8ddde344e", "§7» §6Worlds");

        this.getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void setupConfigs() {
        configManager.newConfig("messages");
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JoinLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new ExplodeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);
        Bukkit.getPluginManager().registerEvents(new WeatherListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new VisitCMD(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
    }

    private void registerCommands() {
        getCommand("gm").setExecutor(new GamemodeCMD());
        getCommand("day").setExecutor(new DayCMD());
        getCommand("night").setExecutor(new NightCMD());
        getCommand("build").setExecutor(new BuildCMD());
        getCommand("worlds").setExecutor(new WorldsCMD());
        getCommand("vanish").setExecutor(new VanishCMD(this));
        getCommand("warp").setExecutor(new WarpCMD(this));
        getCommand("warps").setExecutor(new WarpsCMD(this));
        getCommand("builder").setExecutor(new BuilderCMD());
        getCommand("invsee").setExecutor(new InvseeCMD());
        getCommand("visit").setExecutor(new VisitCMD(this));
    }

    private ItemStack getSkull(final String url, final String displayname) {
        final ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        if (url.isEmpty()) {
            return head;
        }
        final SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        final byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex2) {
            final Exception ex;
            ex2.printStackTrace();
        }
        headMeta.displayName(Component.text(displayname, TextColor.color(0, 0, 0)));
        head.setItemMeta(headMeta);
        return head;
    }

    private void openSecondWorldMenu(final Player player) {
        final List<ItemStack> l = new ArrayList<ItemStack>();
        final ItemStack forwardItem = BuildSystem.h2.clone();
        final SkullMeta forwardMeta = (SkullMeta) forwardItem.getItemMeta();
        forwardMeta.setDisplayName("§6§lWeiter...");
        forwardItem.setItemMeta(forwardMeta);

        final ItemStack glassStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1, (short) 0, (byte) 15);
        final ItemMeta glassStackMeta = glassStack.getItemMeta();
        glassStackMeta.setDisplayName("§a ");
        glassStack.setItemMeta(glassStackMeta);

        for (final World w : Bukkit.getWorlds()) {
            final ItemStack i = BuildSystem.h.clone();
            final SkullMeta im = (SkullMeta) i.getItemMeta();
            im.setDisplayName("§e" + w.getName());
            i.setItemMeta(im);
            l.add(i);
        }
        Inventory inv = null;

        inv = Bukkit.createInventory(null, 9 * 6, "Welten...");

        for (int i = 36; i <= 44; i++) {
            inv.setItem(i, glassStack);
        }

        for (int i = 0; i <= 36; i++) {
            final Iterator<ItemStack> iterator = l.iterator();
            while (iterator.hasNext()) {
                final ItemStack itemStack = iterator.next();
                inv.addItem(itemStack);
            }
        }

        inv.setItem(50, forwardItem);

        player.openInventory(inv);
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
    }

}
