package me.reflexlabs.randomspawn.commands;

import org.bukkit.command.CommandExecutor;
import me.reflexlabs.randomspawn.RandomSpawn;

public class CommandManager
{
    public CommandManager() {
        this.registerCommands();
    }
    
    public void registerCommands() {
        RandomSpawn.getInstance().getCommand("randomspawn").setExecutor((CommandExecutor)new RandomCommands());
        /*if(RandomSpawn.getInstance().getRandomManager().getDataManager().enableAutoTabComplete) {
        	RandomSpawn.getInstance().getCommand("randomspawn").setTabCompleter(new TabCompleter());
        }*/
    }
}
