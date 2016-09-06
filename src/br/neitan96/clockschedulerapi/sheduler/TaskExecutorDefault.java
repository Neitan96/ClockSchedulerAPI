package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockDebug;
import br.neitan96.clockschedulerapi.util.DebugFlags;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public class TaskExecutorDefault implements TaskExecutor, Runnable{

    public final TaskManager manager;

    protected BukkitTask bukkitTask = null;
    protected ClockTask clockTask = null;

    public TaskExecutorDefault(TaskManager manager){
        this.manager = manager;
    }

    @Override
    public ClockTask getCurrentTask(){
        return clockTask;
    }

    @Override
    public void stop(){
        if(bukkitTask != null) bukkitTask.cancel();
        bukkitTask = null;
        clockTask = null;
    }

    @Override
    public synchronized void executeNext(ClockTask task){
        stop();
        clockTask = task;
        ClockCalendar clockCalendar = new ClockCalendar();
        clockCalendar.setMilisecond(0);
        long interval = (task.getNextExecution() - clockCalendar.getTimeInMillis() + 1) / 1000 * 20;
        this.bukkitTask = Bukkit.getScheduler().runTaskLater(
                ClockSchedulerAPI.getInstance(), this, interval
        );
    }

    public boolean running(){
        return bukkitTask != null && clockTask != null;
    }

    @Override
    public void run(){
        try{
            clockTask.run();
        }catch(Exception e){
            e.printStackTrace();
            ClockDebug.log(DebugFlags.TASK_ERROR_EXECUTE, "Erro ao executar a task: " + clockTask);
        }
        manager.updateItem(clockTask);
        stop();
        manager.start();
    }

}
