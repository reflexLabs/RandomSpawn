package me.reflexlabs.randomspawn.events;

import me.reflexlabs.randomspawn.RandomSpawn;
import me.reflexlabs.randomspawn.classes.Point;
import me.reflexlabs.randomspawn.inventory.InventoryFactory;
import me.reflexlabs.randomspawn.utils.Functions;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerEvents
{
	public void openGUI(Player player, Point point) {
		RandomSpawn.getInstance().getRandomManager().getDataManager().addModifier(player, point);
		new InventoryFactory(player, point);
	}

	public void disableAffect(CommandSender sender) {
		if(sender.hasPermission("randomspawn.disable") || sender.isOp()) {
			String statusMessage = "";
			if(RandomSpawn.getInstance().getRandomManager().getDataManager().getActivationRandomSpawn()) {
				RandomSpawn.getInstance().getRandomManager().getDataManager().setActivationRandomSpawn(false);
				statusMessage = RandomSpawn.getInstance().getRandomManager().getDataManager().formatStatusOff;
			} else {
				RandomSpawn.getInstance().getRandomManager().getDataManager().setActivationRandomSpawn(true);
				statusMessage = RandomSpawn.getInstance().getRandomManager().getDataManager().formatStatusOn;
			}
			sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().activationMessage.replace("{status}", statusMessage)));
		} else {
			sender.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().noPermission));
		}
	}
	
    public Boolean isPointExists(final String id) {
        for (final Point p : RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList()) {
            if (p.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    public Boolean createPoint(final Player player, final String id) {
        boolean status = false;
        try {
            final Point p = new Point(true, id, player.getLocation());
            p.setListID(RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList().size());
            RandomSpawn.getInstance().getRandomManager().getDataManager().createPoint(p);
            status = true;
        }
        catch (Exception creationFailed) {
            return status;
        }
        return status;
    }
    
    public Boolean removePoint(final Player player, final String id) {
        boolean status = false;
        try {
            RandomSpawn.getInstance().getRandomManager().getDataManager().removePoint(RandomSpawn.getInstance().getRandomManager().getDataManager().getPointById(id));
            status = true;
        }
        catch (Exception removeFailed) {
            return status;
        }
        return status;
    }
    
    public Boolean updatePoint(final Player player, final String id) {
        try {
            if (this.isPointExists(id)) {
                final Point point = RandomSpawn.getInstance().getRandomManager().getDataManager().getDatabase().getPointByID(id);
                point.setLocation(player.getLocation());
                RandomSpawn.getInstance().getRandomManager().getDataManager().updatePoint(point);
                return true;
            }
            player.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().pointIsntExists));
            return false;
        }
        catch (Exception creationFailed) {
            creationFailed.printStackTrace();
            return false;
        }
    }
    
    public Boolean togglePoint(final Player player, final String id, boolean status) {
        try {
            if (this.isPointExists(id)) {
                final Point point = RandomSpawn.getInstance().getRandomManager().getDataManager().getDatabase().getPointByID(id);
                point.setEnabled(status);
                RandomSpawn.getInstance().getRandomManager().getDataManager().updatePoint(point);
                String statusMessage = "";
    			if(!status) {
    				statusMessage = RandomSpawn.getInstance().getRandomManager().getDataManager().formatStatusOff;
    			} else {
    				statusMessage = RandomSpawn.getInstance().getRandomManager().getDataManager().formatStatusOn;
    			}
    			player.sendMessage(Functions.formatMessage(RandomSpawn.getInstance().getRandomManager().getDataManager().activationMessage.replace("{status}", statusMessage)));
    			
                return true;
            }
            player.sendMessage(Functions.formatMessage(String.valueOf(RandomSpawn.getInstance().getRandomManager().getDataManager().prefix) + RandomSpawn.getInstance().getRandomManager().getDataManager().pointIsntExists));
            return false;
        }
        catch (Exception creationFailed) {
            creationFailed.printStackTrace();
            return false;
        }
    }
    
    public void teleport(Player player, Point point) {
    	if (player.hasPermission("randomspawn.teleport") || player.hasPermission("randomspawn.*") || player.isOp()) {
    		player.teleport(point.getLocation());
    		Functions.playSound(player, 2);
    	}
    }

    public void sendList(CommandSender sender) {
    	if (sender.hasPermission("randomspawn.list") || sender.hasPermission("randomspawn.*") || sender.isOp()) {
    		if (sender instanceof Player) {
	          	for(Point p : RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList()) {
	    			TextComponent thread = new TextComponent(Functions.formatMessage("&2&l» &7" + p.getId()));
	    			thread.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/randomspawn teleport " + p.getId()));
			        thread.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(Functions.formatMessage("Click here to teleport!"))).create()));
			        ((Player) sender).spigot().sendMessage(thread);
	          	}
    		} else {
	    	   	for(Point p : RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList()) {
	    			sender.sendMessage(Functions.formatMessage("&2&l» &7" + p.getId()));
	    		}
	       }
    	}
    }
    
    public void sendHelp(CommandSender sender) {
        if (sender.hasPermission("randomspawn.help") || sender.hasPermission("randomspawn.*") || sender.isOp()) {
           sender.sendMessage(Functions.formatMessage("&2&l[ RandomSpawn ] &7_____________________________ "));
           sender.sendMessage(Functions.formatMessage("&2&l» /RandomSpawn /RS: &fMain Command"));
           sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7Edit [point]: &fOpens GUI with spawn point modification"));
           sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7Help: &fShows you this menu"));
           sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7Info: &fShows you plugin's info"));
           if(sender.hasPermission("randomspawn.*") || sender.isOp()) {
        	   sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7SetSpawn [point]: &fCreate a new spawn point"));
        	   sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7Remove [point]: &fRemove a exists spawn point"));
        	   sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7Teleport [point]: &fTeleports to specified spawn point"));
        	   sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7List: &fShows you spawn points list"));
        	   sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7Reload: &fReloads plugin's data files"));
        	   sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7Save: &fSaves plugin's data files"));
        	   sender.sendMessage(Functions.formatMessage("&f» &2/RandomSpawn &7Disable: &fDisables plugin's affect"));
           }
        }
     }

     public void sendInfo(CommandSender sender) {
       sender.sendMessage(Functions.formatMessage("&2&l[ RandomSpawn ] &7_ "));
       sender.sendMessage(Functions.formatMessage("&f&l» &2Author: &f&lreflexLabs"));
       sender.sendMessage(Functions.formatMessage("&f&l» &2Version: &7&lv" + RandomSpawn.getInstance().getMainVersion()));
       if (sender instanceof Player) {
          TextComponent thread = new TextComponent(Functions.formatMessage("&7&nVisit the official resource thread."));
          thread.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://www.spigotmc.org/resources/95019/"));
          thread.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(Functions.formatMessage("Click here to visit the thread"))).create()));
          ((Player) sender).spigot().sendMessage(thread);
       } else {
          sender.sendMessage(Functions.formatMessage("&f» &f&nhttps://www.spigotmc.org/resources/80718/"));
       }
     }

}
