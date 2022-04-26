package me.reflexlabs.randomspawn.data;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import me.reflexlabs.randomspawn.RandomSpawn;
import me.reflexlabs.randomspawn.utils.Functions;

public class Config {
	public FileConfiguration config;
    public File configPath;
    public int versionType;
    
    public Config() {
        this.config = RandomSpawn.getInstance().getConfig();
        this.configPath = new File("plugins/RandomSpawn/config.yml");
        this.versionType = 0;
        if (!this.configPath.exists()) {
            System.out.println("[RandomSpawn] No configuration file detected, creating new file..");
            try {
                this.config.addDefault("version", ("v" + RandomSpawn.getInstance().mainVersion));
                this.config.addDefault("settings.enablePlugin", true);
                this.config.addDefault("settings.enableAutoTabComplete", true);
                this.config.addDefault("settings.prefix", "&2&lRandomSpawn »");
                this.config.addDefault("settings.enableSound", true);
                this.config.addDefault("settings.bedPriority", false);
                this.config.addDefault("settings.closestSpawn", false);
                this.config.addDefault("settings.spawnSound", "ENTITY_PLAYER_LEVELUP");
                this.config.addDefault("settings.uiSound", "UI_BUTTON_CLICK");
                this.config.addDefault("messages.toggledMessage", "{status}");
                this.config.addDefault("messages.formatStatusOn", "&a&lACTIVE");
                this.config.addDefault("messages.formatStatusOff", "&c&lINACTIVE");
                this.config.addDefault("messages.activationMessage", "&a&lSUCCESS!&f You toggled the status to {status} &f!");
                this.config.addDefault("messages.unknownCommand", "&cUnknown command, use &7/RandomSpawn help");
                this.config.addDefault("messages.notAvailable", "&c&lSORRY!&c This option aren't available currently.");
                this.config.addDefault("messages.isOffline", "&c&lSORRY!&c This player isn't online currently.");
                this.config.addDefault("messages.noPermission", "&c&lSORRY!&c You do not have permission to do that.");
                this.config.addDefault("messages.reloadMessage", "&a&lSUCCESS! &aAll data have been reloaded!");
                this.config.addDefault("messages.saveMessage", "&a&lSUCCESS! &aAll data files have been saved successfully!");
                this.config.addDefault("messages.pointCreatedMessage", "&a&lSUCCESS! &fYou created a new spawn point &7&l{point} &f!");
                this.config.addDefault("messages.pointUpdatedMessage", "&6&lSUCCESS! &fYou updated the spawn point &7&l{point} &f!");
                this.config.addDefault("messages.pointSetMessage", "&6&lSUCCESS! &fYou set the location of spawn point &7&l{point}");
                this.config.addDefault("messages.pointRemoveMessage", "&c&lSUCCESS! &fYou removed the spawn point &7&l{point}");
                this.config.addDefault("messages.pointAlreadyExists", "&c&lSORRY!&c This spawn point already exists.");
                this.config.addDefault("messages.pointIsntExists", "&c&lSORRY!&c This spawn point isn't exists.");
                this.config.addDefault("messages.noPointsAvailable", "&c&lSORRY!&c No spawn points available.");
                this.config.options().copyDefaults(true);
                RandomSpawn.getInstance().saveConfig();
                Bukkit.getLogger().fine(Functions.formatMessage("&a[RandomSpawn] Configuration file created successfully!"));
            }
            catch (Exception e) {
                Bukkit.getLogger().info("");
                Bukkit.getLogger().severe(Functions.formatMessage("&c[RandomSpawn] Configuration file creation failed!"));
                Bukkit.getLogger().severe(Functions.formatMessage("&c[RandomSpawn] Please try to remove config.yml and re-generate it by restart your server."));
                Bukkit.getLogger().info("");
                e.printStackTrace();
            }
        }
        updateConfig();
        
    }
    
