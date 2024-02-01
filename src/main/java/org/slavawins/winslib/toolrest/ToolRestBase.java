package org.slavawins.winslib.toolrest;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.slavawins.winslib.helper.MetaContainerHelper;

import java.util.ArrayList;
import java.util.List;

public class ToolRestBase implements Listener {


    public String pluginName = "ХПЛАГИН";
    public List<ToolRestModel> toolsList = new ArrayList<>();
    private JavaPlugin plugin;


    public void GivePlayer(Player player) {

        PlayerInventory inventory = player.getInventory();

        // Проход по списку всех инструментов
        for (ToolRestModel tool : toolsList) {
            // Создание предмета

            ItemStack item = new ItemStack(tool.material);


            // item.addEnchantment(Enchantment.LUCK, 1);

            ItemMeta meta = item.getItemMeta();



            MetaContainerHelper.Set(meta, "toolcmd", tool.getIdFromCommdnRest());


            // Настройка имени предмета
            meta.setDisplayName(ChatColor.BLUE + tool.name + " [" + pluginName + "]");
            List<String> lore = new ArrayList<>();
            lore.add(tool.desctiption);
            lore.add(tool.getIdFromCommdnRest());
            meta.setLore(lore);
            item.setItemMeta(meta);

            // Добавление предмета в инвентарь игрока
            inventory.addItem(item);
        }

        player.sendMessage("Набор предметов для плагина " + pluginName + " успешно выдан.");

    }


    private ToolRestModel ValidateEvent(Player player, ItemStack itemStack) {

        if (!player.isOp()) return null;
        if (itemStack == null) return null;


        String id = MetaContainerHelper.GetString(itemStack.getItemMeta(), "toolcmd");
        if (id.isEmpty()) return null;


        return GetToolById(id);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

       // Bukkit.broadcastMessage(" TO onInteract");

        ItemStack itemStack = event.getItem();
        ToolRestModel tool = ValidateEvent(player, itemStack);

        if (tool == null) return;
        event.setCancelled(true);

        tool.OnPlayerInteractEvent(player, itemStack, event.getAction(), event.getClickedBlock());

    }

    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();


        if(event.getHand()== EquipmentSlot.OFF_HAND)return;

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ToolRestModel tool = ValidateEvent(player, itemStack);

        if (tool == null) return;

        if (!(event.getRightClicked() instanceof LivingEntity)) return;
        event.setCancelled(true);
        tool.PlayerInteractAtEntityEvent(player, itemStack, event.getRightClicked());
    }


    private ToolRestModel GetToolById(String id) {

        for (ToolRestModel tool : toolsList) {
            if (tool.getIdFromCommdnRest().equalsIgnoreCase(id)) {
                return tool;
            }
        }
        return null;
    }


}
