package br.neitan96.clockschedulerapi.alarms.convertors;

import br.neitan96.clockschedulerapi.alarms.AlarmInterval;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public class AlarmIntervalConvertor implements ClockAlarmConvertor{

    private static final Pattern format = Pattern.compile(AlarmInterval.LABEL +
            "\\|([0-9]{1,2})");

    private static AlarmIntervalConvertor ourInstance = new AlarmIntervalConvertor();

    public static AlarmIntervalConvertor getInstance(){
        return ourInstance;
    }

    private AlarmIntervalConvertor(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        int intervalSeconds = Integer.parseInt(matcher.group(1));

        return new AlarmInterval(intervalSeconds);
    }

}