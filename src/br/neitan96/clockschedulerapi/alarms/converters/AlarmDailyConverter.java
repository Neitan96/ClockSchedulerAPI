package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.AlarmDaily;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmDailyConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile(AlarmDaily.LABEL +
            "\\|([0-9]{1,2}):([0-9]{1,2})(:([0-9]{1,2}))?");

    private static AlarmDailyConverter ourInstance = new AlarmDailyConverter();

    public static AlarmDailyConverter getInstance(){
        return ourInstance;
    }

    private AlarmDailyConverter(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int hour = Integer.parseInt(matcher.group(1));
        int minute = Integer.parseInt(matcher.group(2));

        if(matcher.group(4) != null){
            int seconds = Integer.parseInt(matcher.group(4));
            return new AlarmDaily(hour, minute, seconds);
        }else{
            return new AlarmDaily(hour, minute);
        }
    }

}
