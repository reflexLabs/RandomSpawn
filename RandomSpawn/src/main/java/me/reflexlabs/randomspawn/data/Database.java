package me.reflexlabs.randomspawn.data;

import java.io.File;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import me.reflexlabs.randomspawn.RandomSpawn;
import me.reflexlabs.randomspawn.classes.Point;

public class Database {
public YamlConfiguration dataBase;
    
    public Database() {
        this.dataBase = new YamlConfiguration();
        this.createData();
    }
    
    public void createData() {
        if (!this.getFile().exists()) {
            try {
                this.dataBase.createSection("database");
                this.dataBase.options().copyDefaults(true);
                this.dataBase.save(this.getFile());
                System.out.println("[RandomSpawn] database.yml file created successfully!");
            }
            catch (Exception e) {
                Bukkit.getLogger().info("");
                Bukkit.getLogger().severe("[RandomSpawn] database.yml file creation failed!");
                Bukkit.getLogger().severe("[RandomSpawn] Please try to remove database.yml and re-generate it by restart your server.");
                Bukkit.getLogger().info("");
                e.printStackTrace();
            }
        }
    }
    
    public void saveData() {
        if (this.getFile().exists()) {
            try {
                this.dataBase.save(this.getFile());
                System.out.println("[RandomSpawn] database.yml file saved successfully!");
            }
            catch (Exception e) {
                Bukkit.getLogger().info("");
                Bukkit.getLogger().severe("[RandomSpawn] database.yml file save failed!");
                Bukkit.getLogger().info("");
                e.printStackTrace();
            }
        }
    }
    
    public void updatePoint(Point point) {
        for (Point p : RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList()) {
            if (p.getId().equals(point.getId())) {
                p.setEnabled(point.getEnabled());
                p.setLocation(point.getLocation());
                p.setListID(point.getListID());
                this.updatePointData(p);
            }
        }
    }
    
    public void updatePointData(Point point) {
        try {
            this.dataBase = this.getFileConfiguration();
            for (String acc : this.dataBase.getConfigurationSection("database").getKeys(false)) {
                if (acc.equals(point.getId())) {
                    this.dataBase.set("database." + acc + ".enabled", point.getEnabled());
                    this.dataBase.set("database." + acc + ".location", point.getLocation());
                    this.dataBase.options().copyDefaults(true);
                    this.dataBase.save(this.getFile());
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("[RandomSpawn] Something went wrong with saving " + point.getId() + " spawn point.");
            e.printStackTrace();
        }
    }
    
    public void reloadData() {
    	RandomSpawn.getInstance().getRandomManager().getDataManager().clearDatabase();
        this.loadPoints();
    }
    
    public void loadPoints() {
        try {
            this.dataBase = this.getFileConfiguration();
            for (String point : this.dataBase.getConfigurationSection("database").getKeys(false)) {
                boolean enabled = this.dataBase.getBoolean("database." + point + ".enabled");
                Location location = (Location)this.dataBase.get("database." + point + ".location");
                Point p = new Point(enabled, point, location);
                p.setListID(RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList().size());
                this.loadPoint(p);
            }
        }
        catch (Exception notPointsToLoad) {
            notPointsToLoad.printStackTrace();
        }
    }
    
    public Point getPointByID(String id) {
        for (Point p : RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList()) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        this.dataBase = this.getFileConfiguration();
        for (String point : this.dataBase.getConfigurationSection("database").getKeys(false)) {
            if (point.equals(id)) {
                boolean enabled = this.dataBase.getBoolean("database." + point + ".enabled");
                Location location = (Location)this.dataBase.get("database." + point + ".location");
                Point p = new Point(enabled, point, location);
                p.setListID(RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList().size());
                return p;
            }
        }
        return null;
    }
    
    public void loadPoint(Point point) {
        RandomSpawn.getInstance().getRandomManager().getDataManager().addDatabase(point);
    }
    
    public void removePoint(Point point) {
        try {
            this.dataBase = this.getFileConfiguration();
            Set<String> list = this.dataBase.getConfigurationSection("database").getKeys(false);
            for(String s : list) {
            	if(s.equals(point.getId())) {
            		this.dataBase.set("database." + point.getId(), null);
                    this.dataBase.options().copyDefaults(true);
                    this.dataBase.save(this.getFile());
            	}
            }
            /*Iterator<String> iterator = this.dataBase.getConfigurationSection("database").getKeys(false).iterator();
            if (iterator.hasNext()) {
                String p = iterator.next();
                if (p.equals(point.getId())) {
                	this.dataBase.set("database." + p, null);
                    this.dataBase.options().copyDefaults(true);
                    this.dataBase.save(this.getFile());
                }
            }*/
        }
        catch (Exception e) {
            Bukkit.getLogger().info("");
            Bukkit.getLogger().severe("[RandomSpawn] Spawn point " + point.getId() + " file removing failed!");
            Bukkit.getLogger().info("");
            e.printStackTrace();
        }
    }
    
    public void createPoint(Point point) {
        try {
            this.dataBase = this.getFileConfiguration();
            boolean enabled = false;
            for (String p : this.dataBase.getConfigurationSection("database").getKeys(false)) {
                if (p.equals(point.getId())) {
                    enabled = true;
                }
            }
            if (!enabled) {
                this.dataBase.addDefault("database." + point.getId() + ".enabled", true);
                this.dataBase.addDefault("database." + point.getId() + ".location", point.getLocation());
                this.dataBase.options().copyDefaults(true);
                this.dataBase.save(this.getFile());
                this.loadPoint(point);
            }
        }
        catch (Exception e) {
            Bukkit.getLogger().info("");
            Bukkit.getLogger().severe("[RandomSpawn] Spawn point " + point.getId() + " file creation failed!");
            Bukkit.getLogger().info("");
            e.printStackTrace();
        }
    }
    
    public YamlConfiguration getFileConfiguration() {
        YamlConfiguration data = YamlConfiguration.loadConfiguration(this.getFile());
        return data;
    }
    
    public File getFile() {
        File DataFile = new File("plugins/RandomSpawn/database.yml");
        return DataFile;
    }
}
