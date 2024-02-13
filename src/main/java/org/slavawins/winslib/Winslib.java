package org.slavawins.winslib;

import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.winslib.listener.AuthCommandListner;

public final class Winslib extends JavaPlugin {

    private static JavaPlugin instanse;

    public static JavaPlugin getInstanse() {
        return instanse;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instanse = this;

        getServer().getPluginManager().registerEvents(new AuthCommandListner(), this);
        getCommand("winslibauthevent").setExecutor(new AuthCommandListner());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
