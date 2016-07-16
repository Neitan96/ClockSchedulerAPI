package br.neitan96.clockschedulerapi.alarms.convertors;

import br.neitan96.clockschedulerapi.alarms.AlarmDate;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmDateConvertor implements ClockAlarmConvertor{

    private static final Pattern format = Pattern.compile(AlarmDate.LABEL +
            "\\|([0-9]{1,2})/([0-9]{1,2})/([0-9]{4}) ([0-9]{1,2}):([0-9]{1,2})(:([0-9]{1,2}))?");

    private static AlarmDateConvertor ourInstance = new AlarmDateConvertor();

    public static AlarmDateConvertor getInstance(){
        return ourInstance;
    }

    private AlarmDateConvertor(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int day = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
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

}