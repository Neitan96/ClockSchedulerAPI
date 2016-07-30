package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.AlarmYearly;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmYearlyConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile(AlarmYearly.LABEL +
            "\\|([0-9]{1,2}) ([a-zA-Z]+) ([0-9]{1,2}):([0-9]{1,2})(:([0-9]{1,2}))?");

    private static final AlarmYearlyConverter ourInstance = new AlarmYearlyConverter();

    public static AlarmYearlyConverter getInstance(){
        return ourInstance;
    }

    private AlarmYearlyConverter(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int day = Integer.parseInt(matcher.group(2));
        int month = Util.getMonth(matcher.group(1));
        int hour = Integer.parseInt(matcher.group(3));
        int minute = Integer.parseInt(matcher.group(4));

        if(month < 0) return null;

        if(matcher.group(6) != null){
            int seconds = Integer.parseInt(matcher.group(6));
            return new AlarmYearly(month, day, hour, minute, seconds);
        }else{
            return new AlarmYearly(month, day, hour, minute);
        }
    }

}