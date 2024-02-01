package org.slavawins.winslib.helper;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Objects;

public class TakerItemHelper {

    public static boolean eqalsItems(ItemStack search, ItemStack to) {

        if(to==null)return  false;
        if(search==null)return  false;

        if (search.getType() != to.getType()) {
            return false;
        }

        ItemMeta searchMeta = search.getItemMeta();
        ItemMeta toMeta = to.getItemMeta();

        PersistentDataContainer searchData = searchMeta.getPersistentDataContainer();
        PersistentDataContainer toData = toMeta.getPersistentDataContainer();

        for (NamespacedKey key : searchData.getKeys()) {
            if (key.toString().equals("ammo")) continue;
            if (key.toString().equals("uniqname")) continue;
            if (key.toString().equals("gunid")) continue;
            if (key.toString().equals("hp")) continue;


            if (!toData.has(key, PersistentDataType.STRING)) {
                return false;
            }

            if (!Objects.equals(searchData.get(key, PersistentDataType.STRING), toData.get(key, PersistentDataType.STRING))) {
                return false;
            }
        }
        return true;
    }

    public static int getCountItem(Player player, ItemStack valuteItem) {
        ItemStack[] items = player.getInventory().getContents();
        int count = 0;

        for (ItemStack item : items) {
            if (TakerItemHelper.eqalsItems(valuteItem, item)) {
                count += item.getAmount();
            }
        }

        return count;
    }

    public static void takeItem(Player player, ItemStack goldIngot) {

        HashMap<Integer, ItemStack> remainingItems = player.getInventory().removeItem(goldIngot);

        if (!remainingItems.isEmpty()) {
            for (ItemStack item : remainingItems.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
    }

}
