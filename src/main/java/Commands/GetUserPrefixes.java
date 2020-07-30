package Commands;

import Functions.ConfigFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;
import prefixmanager.prefixmanager.PrefixManager;

import java.util.ArrayList;
import java.util.List;

public class GetUserPrefixes implements CommandExecutor {

    String Prefix = ChatColor.RED + "PrefixGUI:" + ChatColor.GRAY + " ";






    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigFunctions configFunctions = new ConfigFunctions();
        //Command Usage: /prefixList
        if(sender instanceof Player) {
            Player playersender = (Player) sender;
            if(args.length == 1) {
                String playerName = args[0];
                Player playerGET = Bukkit.getPlayer(playerName);
                if(playerGET.isOnline()) {
                    if(playersender.hasPermission("prefix.admin")) {
                        ArrayList<String> userPrefixes = configFunctions.getUserPrefixes(playerGET);
                        playersender.sendMessage(Prefix + "The player " + playerGET.getName() + " has the following prefixes: " + userPrefixes.toString());
                    }

                    if(!playersender.hasPermission("prefix.admin")) {
                        playersender.sendMessage(Prefix + "You do not have access to this command!");
                    }

                }

                else {
                    playersender.sendMessage(Prefix + "Player must be offline, check their individual permissions in the permissions folder");
                }

            }

            else {
                playersender.sendMessage(Prefix + "Incorrect Usage: /prefixuser (player)");
            }

        }
        return false;
    }
}
