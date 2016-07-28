package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.AlarmHour;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmHourConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile(AlarmHour.LABEL +
            "\\|([0-9]{1,2})(:([0-9]{1,2}))?");

    private static AlarmHourConverter ourInstance = new AlarmHourConverter();

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

}