package br.neitan96.clockschedulerapi.alarms.convertors;

import br.neitan96.clockschedulerapi.alarms.AlarmMonthly;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmMothlyConvertor implements ClockAlarmConvertor{

    private static final Pattern format = Pattern.compile(AlarmMonthly.LABEL +
            "\\|([0-9]{1,2}) ([0-9]{1,2}):([0-9]{1,2})(:([0-9]{1,2}))?");

    private static AlarmMothlyConvertor ourInstance = new AlarmMothlyConvertor();

    public static AlarmMothlyConvertor getInstance(){
        return ourInstance;
    }

    private AlarmMothlyConvertor(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int day = Integer.parseInt(matcher.group(1));
        int hour = Integer.parseInt(matcher.group(2));
        int minute = Integer.parseInt(matcher.group(3));

        if(matcher.group(5) != null){
            int seconds = Integer.parseInt(matcher.group(5));
            return new AlarmMonthly(day, hour, minute, seconds);
        }else{
            return new AlarmMonthly(day, hour, minute);
        }
    }

}