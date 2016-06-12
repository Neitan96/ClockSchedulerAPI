package br.neitan96.clockschedulerapi.manager;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.Bukkit;

/**
 * Created by Neitan96 on 11/06/2016.
 */
public class AlarmsManagerReal extends AlarmsManagerBasic{

    @Override
    public void startCheck(){
        ClockSchedulerAPI.debug("Iniciando scheduler de verificação em tempo real...", 2);

        if(schedulers.isEmpty()) return;
        if(schedulerBukkitID > 0) stopCheck();

        ClockCalendar calendar = new ClockCalendar(getNext().getKey());
        calendar.setSecond(1);

        long nextTime = calendar.getTimeInMillis();
        long nowTime = new ClockCalendar().getTimeInMillis();
        long diffTime = nextTime-nowTime;

        ClockSchedulerAPI.debug("Próxima verificação é daqui a "+diffTime+" milésimos...", 2);

        schedulerBukkitID = Bukkit.getScheduler().runTaskLater(
                ClockSchedulerAPI.getInstance(), runnable, (diffTime/1000)*20
        ).getTaskId();

    }
}
