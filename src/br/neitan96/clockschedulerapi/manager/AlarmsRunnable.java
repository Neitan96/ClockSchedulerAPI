package br.neitan96.clockschedulerapi.manager;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Map;

/**
 * Created by Neitan96 on 09/09/15.
 */
public class AlarmsRunnable implements Runnable{

    protected AlarmsManager alarmsManager;

    public AlarmsRunnable(AlarmsManager alarmsManager) {
        this.alarmsManager = alarmsManager;
    }

    @Override
    public void run() {

        ClockSchedulerAPI.debug("Verificando alarmes...", 2);

        Map.Entry<Long, List<AlarmScheduler>> next;

        ClockCalendar calendar = new ClockCalendar();
        calendar.setSecond(59);

        while (true){

            next = alarmsManager.getNext();

            if(next == null){
                ClockSchedulerAPI.debug("Sem alarmes, destivando verificador!", 2);
                alarmsManager.stopCheck();
                return;
            }

            if(next.getKey() > calendar.getTimeInMillis()){
                ClockSchedulerAPI.debug("Alarme ainda não está no tempo!", 2);
                return;
            }

            ClockSchedulerAPI.debug("Executando lista de alarmes...", 2);

            List<AlarmScheduler> alarms = next.getValue();

            for(AlarmScheduler scheduler : alarms){
                if(scheduler.getAlarm().alarmActive()){
                    ClockSchedulerAPI.debug("Executando um alarme "+scheduler.getAlarm().getClass().getSimpleName()+"...", 2);

                    Bukkit.getScheduler().runTaskLater(
                            ClockSchedulerAPI.getInstance(), scheduler.getRunnable(), 10L
                    );

                }
            }

            alarmsManager.recacheAlarms(next.getKey());

        }

    }
}
