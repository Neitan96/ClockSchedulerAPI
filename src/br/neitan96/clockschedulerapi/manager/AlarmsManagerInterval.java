package br.neitan96.clockschedulerapi.manager;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import org.bukkit.Bukkit;

/**
 * Created by Neitan96 on 09/09/15.
 */
public class AlarmsManagerInterval extends AlarmsManagerBasic{

    protected final long interval;


    public AlarmsManagerInterval(long interval){
        this.interval = interval;
    }

    public AlarmsManagerInterval(){
        this(60);
    }


    @Override
    public void startCheck(){
        ClockSchedulerAPI.debug("Iniciando scheduler de verificação em intervalo...", 2);

        if(schedulers.isEmpty()) return;
        if(schedulerBukkitID > 0) stopCheck();

        schedulerBukkitID = Bukkit.getScheduler().runTaskTimer(
                ClockSchedulerAPI.getInstance(), runnable, 20, 20 * interval
        ).getTaskId();
    }

}
