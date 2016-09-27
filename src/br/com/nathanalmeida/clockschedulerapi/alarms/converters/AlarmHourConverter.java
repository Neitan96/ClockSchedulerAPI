package br.com.nathanalmeida.clockschedulerapi.alarms.converters;

import br.com.nathanalmeida.clockschedulerapi.alarms.AlarmHour;
import br.com.nathanalmeida.clockschedulerapi.alarms.ClockAlarm;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmHourConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile("[^|]+\\|([0-9]{1,2})(:([0-9]{1,2}))?");

    private static final AlarmHourConverter ourInstance = new AlarmHourConverter();

    public static AlarmHourConverter getInstance(){
        return ourInstance;
    }

    private AlarmHourConverter(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int minute = Integer.parseInt(matcher.group(1));

        if(matcher.group(3) != null){
            int seconds = Integer.parseInt(matcher.group(3));
            return new AlarmHour(minute, seconds);
        }else{
            return new AlarmHour(minute);
        }
    }

    @Override
    public String[] getLabels(){
        return ClockLang.ALARM_HOUR.getMessage();
    }

}