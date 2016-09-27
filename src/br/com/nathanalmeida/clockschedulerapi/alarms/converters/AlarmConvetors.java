package br.com.nathanalmeida.clockschedulerapi.alarms.converters;

import br.com.nathanalmeida.clockschedulerapi.alarms.ClockAlarm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neitan96 on 26/07/2016.
 */
public class AlarmConvetors{

    private static final Set<ClockAlarmConverter> alarmConverters = new HashSet<>();

    static{
        add(AlarmDailyConverter.getInstance());
        add(AlarmDateConverter.getInstance());
        add(AlarmHourConverter.getInstance());
        add(AlarmIntervalConverter.getInstance());
        add(AlarmMothlyConverter.getInstance());
        add(AlarmMothlyWeekConverter.getInstance());
        add(AlarmWeeklyConverter.getInstance());
        add(AlarmYearlyConverter.getInstance());
        add(AlarmMultiConverter.getInstance());
        add(AlarmBetweenConverter.getInstance());
    }

    public static ClockAlarm convert(String alarm){
        String labelAlarm = alarm.substring(0, alarm.indexOf('|'));
        for(ClockAlarmConverter alarmConverter : alarmConverters){
            for(String iLAbel : alarmConverter.getLabels()){
                if(iLAbel.equalsIgnoreCase(labelAlarm)){
                    ClockAlarm clockAlarm = alarmConverter.getAlarm(alarm);
                    if(clockAlarm != null) return clockAlarm;
                }
            }
        }
        return null;
    }

    public static Set<ClockAlarmConverter> getConveters(){
        return Collections.unmodifiableSet(alarmConverters);
    }

    public static void add(ClockAlarmConverter clockAlarmConverter){
        for(ClockAlarmConverter alarmConverter : alarmConverters){
            for(String nowLabel : alarmConverter.getLabels()){
                for(String newLabel : clockAlarmConverter.getLabels()){
                    if(nowLabel.equalsIgnoreCase(newLabel))
                        throw new IllegalArgumentException("Label already exists");
                }
            }
        }
        alarmConverters.add(clockAlarmConverter);
    }

    public static void remove(ClockAlarmConverter o){
        alarmConverters.remove(o);
    }

    private AlarmConvetors(){
    }

}
