package org.slavawins.winslib;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandCaser {


    public boolean OnCommand(Player player, String name,  String[] args ){
        return  false;

    }

    public static boolean Input(CommandSender sender, Command command,  String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        player.sendMessage("PARSE");
        player.sendMessage(command.getName());
        player.sendMessage(command.getLabel());

        player.sendMessage(command.getDescription());
        player.sendMessage("LEN ARGS:" + args.length);


        return false;
    }
}
