package br.neitan96.clockschedulerapi.manager;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Neitan96 on 11/06/2016.
 */
public interface AlarmsManager{


    TreeMap<Long, List<AlarmScheduler>> getSchedulers();


    void addScheduler(AlarmScheduler scheduler);

    void addScheduler(ClockAlarm alarm, Runnable runnable, JavaPlugin plugin);


    void recacheAlarms(long milisecondsKey);


    Map.Entry<Long, List<AlarmScheduler>> getNext();


    void startCheck();

    void stopCheck();


    void removeAll();

    void removeBefore();

    void removeAllPlugin(JavaPlugin plugin);

}
