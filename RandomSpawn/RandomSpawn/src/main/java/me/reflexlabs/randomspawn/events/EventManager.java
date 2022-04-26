package me.reflexlabs.randomspawn.events;

public class EventManager
{
    private PlayerEvents playerEvents;
    
    public EventManager() {
        this.setPlayerEvents(new PlayerEvents());
    }
    
    public PlayerEvents getPlayerEvents() {
        return this.playerEvents;
    }
    
    public void setPlayerEvents(final PlayerEvents playerEvents) {
        this.playerEvents = playerEvents;
    }
}
