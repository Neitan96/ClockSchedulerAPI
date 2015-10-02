package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

/**
 * Created by Neitan96 on 02/10/15.
 */
public class AlarmInterval extends ClockAlarm{

    int interval;

    public final static String prefix = "Intervalo";

    public ClockCalendar calendar = new ClockCalendar();

    public AlarmInterval(int interval) {
        this.interval = interval;
        calendar.addMinute(interval);
    }

    public AlarmInterval(String time) {

        String[] args = time.split("[|]");
        if(args.length != 2 || !args[0].equalsIgnoreCase(prefix))
            throwInvalid();

        int interval = 0;

        try{
            interval = Integer.parseInt(args[1]);
        }catch (Exception e){
            throwInvalid();
        }

        this.interval = interval;
        calendar.addMinute(interval);
    }

    @Override
    public long calculateNext(long miliseconds) {
        while (calendar.getTimeInMillis() < miliseconds)
            calendar.addMinute(interval);
        return calendar.getTimeInMillis();
    }

    @Override
    public long calculateNext(ClockCalendar calendar) {
        return calculateNext(calendar.getTimeInMillis());
    }

    @Override
    public long calculateNext() {
        return calculateNext(new ClockCalendar());
    }

    @Override
    public String toString() {
        return prefix + "|" + interval;
    }
}
