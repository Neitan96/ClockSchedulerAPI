package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public class AlarmScheduler implements Comparable<AlarmScheduler>{

    public final JavaPlugin plugin;
    public final Runnable runnable;
    public final ClockAlarm alarm;

    public AlarmScheduler(JavaPlugin plugin, Runnable runnable, ClockAlarm alarm){
        this.plugin = plugin;
        this.runnable = runnable;
        this.alarm = alarm;
    }

    @Override
    public int compareTo(AlarmScheduler o){
        return alarm.compareTo(o.alarm);
    }

}
