package de.highnoonmc.BuildSystem.listener;

import de.highnoonmc.BuildSystem.BuildSystem;
import de.highnoonmc.BuildSystem.utils.TitleAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class InventoryListener implements Listener {
    
    private final JavaPlugin plugin;
    
    public InventoryListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(event.getPlayer().getInventory().getHeldItemSlot() == 8);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getItemMeta() == null) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getPlayer().getItemInHand().getType() != Material.NETHER_STAR) return;
            if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Teleporter")) {
                Inventory inventory = Bukkit.createInventory(null, 27, "§7» §6§lTeleporter");

                ItemStack first = new ItemStack(Material.COMPASS, 1);
                ItemMeta firstmeta = first.getItemMeta();
                firstmeta.setDisplayName("§7» §6§lTemplate");
                first.setItemMeta(firstmeta);

                ItemStack second = new ItemStack(Material.OAK_SAPLING, 1);
                ItemMeta secondmeta = second.getItemMeta();
                secondmeta.setDisplayName("§7» §6§lOld Tree");
                second.setItemMeta(secondmeta);

                final ItemStack three = new ItemStack(Material.OAK_SAPLING, 1, (byte) 3);
                ItemMeta threemeta = three.getItemMeta();
                threemeta.setDisplayName("§7» §6§lNew Tree");
                three.setItemMeta(threemeta);

                final ItemStack head = new ItemStack(Material.LEGACY_SKULL, 1, (byte) 3);
                final SkullMeta headmeta = (SkullMeta) head.getItemMeta();
                headmeta.setDisplayName("§7» §6§lKöpfe");
                headmeta.setOwner(event.getPlayer().getName());
                head.setItemMeta(headmeta);

                final ItemStack i = BuildSystem.h.clone();
                final SkullMeta im = (SkullMeta) i.getItemMeta();
                im.setDisplayName("§7» §6§lWelten");
                i.setItemMeta(im);

                inventory.setItem(10, first);
                inventory.setItem(13, second);
                inventory.setItem(16, head);

                event.getPlayer().openInventory(inventory);
                event.setCancelled(true);
            } else {
                event.setCancelled(false);
            }
        } else {
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void onTeleporterInteract(InventoryClickEvent event) {

        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getClickedInventory() == null) return;

        // To get PlainText from Items and Inventorys
        PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.plainText();

        Component inventoryDisplayName = event.getView().title();
        String invTitle = plainTextComponentSerializer.serialize(inventoryDisplayName);

        if (invTitle.contains("Teleporter")) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Template")) {
                player.performCommand("mv tp template");
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Old Tree")) {
                player.performCommand("mv tp Tree");
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Welten")) {
                BuildSystem.openWorldMenu(player);
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Köpfe")) {
                player.performCommand("heads");
            } else {
                event.setCancelled(false);
            }
        } else if (invTitle.contains("Betretbare Welten")) {
            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Component itemDisplayName = event.getCurrentItem().getItemMeta().displayName();
            assert itemDisplayName != null;
            String itemName = plainTextComponentSerializer.serialize(itemDisplayName);

            if (event.isLeftClick()) {
                if (Bukkit.getWorld(itemName) == null) {
                    player.sendMessage(BuildSystem.prefix + "§cDiese Welt existiert nicht!");
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
                    return;
                }
                if (player.getWorld() == Bukkit.getWorld(itemName)) {
                    player.sendMessage(BuildSystem.prefix + "§cDu bist bereits in dieser Welt!");
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
                    return;
                }
                player.closeInventory();
                player.teleport(Objects.requireNonNull(Bukkit.getWorld(itemName)).getSpawnLocation());
                player.performCommand("gm 1");
                player.sendMessage(BuildSystem.prefix + "§7Du wurdest teleportiert.");
            }
        } else if (invTitle.contains("Warps")) {
            final String d = event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§e", "");
            event.setCancelled(true);

            if (plugin.getConfig().get("Warp." + d) == null) {
                player.sendMessage(BuildSystem.prefix + "§cDieser Warp existiert nicht...");
                return;
            }

            double x = plugin.getConfig().getDouble("Warp." + d + ".X");
            double y = plugin.getConfig().getDouble("Warp." + d + ".Y");
            double z = plugin.getConfig().getDouble("Warp." + d + ".Z");
            float yaw = (float) plugin.getConfig().getDouble("Warp." + d + ".Yaw");
            float pitch = (float) plugin.getConfig().getDouble("Warp." + d + ".Pitch");
            String world = plugin.getConfig().getString("Warp." + d + ".World");
            Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

            player.teleport(location);
            player.sendTitle("§7» §6Warp §7«", "§7" + d, 15, 15, 15);
        }
    }
}
