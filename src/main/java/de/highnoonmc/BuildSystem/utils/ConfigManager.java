package de.highnoonmc.BuildSystem.utils;

import de.highnoonmc.BuildSystem.BuildSystem;

import java.util.HashMap;
import java.util.logging.Level;

public class ConfigManager {

    private final HashMap<String, CustomConfig> configs = new HashMap<>();
    private final BuildSystem plugin = BuildSystem.getPlugin();

    public void newConfig(String name) {
        if (!configs.containsKey(name)) {
            CustomConfig config = new CustomConfig(name);
            configs.put(name, config);
        } else {
            plugin.getLogger().log(Level.SEVERE, "[1676856767] Config " + name + " couldn't be created.");
        }
    }

    public CustomConfig getConfig(String name) {
        if (configs.containsKey(name)) {
            return configs.get(name);
        } else {
            plugin.getLogger().log(Level.SEVERE, "[1676356767] Config " + name + " couldn't be loaded.");
            return null;
        }
    }
}