package org.slavawins.winslib;

import org.bukkit.plugin.java.JavaPlugin;

public final class Winslib extends JavaPlugin {

    private static JavaPlugin instanse;

    public static JavaPlugin getInstanse() {
        return instanse;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instanse = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
