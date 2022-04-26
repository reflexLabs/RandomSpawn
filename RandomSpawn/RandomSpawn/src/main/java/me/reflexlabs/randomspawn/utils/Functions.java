package me.reflexlabs.randomspawn.utils;

import java.util.Random;

import me.reflexlabs.randomspawn.RandomSpawn;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public class Functions
{
    public static String formatMessage(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', "&f" + msg);
    }
    
    public static List<String> formatMessage(final List<String> msg) {
        final List<String> message = new ArrayList<String>();
        for (final String line : msg) {
            message.add(ChatColor.translateAlternateColorCodes('&', "&f" + line));
        }
        return message;
    }
    
    public static void print(String msg) {
    	RandomSpawn.getInstance().getLogger().info(msg);
    }
    
    public static void playSound(final Player player, final int type) {
        String soundType = null;
        if (RandomSpawn.getInstance().getRandomManager().getDataManager().enableSound) {
            switch (type) {
                case 1: {
                    soundType = RandomSpawn.getInstance().getRandomManager().getDataManager().uiSound;
                    break;
                }
                case 2: {
                    soundType = RandomSpawn.getInstance().getRandomManager().getDataManager().spawnSound;
                    break;
                }
                default: {
                    soundType = RandomSpawn.getInstance().getRandomManager().getDataManager().uiSound;
                    break;
                }
            }
            try {
                player.playSound(player.getLocation(), soundType, 1.0f, 1.0f);
            }
            catch (Exception e) {
                player.playSound(player.getLocation(), Sound.valueOf(soundType), 1.0f, 1.0f);
            }
        }
    }

    public static boolean isNumeric(final String s) {
        try {
            Double.parseDouble(s);
        }
        catch (NumberFormatException nfe) {
            try {
                Integer.parseInt(s);
            }
            catch (NumberFormatException nfee) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNegative(final double d) {
        return Double.compare(d, 0.0) < 0;
    }
    
   
    public static String getRandomNumberString() {
        final Random rnd = new Random();
        final int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
