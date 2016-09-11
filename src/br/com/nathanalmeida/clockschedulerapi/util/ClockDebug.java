package br.com.nathanalmeida.clockschedulerapi.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Created by Neitan96 on 28/07/2016.
 */
public class ClockDebug{

    public final static ChatColor MSG_COLOR_TAG = ChatColor.DARK_RED;
    public final static ChatColor MSG_COLOR = ChatColor.DARK_GREEN;
    public final static String MSG_TAG = "[ClockDebug] ";

    private static String[] debugTags = new String[0];

    private ClockDebug(){
    }

    public static String[] getDebugTags(){
        return debugTags.clone();
    }

    public static void setTags(String[] tags){
        if(tags == null)
            debugTags = new String[0];
        else
            debugTags = tags;
    }

    public static boolean hasTag(DebugFlags tag){
        return hasTag(tag.flagName);
    }

    public static boolean hasTag(String tag){
        for(String debugTag : debugTags)
            if(debugTag.equalsIgnoreCase(tag))
                return true;
        return false;
    }

    public static void log(DebugFlags tag, String message){
        log(tag.flagName, message);
    }

    public static void log(String tag, String message){
        if(hasTag(tag) || hasTag(DebugFlags.ALL))
            Bukkit.getConsoleSender().sendMessage(
                    MSG_COLOR_TAG + MSG_TAG + MSG_COLOR + message
            );
    }

    public static void log(String message){
        log(DebugFlags.OTHERS, message);
    }

}
