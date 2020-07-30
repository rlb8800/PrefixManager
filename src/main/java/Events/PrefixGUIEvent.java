package Events;


import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import prefixmanager.prefixmanager.PrefixManager;

import java.util.Objects;

public class PrefixGUIEvent implements Listener {

    public String convertfromConfig() {
        String title = JavaPlugin.getPlugin(PrefixManager.class).getConfig().getString("Title");
        assert title != null;
        String newTitle = title.replace("&", "§");
        return newTitle;
    }

    public String covertFromGUI(String name) {
        String newName = name.replace("§", "&");
        return newName;
    }

    public void changePrefix(Player player, ItemStack itemStack) {
        //Get the item clicked and change the users prefix to the item title
        //ItemStack{GOLD_INGOT x 1, UNSPECIFIC_META:{meta-type=UNSPECIFIC, display-name=§8[§4Owner§8]§f, lore=[§aPurchased]}}
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        String title = itemMeta.getDisplayName();
        String name = covertFromGUI(title);
        String playername = player.getName();

        if(player.hasPermission("prefix." + name)) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playername + " prefix " + name);
            player.sendMessage(ChatColor.RED + "You have now changed your prefix to " + ChatColor.translateAlternateColorCodes('&', name));
        }

        else {
            player.sendMessage(ChatColor.RED + "Sorry you don't have access to this, contact staff.");
        }

    }

    public boolean canBuy(Player player, double cost) {
        boolean buyable = false;
        Economy economy = PrefixManager.getEconomy();
        double playerbal = economy.getBalance(player);
        if(playerbal >= cost) {
            //Player can buy
            economy.withdrawPlayer(player, cost);
            buyable = true;

        }

        if(playerbal < cost) {
            buyable = false;
        }

        return buyable;
    }

    public boolean BuyPrefix(Player player, ItemStack itemStack) {
        boolean buyable = false;
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        String name = itemMeta.getDisplayName();
        String prefix = covertFromGUI(name);

        //§cCost: 10000
        String lore = (Objects.requireNonNull(itemMeta.getLore()).get(0));
        String[] split = lore.split(":");
        double cost = Integer.parseInt(split[1].trim());
        if (cost == 0) {
            buyable = false;
        }
        else {
            String playername = player.getName();

            boolean canBuy = canBuy(player, cost);

            if (canBuy) {


                if (!player.hasPermission("prefix." + prefix)) {
                    //You can now add the prefix
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + playername + " add prefix." + prefix);
                    player.sendMessage(ChatColor.RED + "You have just purchased " + ChatColor.translateAlternateColorCodes('&', prefix));
                    buyable = true;
                }
            }

            if (!canBuy) {
                buyable = false;

            }


        }

        return buyable;
    }


    @EventHandler
    public void onIventoryClick(InventoryClickEvent event) {
        String title = convertfromConfig();

        if(event.getView().getTitle().equals(title)) {
            try {
                if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.GOLD_INGOT)) {
                    Player player = (Player) event.getWhoClicked();
                    changePrefix(player, event.getCurrentItem());
                }

                if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.NAME_TAG)) {
                    Player player = (Player) event.getWhoClicked();
                    boolean buy = BuyPrefix(player, event.getCurrentItem());
                    if (buy) {
                        changePrefix(player, event.getCurrentItem());
                        event.setCancelled(true);
                    }

                    if (!buy) {
                        player.sendMessage(ChatColor.RED + "You do not have enough money to purchase this, or prefix cannot be purchased.");
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
            catch (NullPointerException e) {

            }

        }

    }




}

