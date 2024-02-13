package org.slavawins.winslib.listener;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.slavawins.winslib.event.auth.PlayerAuthOrJoinEvent;

public class AuthCommandListner implements CommandExecutor, Listener {


    @EventHandler
    public void onFrameClick(PlayerJoinEvent event) {

        if (!Bukkit.getPluginManager().isPluginEnabled("nLogin")) {
            System.out.println("---------------NO PLUGIN");
            PlayerAuthOrJoinEvent customEvent = new PlayerAuthOrJoinEvent(event.getPlayer());
            Bukkit.getPluginManager().callEvent(customEvent);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getLabel().equalsIgnoreCase("winslibauthevent")) return false;

        if (!sender.isOp()) {
            System.out.println("TRY NO OP CALL");
            return false;
        }

        if (args.length < 2) return false;

        String action = args[0];
        String login = args[1];

        System.out.println(action + " PLAYER: " + login);

        Player player = Bukkit.getPlayer(login);
        if (player == null) {
            return true;
        }

        if (action.equalsIgnoreCase("login")) {
            PlayerAuthOrJoinEvent customEvent = new PlayerAuthOrJoinEvent(player);
            Bukkit.getPluginManager().callEvent(customEvent);
            return true;
        }

        return false;
    }
}