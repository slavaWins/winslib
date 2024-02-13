package org.slavawins.winslib.toolrest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ToolRestModel {

    public String name;

    /**
     * Автоматически вызывает команду
     */
    public String callCommand = "";
    public Material material = Material.STONE_SWORD;
    public ItemStack customItem;
    public String desctiption = "Предмет для редактирования и администрирования";

    public static ToolRestModel New(String name, Material material, String s) {
        ToolRestModel e = new ToolRestModel();
        e.name = name;
        e.material = material;
        e.callCommand = s;
        return e;
    }


    /**
     * ИД или имя для вставки в команды, для инструмент ToolRest
     * @return
     */
    public String getIdFromCommdnRest() {
        return getClass().getSimpleName() ;//+ "_" + name;
    }


    public void OnPlayerInteractEvent(Player player, ItemStack itemStack, Action action, Block clickedBlock) {
        //player.sendMessage(getIdFromCommdnRest() + " INTERACT " + action.name());
    }

    public void PlayerInteractAtEntityEvent(Player player, ItemStack itemStack, Entity entity) {
       // player.sendMessage(getIdFromCommdnRest() + " Entity Click" + entity.getName());
    }
}
