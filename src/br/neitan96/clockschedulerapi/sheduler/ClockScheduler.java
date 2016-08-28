package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public class ClockScheduler implements Comparable<ClockScheduler>{

    public final JavaPlugin plugin;
    public final Runnable runnable;
    public final ClockAlarm alarm;
    public final TaskPriority priority;

    protected ClockScheduler(JavaPlugin plugin, Runnable runnable, ClockAlarm alarm){
        this(plugin, runnable, alarm, TaskPriority.NORMAL);
    }

    protected ClockScheduler(JavaPlugin plugin, Runnable runnable, ClockAlarm alarm, TaskPriority priority){
        this.plugin = plugin;
        this.runnable = runnable;
        this.alarm = alarm;
        this.priority = priority;
    }

    public int compareTo(ClockScheduler o){
        return alarm.compareTo(o.alarm);
    }

}
