package GUI;

import Functions.ConfigFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import prefixmanager.prefixmanager.PrefixManager;

import java.util.ArrayList;

public class PrefixGUI implements InventoryHolder {
    //This is the GUI that displays a users prefixes :)
    private Inventory GUI;
    private String title;

    public PrefixGUI(String title) {
        this.title = title;
    }


    private int getGUISlots() {
        int needed = 0;
        //Gets the amount of slots needed for the GUI based on how many prefixes are in the List
        ConfigFunctions configFunctions = new ConfigFunctions();
        int count = configFunctions.getTotalAmtofPrefixes();
        //Calculate the slots needed
        if(count <= 9) {
            needed = 9;
        }

        if(count > 9 && count <= 18) {
            needed = 18;
        }

        if(count > 18 && count <= 27) {
            needed = 27;
        }

        if(count > 27 && count <= 36) {
            needed = 36;
        }

        if(count > 36 && count <= 45) {
            needed = 45;
        }

        if(count > 45 && count <= 54) {
            needed = 54;
        }

        if(count > 54) {
            System.out.println("Prefix GUI Error: Maximum of 54 prefixes allowed");
        }

        return needed;

    }




    public Inventory initializeItems(Player player) {
        GUI = Bukkit.createInventory(this, getGUISlots(), ChatColor.translateAlternateColorCodes('&', this.title));
        ConfigFunctions configFunctions = new ConfigFunctions();

        //First add to the GUI the prefixes the user has
        ArrayList<String> userprefixes = configFunctions.getUserPrefixes(player);
        int amtofUSerPrefixes = userprefixes.size();
        int i = 0;
        int count = 0;
        while(i < amtofUSerPrefixes) {

            String[] purchased = userprefixes.get(i).split(":");
            GUI.setItem(i, createGuiItem(Material.GOLD_INGOT, 1, ChatColor.translateAlternateColorCodes('&', purchased[0]), ChatColor.GREEN + "Purchased"));
            i++;
            count++;
        }

        //Then add all the remaining prefixes the user can purchase

        ArrayList<String> available = configFunctions.getPrefixesAvailableforUser(player);
        int leftover = available.size();
        int y = 0;
        while(y < leftover) {
            String[] availablesplit = available.get(y).split(":");
            //OLbg8 can buy [&8[&bLegend&8]&f, &8[&5E&1P&4I&6C&8]&f]
            //Caused by: java.lang.ArrayIndexOutOfBoundsException: 1
            GUI.setItem(count, createGuiItem(Material.NAME_TAG, 1, ChatColor.translateAlternateColorCodes('&', availablesplit[0]), ChatColor.RED + "Cost: " + availablesplit[1]));
            y++;
            count++;

        }

        return GUI;

    }

    public ItemStack createGuiItem(Material material, int amount, String name, String...lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metalore = new ArrayList<String>();

        for(String lorecomments : lore) {

            metalore.add(lorecomments);

        }

        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }




    @Override
    public Inventory getInventory() {
        return GUI;
    }

    public String getTitle() {
        return this.title;
    }

    public void openGUI(Player p) {
        initializeItems(p);
        p.openInventory(GUI);

    }





}
