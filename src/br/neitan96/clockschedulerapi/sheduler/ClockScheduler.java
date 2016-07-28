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

    protected ClockScheduler(JavaPlugin plugin, Runnable runnable, ClockAlarm alarm){
        this.plugin = plugin;
        this.runnable = runnable;
        this.alarm = alarm;
    }

    @Override
    public int compareTo(ClockScheduler o){
        return alarm.compareTo(o.alarm);
    }

}
