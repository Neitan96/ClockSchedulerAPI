package br.com.nathanalmeida.clockschedulerapi.alarms.converters;

import br.com.nathanalmeida.clockschedulerapi.alarms.AlarmMonthly;
import br.com.nathanalmeida.clockschedulerapi.alarms.ClockAlarm;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmMothlyConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile("[^|]+\\|([0-9]{1,2}) ([0-9]{1,2}):([0-9]{1,2})(:([0-9]{1,2}))?");

    private static final AlarmMothlyConverter ourInstance = new AlarmMothlyConverter();

    public static AlarmMothlyConverter getInstance(){
        return ourInstance;
    }

    private AlarmMothlyConverter(){
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

    @Override
    public String[] getLabels(){
        return ClockLang.ALARM_MONTHLY.getMessage();
    }

}