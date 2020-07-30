package Commands;

import GUI.PrefixGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import prefixmanager.prefixmanager.PrefixManager;

public class openGUI implements CommandExecutor {
    String Title = JavaPlugin.getPlugin(PrefixManager.class).getConfig().getString("Title");
    PrefixGUI prefixGUI = new PrefixGUI(Title);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        prefixGUI.openGUI(player);
        return false;
    }
}
