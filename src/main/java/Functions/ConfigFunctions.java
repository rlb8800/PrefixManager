package Functions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import prefixmanager.prefixmanager.PrefixManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ConfigFunctions {

    public ArrayList<String> getAllPrefixes() {
        String[] prefixes = JavaPlugin.getPlugin(PrefixManager.class).getConfig().getStringList("Prefixes").toArray(new String[0]);
        ArrayList<String> AllPrefixes = new ArrayList<>();
        for(int i = 0; i < prefixes.length; i++) {
            String[] prefixsplit = prefixes[i].split(":");
            AllPrefixes.add(prefixsplit[0]);
        }

        return AllPrefixes;
    }


    public ArrayList<String> getAllPrefixesAndValues() {
        String[] prefixes = JavaPlugin.getPlugin(PrefixManager.class).getConfig().getStringList("Prefixes").toArray(new String[0]);
        ArrayList<String> AllPrefixes = new ArrayList<>();
        for(int i = 0; i < prefixes.length; i++) {
            AllPrefixes.add(prefixes[i]);
        }

        return AllPrefixes;
    }

    public ArrayList<String> getAllAmounts() {
        String[] prefixes = JavaPlugin.getPlugin(PrefixManager.class).getConfig().getStringList("Prefixes").toArray(new String[0]);
        ArrayList<String> Allamounts = new ArrayList<>();
        for(int i = 0; i < prefixes.length; i++) {
            String[] prefixsplit = prefixes[i].split(":");
            Allamounts.add(prefixsplit[1]);
        }

        return Allamounts;
    }



    public ArrayList<String> getUserPrefixes(Player player) {
        String[] prefixes = JavaPlugin.getPlugin(PrefixManager.class).getConfig().getStringList("Prefixes").toArray(new String[0]);

        ArrayList<String> UserPrefixes = new ArrayList<>();
        for(int i = 0; i < prefixes.length; i++) {
            String[] prefixsplit = prefixes[i].split(":");
            if(player.hasPermission("prefix." + prefixsplit[0])) {
                UserPrefixes.add(prefixes[i]);
            }
        }
        return UserPrefixes;
    }

    public ArrayList<String> getPrefixesAvailableforUser(Player player) {
        ArrayList<String> allPrefixes = getAllPrefixesAndValues();
        ArrayList<String> availablePrefixes = new ArrayList<>();

        int length = allPrefixes.size();
        int i =0;
        while(i < length) {
            String allprefix = allPrefixes.get(i);
            String[] split = allprefix.split(":");
            String prefix = split[0];
            if(!player.hasPermission("prefix." + prefix)) {
                availablePrefixes.add(allprefix);
            }
            i++;
        }

        return availablePrefixes;
    }

    public int getTotalAmtofPrefixes() {
        String[] prefixes = JavaPlugin.getPlugin(PrefixManager.class).getConfig().getStringList("Prefixes").toArray(new String[0]);
        int count = 0;
        for(int i = 0; i < prefixes.length; i++) {
            count++;

        }
        return count;
    }





}