    public void updateConfig() {
    	//Bukkit.getLogger().fine(Functions.formatMessage("&a[RandomSpawn] Updating configuration file..."));
    	Boolean changed = false;
    	config = RandomSpawn.getInstance().getConfig();
    	if(config != null) {
    		try {
    			if(!config.contains("settings.bedPriority")) {
    				config.addDefault("settings.bedPriority", false);
    				changed = true;
                }
    		} catch(Exception processFailed) {
                Bukkit.getLogger().severe(Functions.formatMessage("&c[RandomSpawn] System failed to create new config value `settings.bedPriority`, add it manually or re-generate it by removing the existing config.yml."));
    		}
    		try {
    			if(!config.contains("settings.closestSpawn")) {
                	config.addDefault("settings.closestSpawn", false);
                	changed = true;
                }
    		} catch(Exception processFailed) {
                Bukkit.getLogger().severe(Functions.formatMessage("&c[RandomSpawn] System failed to create new config value `settings.bedPriority`, add it manually or re-generate it by removing the existing config.yml."));
    		}
    	}
    	if(changed) {
    		this.config.options().copyDefaults(true);
        	RandomSpawn.getInstance().saveConfig();
        	Bukkit.getLogger().fine(Functions.formatMessage("&a[RandomSpawn] Configuration file updated successfully!"));
    	}
    }
  
    public void loadConfig() {
        try {
            RandomSpawn.getInstance().getRandomManager().getDataManager().enableSound = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getBoolean("settings.enableSound");
            RandomSpawn.getInstance().getRandomManager().getDataManager().version = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("version");
            RandomSpawn.getInstance().getRandomManager().getDataManager().enablePlugin = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getBoolean("settings.enablePlugin");
            RandomSpawn.getInstance().getRandomManager().getDataManager().bedPriority = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getBoolean("settings.bedPriority");
            RandomSpawn.getInstance().getRandomManager().getDataManager().closestSpawn = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getBoolean("settings.closestSpawn");
            RandomSpawn.getInstance().getRandomManager().getDataManager().enableAutoTabComplete = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getBoolean("settings.enableAutoTabComplete");
            RandomSpawn.getInstance().getRandomManager().getDataManager().prefix = String.valueOf(Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("settings.prefix")) + " ";
            RandomSpawn.getInstance().getRandomManager().getDataManager().spawnSound = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("settings.arrivedSound");
            RandomSpawn.getInstance().getRandomManager().getDataManager().uiSound = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("settings.uiSound");
            RandomSpawn.getInstance().getRandomManager().getDataManager().unknownCommand = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.unknownCommand");
            RandomSpawn.getInstance().getRandomManager().getDataManager().isOffline = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.isOffline");
            RandomSpawn.getInstance().getRandomManager().getDataManager().activationMessage = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.activationMessage");
            RandomSpawn.getInstance().getRandomManager().getDataManager().noPermission = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.noPermission");
            RandomSpawn.getInstance().getRandomManager().getDataManager().notAvailable = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.notAvailable");
            RandomSpawn.getInstance().getRandomManager().getDataManager().reloadMessage = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.reloadMessage");
            RandomSpawn.getInstance().getRandomManager().getDataManager().saveMessage = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.saveMessage");
            RandomSpawn.getInstance().getRandomManager().getDataManager().toggledMessage = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.toggledMessage");
            RandomSpawn.getInstance().getRandomManager().getDataManager().formatStatusOn = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.formatStatusOn");
            RandomSpawn.getInstance().getRandomManager().getDataManager().formatStatusOff = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.formatStatusOff");
            RandomSpawn.getInstance().getRandomManager().getDataManager().pointCreatedMessage = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.pointCreatedMessage");
            RandomSpawn.getInstance().getRandomManager().getDataManager().pointUpdatedMessage = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.pointUpdatedMessage");
            RandomSpawn.getInstance().getRandomManager().getDataManager().pointSetMessage = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.pointSetMessage");
            RandomSpawn.getInstance().getRandomManager().getDataManager().pointRemoveMessage = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.pointRemoveMessage");
            RandomSpawn.getInstance().getRandomManager().getDataManager().noPointsAvailable = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.noPointsAvailable");
            RandomSpawn.getInstance().getRandomManager().getDataManager().pointAlreadyExists = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.pointAlreadyExists");
            RandomSpawn.getInstance().getRandomManager().getDataManager().pointIsntExists = Bukkit.getPluginManager().getPlugin("RandomSpawn").getConfig().getString("messages.pointIsntExists");
        }
        catch (Exception e) {
            Bukkit.getLogger().severe(Functions.formatMessage("&c[RandomSpawn] Couldn't get the config.yml file."));
            e.printStackTrace();
        }
    }
    
    public void saveData() {
        if (this.configPath.exists()) {
            try {
                RandomSpawn.getInstance().saveConfig();
                System.out.println("[RandomSpawn] config.yml file saved successfully!");
            }
            catch (Exception e) {
                Bukkit.getLogger().info("");
                Bukkit.getLogger().severe("[RandomSpawn] config.yml file save failed!");
                Bukkit.getLogger().info("");
                e.printStackTrace();
            }
        }
    }
}
