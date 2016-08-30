package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.AlarmDate;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.ClockLang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmDateConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile("[^|]+\\|([0-9]{1,2})/([0-9]{1,2})/([0-9]{4}) ([0-9]{1,2}):([0-9]{1,2})(:([0-9]{1,2}))?");

    private static final AlarmDateConverter ourInstance = new AlarmDateConverter();

    public static AlarmDateConverter getInstance(){
        return ourInstance;
    }

    private AlarmDateConverter(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int day = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2)) - 1;
        int year = Integer.parseInt(matcher.group(3));
        int hour = Integer.parseInt(matcher.group(4));
        int minute = Integer.parseInt(matcher.group(5));

        if(matcher.group(7) != null){
            int seconds = Integer.parseInt(matcher.group(7));
            return new AlarmDate(year, month, day, hour, minute, seconds);
        }else{
            return new AlarmDate(year, month, day, hour, minute);
        }
    }

    @Override
    public String[] getLabels(){
        return ClockLang.ALARM_DATE.getMessage();
    }

}