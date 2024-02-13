package org.slavawins.winslib.cmdpager;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PagenatorBase<T extends IViewPaganatorModel> implements CommandExecutor {


    public String itemName = "Предметы";
    public String chatCmd = "example";
    public int pageSize = 14;
    public boolean isOnlyOp = true;

    public PagenatorBase(String chatCmd) {
        this.chatCmd = chatCmd;
    }


    public void ShowPginated(Player player, T[] list, int page) {

        if (isOnlyOp) {
            if (!player.isOp()) {
                player.sendMessage(ChatColor.DARK_RED + "Не хватает прав");
            }
        }
        if (page < 1) {
            player.sendMessage("Нет такой страницы");
            return;
        }
        String msg = "\n================= " + itemName + " ====================\n";
        msg += ChatColor.GOLD + "Список всех " + itemName;
        msg += ChatColor.GRAY + " (Всего " + list.length + " шт.)";
        int totalPages = (int) Math.ceil((double) list.length / pageSize);
        msg += " " + ChatColor.GREEN + "Страница " + page + " из " + totalPages;
        player.sendMessage(msg);


        if (page > totalPages) {
            player.sendMessage("Нет такой страницы");
            return;
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.length);


        for (int i = startIndex; i < endIndex; i++) {
            Render(player, list[i]);
        }

        Paginate(player, page, totalPages);
    }

    public void ShowPginatedOnServer(CommandSender player, T[] list, int page) {

        if (page < 1) {
            player.sendMessage("Not this page ");
            return;
        }
        String msg = "\n================= " + itemName + " ====================\n";
        msg += ChatColor.GOLD + "List of all" + itemName;
        msg += ChatColor.GRAY + " (All " + list.length + " x)";
        int totalPages = (int) Math.ceil((double) list.length / pageSize);
        msg += " " + ChatColor.GREEN + "Page " + page + " / " + totalPages;
        player.sendMessage(msg);


        if (page > totalPages) {
            player.sendMessage("Нет такой страницы");
            return;
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.length);


        for (int i = startIndex; i < endIndex; i++) {
            RenderForServer(player, list[i]);
        }

    }


    public List<BtnPagenatorModel> btns = new ArrayList<>();


    public void Paginate(Player player, int page, int pageCount) {
        String msg = "";

        TextComponent message = new TextComponent("");
        TextComponent btnTp;

        if (page > 1) {
            btnTp = new TextComponent("<<");
            btnTp.setBold(true);
            btnTp.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            btnTp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + chatCmd + " list " + (page - 1)));
            message.addExtra(btnTp);
        }

        message.addExtra("     " + page + " / " + pageCount + "      ");

        if (page < pageCount) {
            btnTp = new TextComponent(">>");
            btnTp.setBold(true);
            btnTp.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            btnTp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + chatCmd + " list " + (page + 1)));
            message.addExtra(btnTp);
        }

        player.spigot().sendMessage(message);
    }


    public String OnAddTextBeforeTitle(T contract) {


        return "";
    }

    public void Render(Player player, T Shop) {

        String msg = "";

        msg += net.md_5.bungee.api.ChatColor.GRAY + "   #" + Shop.GetIdForChatView()
                + net.md_5.bungee.api.ChatColor.RESET + OnAddTextBeforeTitle(Shop)
                + " " + Shop.GetTitleForChatView() + " ";


        TextComponent message = new TextComponent(msg);


        for (BtnPagenatorModel btn : btns) {

            if (!player.isOp() && btn.isForOp) continue;

            TextComponent btnTp = new TextComponent(" " + btn.label);
            btnTp.setBold(true);
            btnTp.setColor(btn.color);
            btnTp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(btn.description)));
            String cmd = btn.cmd;
            cmd = cmd.replace("@p", player.getName());
            cmd = cmd.replace("@id", Shop.GetIdForChatView());
            cmd = cmd.replace("@title", Shop.GetTitleForChatView());

            btnTp.setClickEvent(new ClickEvent(btn.actionType, cmd));
            message.addExtra(btnTp);
        }

        if (btns.size() > 5) {
            message.addExtra("\n");
        }
        player.spigot().sendMessage(message);
    }

    public void RenderForServer(CommandSender player, T Shop) {

        String msg = "";

        msg += net.md_5.bungee.api.ChatColor.GRAY + "   #" + Shop.GetIdForChatView()
                + net.md_5.bungee.api.ChatColor.RESET + OnAddTextBeforeTitle(Shop)
                + " " + Shop.GetTitleForChatView() + " ";


        TextComponent message = new TextComponent(msg);


        player.sendMessage(msg);
    }

    public T[] GetData(Player player) {
        return null;
    }


    public void onFastCommandProvider(CommandSender sender, String[] args) {

        if (args.length == 0) {
            if (sender instanceof Player) {
                ShowPginated((Player) sender, GetData((Player) sender), 1);
            } else {
                // ShowPginatedOnServer(sender, GetData((Player) sender), 1);
            }
            return;
        }


        int page = 1;

        if (args.length == 1) {
            page = Integer.parseInt(args[0]);
        }
        if (sender instanceof Player) {
            ShowPginated((Player) sender, GetData((Player) sender), page);
        } else {

        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (!command.getName().equalsIgnoreCase(chatCmd)) return false;


        if (args.length == 0) {
            ShowPginated((Player) sender, GetData((Player) sender), 1);
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {

            int page = 1;

            if (args.length == 2) {
                page = Integer.parseInt(args[1]);
            }
            ShowPginated((Player) sender, GetData((Player) sender), page);

            return true;
        }

        return false;
    }
}
