package me.reflexlabs.randomspawn.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import me.reflexlabs.randomspawn.RandomSpawn;
import me.reflexlabs.randomspawn.classes.Point;
import me.reflexlabs.randomspawn.utils.Functions;

public class ListenersManager implements Listener{
	@EventHandler(priority = EventPriority.HIGHEST)   
    public void onPlayerRespawn(PlayerRespawnEvent event) {
		if(RandomSpawn.getInstance().getRandomManager().getDataManager().bedPriority) {
			try {
				if(event.getPlayer().getBedSpawnLocation() != null)	{
					event.setRespawnLocation(event.getPlayer().getBedSpawnLocation());
					return;
				}
			} catch(Exception e) { /* No bed */ }
		}

		if(RandomSpawn.getInstance().getRandomManager().getDataManager().getActivationRandomSpawn()) {
			if(RandomSpawn.getInstance().getRandomManager().getDataManager().closestSpawn) {
				try {
					Point point = RandomSpawn.getInstance().getRandomManager().getDataManager().getClosestSpawnPoint(RandomSpawn.getInstance().getRandomManager().getDataManager().getPlayerDeathLoc(event.getPlayer()));
					if(point != null) {
						RandomSpawn.getInstance().getRandomManager().getDataManager().cachePlayer(event.getPlayer(), point);
						event.setRespawnLocation(point.getLocation());
					} else {
						System.out.println("What we didn't want to happened happened...");
					}
				} catch(Exception noCloseSpawn) {}
			} else {
				Point point = RandomSpawn.getInstance().getRandomManager().getDataManager().getRandomSpawnPoint();
				RandomSpawn.getInstance().getRandomManager().getDataManager().cachePlayer(event.getPlayer(), point);
		        event.setRespawnLocation(point.getLocation());
			}
			
		}  
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)   
    public void onPlayerDeath(PlayerDeathEvent event) {
		if(!RandomSpawn.getInstance().getRandomManager().getDataManager().bedPriority) {
			Player player = event.getEntity();
			Location loc = event.getEntity().getLocation();
			RandomSpawn.getInstance().getRandomManager().getDataManager().addDeath(player, loc); 
		}
    }
    		
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) {
            return;
        }
    	String clickedName = null;
	   	try {
	   		clickedName = Functions.formatMessage(e.getCurrentItem().getItemMeta().getDisplayName());
	   	} catch(Throwable noMeta) {}
	   	try {            
	   		if(RandomSpawn.getInstance().getRandomManager().getDataManager().isModiferExists(((Player) e.getWhoClicked()))) {
	   			if (e.getView().getTitle().equals(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix + "&7" + RandomSpawn.getInstance().getRandomManager().getDataManager().getModifedByPlayer(((Player) e.getWhoClicked())).getId()))) {
	            	e.setCancelled(true);
	            	Point p = RandomSpawn.getInstance().getRandomManager().getDataManager().getModifedByPlayer(((Player) e.getWhoClicked()));
	            	Player player = (Player) e.getWhoClicked();
	            	if (clickedName.equals(Functions.formatMessage("&e&lTeleport"))) {
	            	   	RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().teleport(player, p);
	            	} else if (clickedName.equals(Functions.formatMessage("&6&lChange Location"))) {
	            	   	RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().updatePoint(player, p.getId());
	            	   	player.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().pointUpdatedMessage.replace("{point}", Functions.formatMessage(p.getId()))));
	            	} else if (clickedName.equals(Functions.formatMessage("&a&lActivation"))) {
	            		Boolean status;
	            		if(p.getEnabled()) {
	            			status = false;
	            		} else {
	            			status = true;
	            		}
	                	RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().togglePoint(player, p.getId(), status);
	            	} else if (clickedName.equals(Functions.formatMessage("&c&lRemove"))) {
	                	if(RandomSpawn.getInstance().getRandomManager().getEventManager().getPlayerEvents().removePoint(player, p.getId()))
	                    {
	                    	player.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().pointRemoveMessage.replace("{point}", Functions.formatMessage(p.getId()))));
	                    }
	            	}
	            	RandomSpawn.getInstance().getRandomManager().getDataManager().removeModifier(player);
	            	player.closeInventory();
	            	Functions.playSound(player, 1);
	            }
	   		}
         } catch (Exception var3) {
        	 var3.printStackTrace();
         }
	}
}
