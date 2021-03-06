package br.com.nathanalmeida.clockschedulerapi.alarms.converters;

import br.com.nathanalmeida.clockschedulerapi.alarms.AlarmBetween;
import br.com.nathanalmeida.clockschedulerapi.alarms.ClockAlarm;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 23/08/2016.
 */
public class AlarmBetweenConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile("[^|]+\\|(.+)[ ]*,[ ]*(.+)[ ]*,[ ]*(.+)");

    private static final AlarmBetweenConverter ourInstance = new AlarmBetweenConverter();

    public static AlarmBetweenConverter getInstance(){
        return ourInstance;
    }

    private AlarmBetweenConverter(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        ClockAlarm start = AlarmConvetors.convert(matcher.group(1).trim());
        ClockAlarm end = AlarmConvetors.convert(matcher.group(2).trim());
        ClockAlarm clockAlarm = AlarmConvetors.convert(matcher.group(3).trim());

        if(start == null || end == null || clockAlarm == null)
            return null;

        return new AlarmBetween(start, end, clockAlarm);
    }

    @Override
    public String[] getLabels(){
        return ClockLang.ALARM_BETWEEN.getMessage();
    }

}
