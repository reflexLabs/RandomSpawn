package me.reflexlabs.randomspawn.inventory;

import java.util.Arrays;
import org.bukkit.inventory.meta.ItemMeta;
import me.reflexlabs.randomspawn.RandomSpawn;
import me.reflexlabs.randomspawn.classes.Point;
import me.reflexlabs.randomspawn.utils.Functions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryFactory
{
	int slots = 9;
    private Inventory inv = null;
    
    public InventoryFactory(Player player, Point point) {
    	this.create(point);
    	this.openInventory(player);
    }
    
    public void create(Point point) {
    	inv = Bukkit.createInventory((InventoryHolder)null, slots, Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix + "&7" + point.getId()));

	    //ItemStack emptyItem = null;
	    ItemStack teleportItem = null;
	    ItemStack changeItem = null;
	    ItemStack activeItem = null;
	    ItemStack removeItem = null;
	    
	    ItemStack pointItem = null;
	    
	    pointItem = new ItemStack(Material.PAPER);
	    
	    teleportItem = new ItemStack(Material.ENDER_PEARL);
	    changeItem = new ItemStack(Material.COMPASS);
	    /*try {
	    	emptyItem = new ItemStack(Material.STAINED_GLASS_PANE, 5);
	    } catch(Throwable wrongMaterial) {
    		emptyItem = new ItemStack(Material.getMaterial("LIME_STAINED_GLASS_PANE"));
	    }*/
	    try {
	    	activeItem = new ItemStack(Material.DOUBLE_PLANT);  
	    } catch(Throwable wrongMaterial) {
	    	activeItem = new ItemStack(Material.getMaterial("SUNFLOWER"));
	    }
	    try {
	    	removeItem = new ItemStack(Material.BARRIER);
	    } catch(Throwable wrongMaterial) {
	    	activeItem = new ItemStack(Material.getMaterial("BARRIER"));
	    }
	    
	    ItemMeta teleportMeta = teleportItem.getItemMeta();
	    ItemMeta changeMeta = changeItem.getItemMeta();
	    ItemMeta activeMeta = activeItem.getItemMeta();
	    ItemMeta removeMeta = removeItem.getItemMeta();
	    //ItemMeta emptyMeta = emptyItem.getItemMeta();
	    ItemMeta pointMeta = pointItem.getItemMeta();
	    
	    //emptyMeta.setDisplayName(Functions.formatMessage("&2 "));
	    
	    pointMeta.setDisplayName(Functions.formatMessage("&7&l" + point.getId()));
	    pointItem.setItemMeta(pointMeta);
	    
	    teleportMeta.setDisplayName(Functions.formatMessage("&e&lTeleport"));
	    teleportMeta.setLore(Functions.formatMessage(Arrays.asList("&7Teleport yourself","&7to the spawn point")));
	    teleportItem.setItemMeta(teleportMeta);
	    
	    changeMeta.setDisplayName(Functions.formatMessage("&6&lChange Location"));
	    changeMeta.setLore(Functions.formatMessage(Arrays.asList("&7Change spawn point location","&7to your current location.")));
	    changeItem.setItemMeta(changeMeta);
	    
	    activeMeta.setDisplayName(Functions.formatMessage("&a&lActivation"));
	    String status;
	    if(point.getEnabled()) {
	    	status = RandomSpawn.getInstance().getRandomManager().getDataManager().formatStatusOn;
	    } else {
	    	status = RandomSpawn.getInstance().getRandomManager().getDataManager().formatStatusOff;
	    }
	    activeMeta.setLore(Functions.formatMessage(Arrays.asList("{status}".replace("{status}", status))));
	    activeItem.setItemMeta(activeMeta);
	    
	    removeMeta.setDisplayName(Functions.formatMessage("&c&lRemove"));
	    removeMeta.setLore(Functions.formatMessage(Arrays.asList("&7Remove this spawn","&7point permanently.")));
	    removeItem.setItemMeta(removeMeta);
	    
	    /*this.inv.setItem(0, emptyItem);
	    this.inv.setItem(1, emptyItem);*/
	    this.inv.setItem(2, teleportItem);
	    this.inv.setItem(3, changeItem);
	    this.inv.setItem(4, pointItem);
	    this.inv.setItem(5, activeItem);
	    this.inv.setItem(6, removeItem);
	    /*this.inv.setItem(7, emptyItem);
	    this.inv.setItem(8, emptyItem);*/
     }
    
    public void openInventory(final Player player) {
        player.openInventory(this.inv);
    }
}
