package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;

/**
 * Created by Neitan96 on 08/09/15.
 */
public class AlarmYearly extends ClockAlarm {

    public final static String prefix = "Anual";

    protected int hour;
    protected int minute;

    protected int month;
    protected int day;

    public AlarmYearly(int day, int month, int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        this.month = month;
        this.day = day;
    }

    public AlarmYearly(int day, String month, int hour, int minute) {
        this(day, Util.monthStringToInt(month), hour, minute);
    }

    public AlarmYearly(String time){
        String[] args = time.split("[|]");
        if(args.length != 2 || !args[0].equalsIgnoreCase(prefix))
            throwInvalid();

        args = args[1].split(" ");
        if(args.length != 3)
            throwInvalid();

        int day = 0;
        int month;
        int hour;
        int minute;

        try{
            day = Integer.parseInt(args[0]);
        }catch (Exception e){
            throwInvalid();
        }

        month = Util.monthStringToInt(args[1]);

        if(month < 0)
            throwInvalid();

        int[] timeArgs = Util.timeStringToInt(args[2]);

        if(timeArgs == null)
            throwInvalid();

        assert timeArgs != null;

        hour = timeArgs[0];
        minute = timeArgs[1];

        this.hour = hour;
        this.minute = minute;
        this.month = month;
        this.day = day;
    }

    @Override
    public long calculateNext(long miliseconds) {
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(0);

        if(calendar.getTimeInMillis() < miliseconds)
            calendar.addYear();

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
        return prefix + "|" + day +
                " " + Util.monthIntToString(month) +
                " " + hour + ":" + minute;
    }
}
