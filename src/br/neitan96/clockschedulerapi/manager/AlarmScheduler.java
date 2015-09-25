package br.neitan96.clockschedulerapi.manager;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Neitan96 on 09/09/15.
 */
public class AlarmScheduler {

    protected Runnable runnable;
    protected ClockAlarm alarm;
    protected JavaPlugin plugin;

    public AlarmScheduler(ClockAlarm alarm, Runnable runnable, JavaPlugin plugin) {
        this.runnable = runnable;
        this.alarm = alarm;
        this.plugin = plugin;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public ClockAlarm getAlarm() {
        return alarm;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
