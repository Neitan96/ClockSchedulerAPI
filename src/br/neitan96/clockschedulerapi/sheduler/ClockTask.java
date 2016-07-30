package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockDebug;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public class ClockTask extends ClockScheduler implements Runnable{

    protected boolean enabled = true;
    protected long lastExecution = 0;
    protected long nextExecution = 0;

    public ClockTask(JavaPlugin plugin, Runnable runnable, ClockAlarm alarm){
        super(plugin, runnable, alarm);
        nextExecution = alarm.next();
    }

    public boolean enabled(){
        return enabled;
    }

    public void enable(){
        enabled = true;
        ClockDebug.log(ClockDebug.TASK_ENABLED, "Task ativada: "+toString());
    }

    public void disable(){
        enabled = false;
        ClockDebug.log(ClockDebug.TASK_DISABLED, "Task desativada: "+toString());
    }

    public long getLastExecution(){
        return lastExecution;
    }

    public long getNextExection(){
        return nextExecution;
    }

    @Override
    public void run(){
        ClockDebug.log(ClockDebug.TASK_RUNNING, "Executando task: "+toString());
        lastExecution = nextExecution;
        nextExecution = alarm.nextAfter(lastExecution+1);
        runnable.run();
    }

    @Override
    public String toString(){
        return String.format("{%s@%s %b %s}",
                plugin.getName(), alarm, enabled, new ClockCalendar(lastExecution).toString(true));
    }

}
