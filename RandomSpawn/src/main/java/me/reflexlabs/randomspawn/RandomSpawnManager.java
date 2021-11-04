package me.reflexlabs.randomspawn;

import org.bukkit.Bukkit;
import me.reflexlabs.randomspawn.commands.CommandManager;
import me.reflexlabs.randomspawn.data.DataManager;
import me.reflexlabs.randomspawn.events.EventManager;
import me.reflexlabs.randomspawn.utils.Functions;

public class RandomSpawnManager {

    private DataManager dataManager;
    private CommandManager commandManager;
	private EventManager eventManager;
	
    public void onEnable() {
        (this.dataManager = new DataManager()).loadData();
        if (this.dataManager.enablePlugin) {
            this.setCommandManager(new CommandManager());
            this.setEventManager(new EventManager());
            Bukkit.getLogger().info(Functions.formatMessage("&a[RandomSpawn] plugin has been enabled."));
        }
        else {
            Bukkit.getLogger().severe(Functions.formatMessage("&c[RandomSpawn] plugin has not enabled, you need to enable it in config.yml."));
        }
    }
    
    public void onDisable() {
        System.out.println("[RandomSpawn] plugin has been disabled.");
    }
	
    public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}
}
