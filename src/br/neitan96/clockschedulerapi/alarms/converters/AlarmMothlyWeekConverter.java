package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.AlarmMonthlyWeek;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmMothlyWeekConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile(AlarmMonthlyWeek.LABEL +
            "\\|([0-9]{1,2}) ([a-zA-z-]+) ([0-9]{1,2}):([0-9]{1,2})(:([0-9]{1,2}))?");

    private static AlarmMothlyWeekConverter ourInstance = new AlarmMothlyWeekConverter();

    public static AlarmMothlyWeekConverter getInstance(){
        return ourInstance;
    }

    private AlarmMothlyWeekConverter(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int weekCount = Integer.parseInt(matcher.group(1));
        int week = Util.getWeek(matcher.group(2));
        int hour = Integer.parseInt(matcher.group(3));
        int minute = Integer.parseInt(matcher.group(4));

        if(week < 0) return null;

        if(matcher.group(6) != null){
            int seconds = Integer.parseInt(matcher.group(6));
            return new AlarmMonthlyWeek(weekCount, week, hour, minute, seconds);
        }else{
            return new AlarmMonthlyWeek(weekCount, week, hour, minute);
        }
    }

}