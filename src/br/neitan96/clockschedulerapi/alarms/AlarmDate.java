package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;

/**
 * Created by Neitan96 on 08/09/15.
 */
public class AlarmDate extends ClockAlarm {

    public final static String prefix = "Data";

    protected int year;
    protected int month;
    protected int day;
    protected int hour;
    protected int minute;

    public AlarmDate(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public AlarmDate(int year, String month, int day, int hour, int minute) {
        this(year, Util.monthStringToInt(month), day, hour, minute);
    }

    public AlarmDate(String time){
        String[] args = time.split("[|]");
        if(args.length != 2 || !args[0].equalsIgnoreCase(prefix))
            throwInvalid();

        args = args[1].split(" ");
        if(args.length != 2)
            throwInvalid();

        int[] dateArgs = Util.dateStringToInt(args[0]);
        int[] timeArgs = Util.timeStringToInt(args[1]);

        if(dateArgs == null || timeArgs == null)
            throwInvalid();

        assert dateArgs != null;
        assert timeArgs != null;

        this.year = dateArgs[2];
        this.month = dateArgs[1];
        this.day = dateArgs[0];
        this.hour = timeArgs[0];
        this.minute = timeArgs[1];
    }

    @Override
    public long calculateNext(long miliseconds) {
        ClockCalendar calendar = new ClockCalendar(miliseconds);
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(0);

        if(calendar.getTimeInMillis() < miliseconds)
            return (long) -1;

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
        return prefix + "|" + day + "/" + month + "/" + year +
                " " + hour + ":" + minute;
    }
}
