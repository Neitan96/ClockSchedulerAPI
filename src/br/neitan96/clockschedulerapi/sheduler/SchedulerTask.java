package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public class SchedulerTask extends ClockScheduler implements Runnable{

    protected boolean enabled = true;
    protected long lastExecution = ClockCalendar.getClockMilisecond();

    public SchedulerTask(JavaPlugin plugin, Runnable runnable, ClockAlarm alarm){
        super(plugin, runnable, alarm);
    }

    public boolean enabled(){
        return enabled;
    }

    public void enable(){
        enabled = true;
    }

    public void disable(){
        enabled = false;
    }

    public long getLastExecution(){
        return lastExecution;
    }

    @Override
    public void run(){
        lastExecution = ClockCalendar.getClockMilisecond();
        runnable.run();
    }

}
