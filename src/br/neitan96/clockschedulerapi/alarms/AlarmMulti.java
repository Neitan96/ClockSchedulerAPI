package br.neitan96.clockschedulerapi.alarms;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Neitan96 on 21/08/2016.
 */
public class AlarmMulti implements ClockAlarm{

    public final static String LABEL = "Multi";

    protected final ClockAlarm[] alarms;

    public AlarmMulti(ClockAlarm... alarms){
        this.alarms = alarms;
    }

    @Override
    public long nextAfter(long miliseconds){
        long nextAfter = -1;

        for(ClockAlarm alarm : alarms){
            long next = alarm.nextAfter(miliseconds);
            nextAfter = nextAfter < 0 || next < nextAfter ? next : nextAfter;
        }

        return nextAfter;
    }

    @Override
    public String toString(){
        List<String> alarms = Arrays.stream(this.alarms)
                .map(ClockAlarm::toString).collect(Collectors.toList());
        return String.format("%s|%s", LABEL, String.join(", ", alarms));
    }
}
