package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockDebug;
import br.neitan96.clockschedulerapi.util.DebugFlags;
import br.neitan96.clockschedulerapi.util.Util;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public class ClockTask extends ClockScheduler implements Runnable{

    protected boolean enabled = false;
    protected long lastExecution = -1;
    protected long nextExecution = -1;

    public ClockTask(JavaPlugin plugin, Runnable runnable, ClockAlarm alarm){
        super(plugin, runnable, alarm);
        enable();
    }

    protected void calculateNextExecution(){
        long start;
        if(nextExecution > 0){
            setLastExecution(nextExecution);
            start = lastExecution;
        }else{
            start = ClockCalendar.getClockMilisecond();
        }
        setNextExecution(alarm.nextAfter(++start));
    }

    protected void setLastExecution(long lastExecution){
        if(lastExecution > this.lastExecution)
            this.lastExecution = lastExecution;
    }

    protected void setNextExecution(long nextExecution){
        this.nextExecution = nextExecution;
        if(nextExecution < 0) disable();
    }

    public boolean enabled(){
        return enabled;
    }

    public void reset(){
        if(enabled()){
            nextExecution = -1;
            calculateNextExecution();
            ClockDebug.log(DebugFlags.TASK_RESTARTED, "Task reiniciada: " + toString());
        }
    }

    public void enable(){
        if(!enabled()){
            enabled = true;
            calculateNextExecution();
            ClockDebug.log(DebugFlags.TASK_ENABLED, "Task ativada: " + toString());
        }
    }

    public void disable(){
        if(enabled()){
            enabled = false;
            nextExecution = -1;
            ClockDebug.log(DebugFlags.TASK_DISABLED, "Task desativada: " + toString());
        }
    }

    public long getLastExecution(){
        return lastExecution;
    }

    public long getNextExecution(){
        return nextExecution;
    }

    @Override
    public void run(){
        calculateNextExecution();
        ClockDebug.log(DebugFlags.TASK_RUNNING, "Executando task: " + toString());
        runnable.run();
    }

    public int compareTo(ClockTask o){
        return Float.compare(getNextExecution(), o.getNextExecution());
    }

    @Override
    public String toString(){
        return String.format("{%s %s %s}",
                plugin.getName(), alarm,
                nextExecution > 0 ? Util.getIntervalNow(nextExecution) : "off");
    }

    public String toShortString(){
        return String.format("{%s %s}",
                plugin.getName(), alarm);
    }

}
