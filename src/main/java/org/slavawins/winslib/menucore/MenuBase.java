package org.slavawins.winslib.menucore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slavawins.winslib.Winslib;
import org.slavawins.winslib.helper.ECustomGuiBack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuBase implements Listener {


    public static String MENU_META_KEY = "menuBase";
    public Player player;

    public String menuId = "";
    public int rows = 9;
    public String title = "nontitle";
    public List<BtnMenuCoreContract> listBtns = new ArrayList<>();
    public Inventory guiInventory;


    public boolean isLockedAll = true;
    public boolean isChestMode = true;

    public final void setSize(int i) {
        this.rows = i;
    }


    /**
     * Установить бэкграунд для инвентаря. Центрированый контейнер
     * @param back
     * @param text
     */
    public final void setBackground(ECustomGuiBack back,  String text) {
        String val = ChatColor.WHITE + "" + ENegativeSpaceGui.WINDOW + back.toString();
        if(text!=null)val+=text;
        setTitle(val);
    }

    public final void setTitle(String val) {
        this.title = val;

        if (guiInventory == null) return;
        Inventory guiInventoryNew = Bukkit.createInventory(null, rows * 9, title);
        guiInventoryNew.setContents(guiInventory.getContents());
        guiInventory.clear();
        guiInventory = null;
    }

    public final void setTitleUnicode(String val) {
        this.title = ChatColor.WHITE + "" + val;
    }

    public final void setLockedAll(boolean val) {
        this.isLockedAll = val;
    }


    public final int PosToId(int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > rows) {
            return -1;
        }
        int id = 9 * y - 9 + x - 1;
        return id;
    }

    public final int getX(int id) {
        if (id < 0 || id > rows * 9 - 1) {
            return -1;
        }
        int x = ((id) % 9) + 1;
        return x;
    }

    public final int getY(int id) {
        if (id < 0 || id > rows * 9 - 1) {
            return -1;
        }
        int y = (int) Math.ceil(id / 9) + 1;
        return y;
    }

    public final BtnMenuCoreContract AddButton(int x, int y, Material mat, String name, String descr, String action) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(descr);

        meta.setLore(lore);

        meta.setDisplayName(name);
        item.setItemMeta(meta);

        return AddButtonItem(x, y, item, action, true);
    }

    public final BtnMenuCoreContract AddButtonItem(int x, int y, ItemStack item, String action, boolean isLockedBtn) {


        BtnMenuCoreContract btn = new BtnMenuCoreContract();
        btn.x = x;
        btn.y = y;
        btn.item = item;
        btn.action = action;
        btn.id = PosToId(x, y);
        btn.isLocked = isLockedBtn;

        this.listBtns.add(btn);
        return btn;
    }


    public final void Init() {

        if (guiInventory != null) return;

        // Создаем новый инвентарь с заданным размером и заголовком
        guiInventory = Bukkit.createInventory(null, 9 * rows, title);


        //System.out.println("guiInventory init size: " + (9 * rows));

    }


    public void ClearButtons() {
        for (BtnMenuCoreContract btn : listBtns) {
            guiInventory.setItem(btn.id, null);
        }
    }

    public void RenderButtons() {
        for (BtnMenuCoreContract btn : listBtns) {

            if (btn.id > rows * 9 || btn.id < 0) {
                System.out.println("Error item btn possition to " + " - " + btn.action + " in pos " + btn.id + " / x:y = " + btn.x + ":" + btn.y);
                continue;
            }
            guiInventory.setItem(btn.id, btn.item);
        }
    }

    public void Show(Player player) {
        this.player = player;

        Init();


        RenderButtons();


        menuId = UUID.randomUUID().toString();

        //System.out.println("id: " + menuId);

        PMetaHelper.setPlayerMetadata(player, "menuId", menuId);
        PMetaHelper.setPlayerMetadata(player, "menuClass", getClass().getSimpleName());
        PMetaHelper.setPlayerMetadata(player, MENU_META_KEY, "true");


        Winslib.getInstanse().getServer().getPluginManager().registerEvents(this, Winslib.getInstanse());


        // Открываем инвентарь для игрока
        player.openInventory(guiInventory);
    }


    private final void Delete() {

        PMetaHelper.remove(player, MENU_META_KEY);

        //  player.sendMessage("closed");

        //PlayerInteractEvent.getHandlerList().unregister(this);
        HandlerList.unregisterAll(this);

        PMetaHelper.remove(player, "menuId");
        PMetaHelper.remove(player, "menuClass");
        PMetaHelper.remove(player, MENU_META_KEY);

        guiInventory = null;


    }

    public final BtnMenuCoreContract GetBtn(int id) {
        for (BtnMenuCoreContract btn : listBtns) {
            if (btn.id == id) return btn;
        }
        return null;
    }

    public void OnClickButton(BtnMenuCoreContract btn, ClickType clickType, ItemStack currentItemInMouse) {
        //  player.sendMessage("CBTN:" + btn.action);
    }

    public void OnClickEmpty(int id, ClickType clickType, ItemStack currentItemInMouse) {
        //  player.sendMessage("CBTN to empty:" + id);
    }


    @org.bukkit.event.EventHandler
    public final void eventonClick(InventoryClickEvent e) {


        Player player = (Player) e.getWhoClicked();
        if (!player.hasMetadata(MENU_META_KEY)) return;


        if (!PMetaHelper.get(player, "menuId").equalsIgnoreCase(menuId)) {
            //    System.out.println("is not my id: " + PMetaHelper.get(player, "menuId"));
            return;
        }


        if (isLockedAll && !isChestMode) e.setCancelled(true);


        Inventory clickedInventory = e.getClickedInventory();

        if (clickedInventory == null) return;

        if (!clickedInventory.equals(guiInventory)) {
            //  System.out.println("click in other inv");
            return;//клиенул в другом инвентаре
        }


        BtnMenuCoreContract btn = GetBtn(e.getSlot());
        if (btn != null) {
            OnClickButton(btn, e.getClick(), e.getCurrentItem());
            if (btn.isLocked || isLockedAll) e.setCancelled(true);

        } else {
            OnClickEmpty(e.getSlot(), e.getClick(), e.getCurrentItem());
            if (isLockedAll) e.setCancelled(true);
        }

    }


    public void OnCloseEvent() {

    }

    @org.bukkit.event.EventHandler
    public final void onCloseListner(InventoryCloseEvent e) {


        if (!e.getInventory().equals(guiInventory)) return;

        OnCloseEvent();

        Delete();


    }


}
