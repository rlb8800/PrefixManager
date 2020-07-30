package Commands;

import Functions.ConfigFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import prefixmanager.prefixmanager.PrefixManager;

import java.util.ArrayList;
import java.util.Arrays;

public class GetAllPrefixes implements CommandExecutor {

    String Prefix = ChatColor.RED + "PrefixGUI:" + ChatColor.GRAY + " ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigFunctions configFunctions = new ConfigFunctions();
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("prefix.admin")) {
                    player.sendMessage(Prefix + "All known prefixes are: " + configFunctions.getAllPrefixes().toString());
                }
                if (!player.hasPermission("prefix.admin")) {
                    player.sendMessage(Prefix + "You do not have access to this command!");
                }

            }

            else {
                player.sendMessage(Prefix + "Incorrect usage: /listprefixes");
            }
        }

        return false;
    }
}
