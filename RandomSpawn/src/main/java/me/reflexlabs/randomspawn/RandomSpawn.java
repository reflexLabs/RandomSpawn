package me.reflexlabs.randomspawn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import me.reflexlabs.randomspawn.listeners.ListenersManager;
import me.reflexlabs.randomspawn.utils.Metrics;

public class RandomSpawn extends JavaPlugin{
	public static RandomSpawn instance;
	public String mainVersion;
	private RandomSpawnManager randomManager;
	private ListenersManager listenersManager;
	
	public void onEnable() {
		instance = this;
		final PluginDescriptionFile pdf = this.getDescription();
        setMainVersion(pdf.getVersion());
        final int pluginId = 12324;
        new Metrics(getInstance(), pluginId);
        setRandomManager(new RandomSpawnManager());
        getRandomManager().onEnable();
        setListenersManager(new ListenersManager());
        Bukkit.getPluginManager().registerEvents(new ListenersManager(),this);
        
	}
	
	public void onDisable() {
        this.randomManager.onDisable();
    }
    
    public static RandomSpawn getInstance() {
        return RandomSpawn.instance;
    }

	public RandomSpawnManager getRandomManager() {
		return randomManager;
	}

	public void setRandomManager(RandomSpawnManager randomManager) {
		this.randomManager = randomManager;
	}

	public ListenersManager getListenersManager() {
		return listenersManager;
	}

	public void setListenersManager(ListenersManager listenersManager) {
		this.listenersManager = listenersManager;
	}
	
	public String getMainVersion() {
		return mainVersion;
	}

	public void setMainVersion(String mainVersion) {
		this.mainVersion = mainVersion;
	}
}
