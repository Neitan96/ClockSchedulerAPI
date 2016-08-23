package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.AlarmMulti;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Neitan96 on 21/08/2016.
 */
public class AlarmMultiConverter implements ClockAlarmConverter{

    private static final Pattern format = Pattern.compile(AlarmMulti.LABEL + "\\|(.+)");

    private static final AlarmMultiConverter ourInstance = new AlarmMultiConverter();

    public static AlarmMultiConverter getInstance(){
        return ourInstance;
    }

    private AlarmMultiConverter(){
    }

    @Override
    public ClockAlarm getAlarm(String alarm){
        Matcher matcher = format.matcher(alarm);
        if(!matcher.matches()) return null;

        String alarmStr = matcher.group(1);

        List<ClockAlarm> alarms = Arrays.stream(alarmStr.split("[,]"))
                .map(String::trim).map(AlarmConvetors::convert).collect(Collectors.toList());

        if(alarms.size() < 1) return null;

        return new AlarmMulti(alarms.toArray(new ClockAlarm[alarms.size()]));
    }
}