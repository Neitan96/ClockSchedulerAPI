package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neitan96 on 26/07/2016.
 */
public class AlarmConvetors{

    private static Set<ClockAlarmConverter> alarmConverters = new HashSet<>();

    static{
        add(AlarmDailyConverter.getInstance());
        add(AlarmDateConverter.getInstance());
        add(AlarmHourConverter.getInstance());
        add(AlarmIntervalConverter.getInstance());
        add(AlarmMothlyConverter.getInstance());
        add(AlarmMothlyWeekConverter.getInstance());
        add(AlarmWeeklyConverter.getInstance());
        add(AlarmYearlyConverter.getInstance());
    }

    public static ClockAlarm convert(String alarm){
        for(ClockAlarmConverter alarmConverter : alarmConverters){
            ClockAlarm clockAlarm = alarmConverter.getAlarm(alarm);
            if(clockAlarm != null) return clockAlarm;
        }
        return null;
    }

    public static Set<ClockAlarmConverter> getConveters(){
        return Collections.unmodifiableSet(alarmConverters);
    }

    public static boolean add(ClockAlarmConverter clockAlarmConverter){
        return alarmConverters.add(clockAlarmConverter);
    }

    public static boolean remove(ClockAlarmConverter o){
        return alarmConverters.remove(o);
    }

    private AlarmConvetors(){
    }

}
