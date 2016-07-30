package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.AlarmWeekly;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmWeeklyConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile(AlarmWeekly.LABEL +
            "\\|([a-zA-Z-]+) ([0-9]{1,2}):([0-9]{1,2})(:([0-9]{1,2}))?");

    private static final AlarmWeeklyConverter ourInstance = new AlarmWeeklyConverter();

    public static AlarmWeeklyConverter getInstance(){
        return ourInstance;
    }

    private AlarmWeeklyConverter(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int week = Util.getWeek(matcher.group(1));
        int hour = Integer.parseInt(matcher.group(2));
        int minute = Integer.parseInt(matcher.group(3));

        if(week < 0) return null;

        if(matcher.group(5) != null){
            int seconds = Integer.parseInt(matcher.group(5));
            return new AlarmWeekly(week, hour, minute, seconds);
        }else{
            return new AlarmWeekly(week, hour, minute);
        }
    }

}