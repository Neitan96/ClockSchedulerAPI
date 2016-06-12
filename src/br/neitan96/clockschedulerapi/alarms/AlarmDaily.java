package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;

/**
 * Created by Neitan96 on 11/06/2016.
 */
public class AlarmDaily extends ClockAlarm {

    public final static String prefix = "Diario";

    protected int hour;
    protected int minute;

    public AlarmDaily(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public AlarmDaily(String time){

        String[] args = time.split("[|]");
        if(args.length != 2 || !args[0].equalsIgnoreCase(prefix))
            throwInvalid();

        int[] timeArgs = Util.timeStringToInt(args[1]);

        if(timeArgs == null)
            throwInvalid();

        assert timeArgs != null;

        this.hour = timeArgs[0];
        this.minute = timeArgs[1];
    }

    @Override
    public long calculateNext(long miliseconds) {
        ClockCalendar calendar = new ClockCalendar(miliseconds);
        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(0);

        if(calendar.getTimeInMillis() < miliseconds)
            calendar.addDay();

        return calendar.getTimeInMillis();
    }

    @Override
    public long calculateNext(ClockCalendar calendar) {
        return calculateNext(calendar.getTimeInMillis());
    }

    public long calculateNext(){
        return calculateNext(new ClockCalendar());
    }

    @Override
    public String toString() {
        return prefix+"|"+hour+":"+ minute;
    }
}
