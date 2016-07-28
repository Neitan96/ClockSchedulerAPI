package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public class SchedulerExecutorDefault implements SchedulerExecutor, Runnable{

    public final Runnable onExecuted;

    protected BukkitTask bukkitTask = null;
    protected SchedulerTask schedulerTask = null;

    public SchedulerExecutorDefault(Runnable onExecuted){
        this.onExecuted = onExecuted;
    }

    @Override
    public void stop(){
        if(bukkitTask != null) bukkitTask.cancel();
        bukkitTask = null;
        schedulerTask = null;
    }

    @Override
    public void executeNext(SchedulerTask task, long next){
        stop();
        if(schedulerTask.enabled()){
            long interval = next - ClockCalendar.getClockMilisecond();
            this.bukkitTask = Bukkit.getScheduler().runTaskLater(
                    ClockSchedulerAPI.getInstance(), this, (interval / 1000) * 20
            );
        }else{
            onExecuted.run();
        }
    }

    @Override
    public void run(){
        if(schedulerTask.enabled()){
            try{
                schedulerTask.run();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        onExecuted.run();
        stop();
    }

    @Override
    protected void finalize() throws Throwable{
        super.finalize();
        stop();
    }
}
