package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;

/**
 * Created by Neitan96 on 08/09/15.
 */
public class AlarmWeekly extends ClockAlarm {

    public final static String prefix = "Semanal";

    protected int weekDay;
    protected int hour;
    protected int minute;

    public AlarmWeekly(int weekDay, int hour, int minute) {
        this.weekDay = weekDay;
        this.hour = hour;
        this.minute = minute;
    }

    public AlarmWeekly(String weekDay, int hour, int minute) {
        this(Util.weekStringToInt(weekDay), hour, minute);
    }

    public AlarmWeekly(String time) {

        String[] args = time.split("[|]");
        if(args.length != 2 || !args[0].equalsIgnoreCase(prefix))
            throwInvalid();

        args = args[1].split(" ");

        if(args.length != 2)
            throwInvalid();

        int weekDay = Util.weekStringToInt(args[0]);
        int[] timeArgs = Util.timeStringToInt(args[1]);

        if(weekDay < 0 || timeArgs == null)
            throwInvalid();

        assert timeArgs != null;

        this.weekDay = weekDay;
        this.hour = timeArgs[0];
        this.minute = timeArgs[1];
    }

    @Override
    public long calculateNext(long miliseconds) {
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        int hour = this.hour;
        int minute = this.minute;

        while (minute > 59){
            minute -= 60;
            hour++;
        }

        if(calendar.getHour() >= hour && calendar.getMinute() > minute)
            calendar.addDay();

        while (calendar.getWeek() != weekDay)
            calendar.addDay();

        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(0);
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
        return prefix + "|" + Util.weekIntToString(weekDay) + " " + Util.timeIntToString(hour, minute);
    }
}
