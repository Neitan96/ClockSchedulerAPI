package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockDebug;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public class TaskExecutorDefault implements TaskExecutor, Runnable{

    public final Runnable onExecuted;

    protected BukkitTask bukkitTask = null;
    protected ClockTask clockTask = null;

    public TaskExecutorDefault(Runnable onExecuted){
        this.onExecuted = onExecuted;
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
        if(task.enabled()){
            clockTask = task;
            double interval = task.getNextExecution() - ClockCalendar.getClockMilisecond();
            this.bukkitTask = Bukkit.getScheduler().runTaskLater(
                    ClockSchedulerAPI.getInstance(), this, (long) Math.ceil(interval / 50D)
            );
        }else{
            onExecuted.run();
        }
    }

    @Override
    public boolean running(){
        return bukkitTask != null && clockTask != null;
    }

    @Override
    public void run(){
        try{
            clockTask.run();
        }catch(Exception e){
            e.printStackTrace();
            ClockDebug.log(ClockDebug.TASK_ERROR_EXECUTE, "Erro ao executar a task: " + clockTask);
        }
        stop();
        onExecuted.run();
    }

    @Override
    protected void finalize() throws Throwable{
        super.finalize();
        stop();
    }

}
