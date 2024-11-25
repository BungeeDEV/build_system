package de.highnoonmc.BuildSystem.methods;

import de.highnoonmc.BuildSystem.BuildSystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BuildMenue {

    public static void openBuilderInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9*3, "§6§lBuilder Inventar");

        ItemStack glassStack = StackManager.createStack(Material.BLACK_STAINED_GLASS_PANE, 1, "");
        ItemStack buildStack = StackManager.createStack(Material.DIAMOND_PICKAXE, 1, "§7» §6Build Modus");
        ItemStack gameModeStack = StackManager.createStack(Material.FEATHER, 1, "§7» §6Flug Speed");
        ItemStack vanishStack = StackManager.createStack(Material.REDSTONE, 1, "§7» §6Vanish");
        ItemStack worldsStack = BuildSystem.h2.clone();
       // ItemStack warpsStack = Main.getSkull("http://textures.minecraft.net/texture/da40cc92194d2e0c6ddccd9ee03ffcda66fb469f4e23de7ed7293f9235597ec0", "§7» §6Warps");
      //  ItemStack headStack = Main.getSkull("http://textures.minecraft.net/texture/badc048a7ce78f7dad72a07da27d85c0916881e5522eeed1e3daf217a38c1a", "§7» §6Heads");

        for (int i = 0; i < 3*9; i++) {
            inventory.setItem(i, glassStack);
        }
        inventory.setItem(10, buildStack);
        inventory.setItem(11, gameModeStack);
        inventory.setItem(12, vanishStack);
        inventory.setItem(14, worldsStack);
   //     inventory.setItem(15, warpsStack);
   //     inventory.setItem(16, headStack);

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
    }

}
