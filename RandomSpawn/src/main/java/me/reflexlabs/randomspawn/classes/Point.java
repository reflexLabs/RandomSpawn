package me.reflexlabs.randomspawn.classes;

import org.bukkit.Location;

public class Point
{
    private Boolean enabled;
    private String id;
    private Integer listID;
    private Location location;
    
    public Point(final Boolean enabled, final String id, final Location location) {
    	this.setEnabled(enabled);
    	this.setId(id);
    	this.setLocation(location);
    }

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getListID() {
		return listID;
	}

	public void setListID(Integer listID) {
		this.listID = listID;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
