package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

/**
 * Created by Neitan96 on 10/09/15.
 */
public class AlarmHour extends ClockAlarm{

    protected int minute;

    public final static String prefix = "Horario";

    public AlarmHour(int minute) {
        this.minute = minute;
    }

    public AlarmHour(String time){

        String[] args = time.split("[|]");
        if(args.length != 2 || !args[0].equalsIgnoreCase(prefix))
            throwInvalid();

        int minute = 0;

        try{
            minute = Integer.parseInt(args[1]);
        }catch (Exception e){
            throwInvalid();
        }

        this.minute = minute;
    }

    @Override
    public long calculateNext(long miliseconds) {
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setMinute(minute);

        if(calendar.getTimeInMillis() < miliseconds)
            calendar.addHour(1);

        return calendar.getTimeInMillis();
    }

    @Override
    public long calculateNext(ClockCalendar calendar) {
        ClockCalendar calendarAlarm = new ClockCalendar();

        if(calendar.getMinute() > minute)
            calendarAlarm.addHour(1);

        calendarAlarm.setMinute(minute);

        return calendar.getTimeInMillis();
    }

    @Override
    public long calculateNext() {
        return calculateNext(new ClockCalendar());
    }

    @Override
    public String toString() {
        return prefix + "|" + minute;
    }
}
