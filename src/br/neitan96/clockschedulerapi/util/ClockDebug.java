package br.neitan96.clockschedulerapi.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Created by Neitan96 on 28/07/2016.
 */
public class ClockDebug{

    public final static String TAG_ALL = "All";
    public final static String TAG_OTHERS = "Others";

    public final static String TASK_ADDED = "Task added";
    public final static String TASK_REMOVED = "Task removed";
    public final static String TASK_ENABLED = "Task enabled";
    public final static String TASK_DISABLED = "Task disabled";
    public final static String TASK_RUNNING = "Task running";

    public final static String MANAGER_STARTING = "Manager starting";
    public final static String MANAGER_STOPPING = "Manager stopping";
    public final static String MANAGER_NEXT_EXECUTION = "Manager next execution";
    public final static String MANAGER_NONE_TASK = "Manager none task";
    public final static String MANAGER_REMOVING_DISABLED = "Manager removing disabled tasks";

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

    public static boolean hasTag(String tag){
        for(String debugTag : debugTags)
            if(debugTag.equalsIgnoreCase(tag))
                return true;
        return false;
    }

    public static void log(String tag, String message){
        if(hasTag(tag) || hasTag(TAG_ALL))
            Bukkit.getConsoleSender().sendMessage(
                    MSG_COLOR_TAG + MSG_TAG + MSG_COLOR + message
            );
    }

    public static void log(String message){
        log(TAG_OTHERS, message);
    }

}
