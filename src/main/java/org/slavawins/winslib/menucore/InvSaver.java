package org.slavawins.winslib.menucore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InvSaver {

    private static String GetFoolderByPluginName(String pluginName){
        return "plugins/" + pluginName+"/inventories/";
    }
    public static void saveInventory(Inventory inventory, String name, String pluginName) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("inventory", inventory.getContents());
        // Указать путь к файлу, в котором будет сохранен инвентарь
        File file = new File(GetFoolderByPluginName(pluginName) +  name + ".yml");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Inventory getInvLoad(String name, int rows, String pluginName) {
        Inventory inv = Bukkit.createInventory(null, 9 * rows, "Загруженый " + name);

        if (!loadInventory(inv, name, pluginName)) {
            inv = null;
            return null;
        }

        return inv;
    }

    public static boolean loadInventory(Inventory inventory, String name, String pluginName) {
        File file = new File(GetFoolderByPluginName(pluginName) +  name + ".yml");

        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            ItemStack[] contents = ((List<ItemStack>) config.get("inventory")).toArray(new ItemStack[0]);
            inventory.setContents(contents);
            return true;
        }
        return false;
    }

    // Загрузка инвентаря из файла
    public static void remove(String name, String pluginName) {
        File file = new File(GetFoolderByPluginName(pluginName) +  name + ".yml");

        if (!file.exists()) return;


        file.delete();
    }

}
