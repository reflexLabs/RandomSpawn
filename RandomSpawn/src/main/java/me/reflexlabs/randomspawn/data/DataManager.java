package me.reflexlabs.randomspawn.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.reflexlabs.randomspawn.RandomSpawn;
import me.reflexlabs.randomspawn.classes.Point;

public class DataManager {
	private Config config;
    private Database database;
	public String prefix;
    public String version;
    public Boolean enablePlugin;
    public Boolean enableAutoTabComplete;
    public Boolean enableSound;
    
    public Boolean bedPriority;
    public Boolean closestSpawn;
    
    public String uiSound;
    public String spawnSound;
    public String toggledMessage;
    public String formatStatusOn;
    public String formatStatusOff;
    public String unknownCommand;
    public String notAvailable;
    public String isOffline;
    public String activationMessage;
    public String noPermission;
    public String reloadMessage;
    public String saveMessage;
    public String pointCreatedMessage;
    public String pointUpdatedMessage;
    public String pointSetMessage;
    public String pointRemoveMessage;
    public String noPointsAvailable;
    public String pointAlreadyExists;
    public String pointIsntExists;
    
    public String fillMaterial;
    public Boolean fillInventories;
    
    private List<Point> pointsList;
    private HashMap<Player, Location> deathLocList = new HashMap<Player, Location>();
    private HashMap<Player, Point> modifyList = new HashMap<Player, Point>();
    private HashMap<Player, Point> cacheList = new HashMap<Player, Point>();
    private Point lastCache;

    private Boolean activationRandomSpawn = true;
    
    public DataManager() {
        this.setPointsList(new ArrayList<Point>());
        this.setConfig(new Config());
        this.setDatabase(new Database());
    }
    
    public void loadData() {
        this.config.loadConfig();
        this.database.loadPoints();
    }
    
    public void saveData() {
        this.config.saveData();
        this.database.saveData();
    }
    
    public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Database getDatabase() {
		return database;
	}

	public List<Point> getPointsList() {
		return pointsList;
	}

	public void setPointsList(List<Point> pointsList) {
		this.pointsList = pointsList;
	}
	
	public void addDatabase(final Point point) {
        this.pointsList.add(point);
    }
    
    public void clearDatabase() {
        this.pointsList.clear();
    }
    
    public void removeDatabase(final Point point) {
        this.pointsList.remove(point);
    }
    
    public void setDatabase(final Database database) {
        this.database = database;
    }
    
    public void createPoint(final Point point) {
        this.database.createPoint(point);
    }
    
    public void removePoint(final Point point) {
        this.database.removePoint(point);
    }
    
    public void updatePoint(final Point point) {
        this.database.updatePoint(point);
    }
    
    public Point getPointById(final String id) {
        for (final Point point : this.getPointsList()) {
            if (point.getId().equalsIgnoreCase(id)) {
                return point;
            }
        }
        return null;
    }

    public Point getRandomSpawnPoint() {
    	Random rand = new Random();
    	Point gen = getPointsList().get(rand.nextInt(getPointsList().size()));
    	if(gen != RandomSpawn.getInstance().getRandomManager().getDataManager().getLastCache() && RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList().size() > 1) {
    		return gen;
    	} else {
    		return getPointsList().get(rand.nextInt(getPointsList().size()));
    	}
    }
    

    public Point getClosestSpawnPoint(Location location) {
    	Point point = null;
    	double lastDistance = Double.MAX_VALUE;
    	for(Point p : getPointsList()) {
    		double distance = location.distance(p.getLocation());
    		if(distance < lastDistance) {
    			lastDistance = distance;
    			point = p;
    		}
    	}
    	/*for(Entry<Player,Location> loc : getDeathLocList().entrySet()){
    		if(location == null)
    			location = loc.getValue();
    	    else if(location.distance(loc)
    	    	location = loc;
    	    
    	    
    	    
    	    double distance = player.getLocation().distance(p.getLocation());
    	    if(distance < lastDistance) {
    	        lastDistance = distance;
    	        result = p;
    	    }
        }*/
    	return point;
    }

    public void cachePlayer(Player player, Point point) {
    	addCache(player, point);
		updateLastCache(point);
    }
    
	public Boolean getActivationRandomSpawn() {
		return activationRandomSpawn;
	}

	public void setActivationRandomSpawn(Boolean activationRandomSpawn) {
		this.activationRandomSpawn = activationRandomSpawn;
	}

	public void addModifier(Player player, Point point) {
		this.modifyList.put(player, point);
	}
	
	public void removeModifier(Player player) {
		this.modifyList.remove(player);
	}
	
	public Point getModifedByPlayer(Player player) {
		for (HashMap.Entry<Player, Point> entry : modifyList.entrySet()) {
			return entry.getValue();
		}
		return null;
	}
	
	public Boolean isModiferExists(Player player) {
		for (HashMap.Entry<Player, Point> entry : modifyList.entrySet()) {
			if(entry.getKey().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	public HashMap<Player, Point> getModifyList() {
		return modifyList;
	}

	public void setCacheList(HashMap<Player, Point> cacheList) {
		this.cacheList = cacheList;
	}
	
	
	public void addCache(Player player, Point point) {
		this.cacheList.put(player, point);
	}
	
	public void removeCache(Player player) {
		this.cacheList.remove(player);
	}
	
	public Point getCacheByPlayer(Player player) {
		for (HashMap.Entry<Player, Point> entry : cacheList.entrySet()) {
			return entry.getValue();
		}
		return null;
	}
	
	public Boolean isCacheExists(Player player) {
		for (HashMap.Entry<Player, Point> entry : cacheList.entrySet()) {
			if(entry.getKey().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	public Point getLastCache() {
		return this.lastCache;
	}
	
	public void updateLastCache(Point point) {
		this.lastCache = point;
	}
	
	public HashMap<Player, Point> getCacheList() {
		return cacheList;
	}

	public HashMap<Player, Location> getDeathLocList() {
		return deathLocList;
	}
    public Location getPlayerDeathLoc(Player player) {
    	Location loc = null;
    	for(Entry<Player,Location> l : getDeathLocList().entrySet()){
    		if(l.getKey().getPlayer().getUniqueId().equals(player.getUniqueId())) {
    			return l.getValue();
    		}
    	}
    	return loc;
    }
    
	public void setDeathLocList(HashMap<Player, Location> deathLocList) {
		this.deathLocList = deathLocList;
	}
	
	public void addDeath(Player player, Location location) {
		this.deathLocList.put(player, location);
	}
	
	public void removeDeath(Player player, Location location) {
		this.deathLocList.put(player, location);
	}

}