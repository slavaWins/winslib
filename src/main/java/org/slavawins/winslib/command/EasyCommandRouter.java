package org.slavawins.winslib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EasyCommandRouter implements CommandExecutor {


    private final String rootCommand;


    public EasyCommandRouter(String rootCommand) {
        this.rootCommand = rootCommand;
    }

    public boolean commandPlayer(Player player, String label, String[] args) {
        return false;
    }

    public String CombainArgumentsFrom(String[] args, int from) {
        String text = "";
        for (int i = from; i < args.length; i++) text += args[i] + " ";
        text = text.trim();
        return text;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getName().equalsIgnoreCase(rootCommand)) return false;

        if (args.length == 0) return false;

        String[] arguments = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) arguments[i - 1] = args[i];

        if ((sender instanceof Player)) {
            return commandPlayer((Player) sender, args[0], arguments);
        }

        return false;
    }
}
