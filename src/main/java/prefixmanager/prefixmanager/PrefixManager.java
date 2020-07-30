package prefixmanager.prefixmanager;

import Commands.GetAllPrefixes;
import Commands.GetUserPrefixes;
import Commands.openGUI;
import Events.PrefixGUIEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public final class PrefixManager extends JavaPlugin {

    private FileConfiguration config = this.getConfig();
    private static Economy econ = null;



    @Override
    public void onEnable() {

        if (!setupEconomy() ) {
            System.out.println("No Economy plugin detected");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        // Plugin startup logic
        System.out.println("PrefixManager has been enabled");

        //Config Stuff
        List<String> prefixes = new ArrayList<>();
        prefixes.add("&8[&4Owner&8]&f:10000");
        //For Permission nodes List (All avaibale prefixes)
        config.addDefault("Title", "&cGuiTitle");
        config.addDefault("Prefixes", prefixes);
        config.options().copyDefaults(true);
        saveConfig();

        //Events

        this.getServer().getPluginManager().registerEvents(new PrefixGUIEvent(), this);


        //Commands
        this.getCommand("prefixuser").setExecutor(new GetUserPrefixes());
        this.getCommand("listprefixes").setExecutor(new GetAllPrefixes());
        this.getCommand("prefix").setExecutor(new openGUI());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }


}
