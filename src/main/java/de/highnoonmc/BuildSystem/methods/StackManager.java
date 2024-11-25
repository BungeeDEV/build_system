package de.highnoonmc.BuildSystem.methods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class StackManager {

    public static ItemStack createStack(Material material, int amount, String name) {

        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(Component.text(name));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack createColoredArmor(Material material, int amount, String name, Color color) {
        ItemStack stack = new ItemStack(material, amount);
        LeatherArmorMeta colorMeta = (LeatherArmorMeta) stack.getItemMeta();
        colorMeta.setDisplayName(name);
        colorMeta.setColor(color);
        stack.setItemMeta(colorMeta);

        return stack;
    }

    public static ItemMeta setLore(ItemStack is, List<String> lore) {

        ItemMeta meta = is.getItemMeta();
        meta.setLore(lore);
        is.setItemMeta(meta);

        return meta;
    }

    public static ItemStack createStackByMaterial(Material material, int amount, String name) {
        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', name)));
        stack.setItemMeta(meta);
        return stack;
    }
}
