package br.neitan96.clockschedulerapi.manager;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Neitan96 on 09/09/15.
 */
public class AlarmsManager {

    protected TreeMap<Long, List<AlarmScheduler>> schedulers = new TreeMap<>();
    protected Runnable runnable = new AlarmsRunnable(this);
    protected int schedulerBukkitID = -1;
    protected long checkTime;


    public AlarmsManager(long checkTime) {
        this.checkTime = checkTime;
    }


    public TreeMap<Long, List<AlarmScheduler>> getSchedulers() {
        return schedulers;
    }


    public void addScheduler(AlarmScheduler scheduler){
        addScheduler(scheduler, new ClockCalendar().getTimeInMillis(), true);
    }

    public void addScheduler(ClockAlarm alarm, Runnable runnable, JavaPlugin plugin){
        addScheduler(new AlarmScheduler(alarm, runnable, plugin));
    }

    protected void addScheduler(AlarmScheduler scheduler, long milisecondsNow, boolean debug){

        long next = scheduler.getAlarm().getNextTime(milisecondsNow);

        if(next <= 0 || !scheduler.getAlarm().alarmActive()) {
            if(debug)
                ClockSchedulerAPI.debug("Alarme adicionado invalido ou desativado!", 1);
            return;
        }

        List<AlarmScheduler> schedulerList;

        if(schedulers.containsKey(next))
            schedulerList = schedulers.get(next);
        else
            schedulerList = new ArrayList<>();

        schedulerList.add(scheduler);

        schedulers.put(next, schedulerList);

        startCheck();

        if(debug && ClockSchedulerAPI.debug()){
            ClockCalendar clockCalendar = new ClockCalendar(next);
            ClockSchedulerAPI.debug("Alarme adicionado vai despertar: "+clockCalendar, 1);
        }
    }


    public void recacheMiliSeconds(long milisecondsKey){
        if(schedulers.containsKey(milisecondsKey)){

            List<AlarmScheduler> schedulerList = schedulers.remove(milisecondsKey);

            ClockCalendar calendar = new ClockCalendar();
            calendar.setSecond(0);
            calendar.addMinute();
            long miliseconds = calendar.getTimeInMillis();

            for(AlarmScheduler scheduler : schedulerList){
                addScheduler(scheduler, miliseconds, false);
            }
        }
    }


    public Map.Entry<Long, List<AlarmScheduler>> getNext(){
        return schedulers.firstEntry();
    }


    public void startCheck(){
        if(schedulerBukkitID < 0 && schedulers.size() > 0){
            ClockSchedulerAPI.debug("Iniciando scheduler de verificação...", 2);
            schedulerBukkitID = Bukkit.getScheduler().runTaskTimer(
                    ClockSchedulerAPI.getInstance(), runnable, 20, 20*checkTime
            ).getTaskId();
        }
    }


    public void removeAll(){
        Bukkit.getScheduler().cancelTask(schedulerBukkitID);
        schedulerBukkitID = -1;
        schedulers = new TreeMap<>();
    }

    public synchronized void removeOffs(){
        for (Map.Entry<Long, List<AlarmScheduler>> groupSchedulers : this.schedulers.entrySet()) {
            List<AlarmScheduler> schedulers = groupSchedulers.getValue();
            for (int i = schedulers.size()-1; i >= 0; i--) {
                if(!schedulers.get(i).getAlarm().alarmActive())
                    schedulers.remove(i);
            }
            this.schedulers.put(groupSchedulers.getKey(), schedulers);
        }
    }

    public synchronized void removeAllPlugin(JavaPlugin plugin){
        for (Map.Entry<Long, List<AlarmScheduler>> groupSchedulers : this.schedulers.entrySet()) {
            List<AlarmScheduler> schedulers = groupSchedulers.getValue();
            for (int i = schedulers.size()-1; i >= 0; i--) {
                if(schedulers.get(i).getPlugin() == plugin)
                    schedulers.remove(i);
            }
            this.schedulers.put(groupSchedulers.getKey(), schedulers);
        }
    }

}
