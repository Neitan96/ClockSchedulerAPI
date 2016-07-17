package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public class AlarmExecutorDefault implements AlarmExecutor, Runnable{

    public final Runnable onExecuted;

    protected BukkitTask bukkitTask = null;
    protected AlarmTask alarmTask = null;

    public AlarmExecutorDefault(Runnable onExecuted){
        this.onExecuted = onExecuted;
    }

    @Override
    public void stop(){
        if(bukkitTask != null) bukkitTask.cancel();
        bukkitTask = null;
        alarmTask = null;
    }

    @Override
    public void executeNext(AlarmTask task){
        stop();
        long next = task.alarm.nextAfter(task.getLastExecution() + 1);
        long interval = next - ClockCalendar.getClockMilisecond();

        this.bukkitTask = Bukkit.getScheduler().runTaskLater(
                ClockSchedulerAPI.getInstance(), this, (interval / 1000) * 20
        );
    }

    @Override
    public void run(){
        try{
            alarmTask.run();
            onExecuted.run();
        }catch(Exception e){
            e.printStackTrace();
        }
        stop();
    }

}
