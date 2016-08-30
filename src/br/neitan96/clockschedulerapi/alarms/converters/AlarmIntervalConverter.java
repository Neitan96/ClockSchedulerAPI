package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.AlarmInterval;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.ClockLang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmIntervalConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile("[^|]+\\|([0-9]+)");

    private static final AlarmIntervalConverter ourInstance = new AlarmIntervalConverter();

    public static AlarmIntervalConverter getInstance(){
        return ourInstance;
    }

    private AlarmIntervalConverter(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int intervalSeconds = Integer.parseInt(matcher.group(1));

        return new AlarmInterval(intervalSeconds);
    }

    @Override
    public String[] getLabels(){
        return ClockLang.ALARM_INTERVAL.getMessage();
    }

}