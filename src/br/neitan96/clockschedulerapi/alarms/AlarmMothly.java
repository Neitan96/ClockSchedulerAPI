package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;

/**
 * Created by Neitan96 on 07/09/15.
 */
public class AlarmMothly extends ClockAlarm {

    protected boolean weeking = false;

    protected int weekCount = 0;
    protected int weekDay = 0;

    protected int monthDay = 0;

    protected int hour = 0;
    protected int minute = 0;

    public final static String prefix = "Mensal";

    public AlarmMothly(int weekCount, int weekDay, int hour, int minute){
        this.weekCount = weekCount;
        this.weekDay = weekDay;

        this.hour = hour;
        this.minute = minute;

        weeking = true;
    }

    public AlarmMothly(int day, int hour, int minute){

        this.monthDay = day;
        this.hour = hour;
        this.minute = minute;

        weeking = false;
    }

    public AlarmMothly(String time){

        String[] args = time.split("[|]");
        if(args.length != 2 || !args[0].equalsIgnoreCase(prefix))
            throwInvalid();


        if(args[1].matches("[0-9] [a-zA-Z-]* [0-9]{1,2}:[0-9]{1,2}")){

            args = args[1].split(" ");

            try{

                int weekCount = Integer.parseInt(args[0]);
                int weekDay = Util.weekStringToInt(args[1]);
                int[] timeArgs = Util.timeStringToInt(args[2]);

                if(weekDay < 1 || timeArgs == null)
                    throw new RuntimeException();

                int hour = timeArgs[0];
                int minute = timeArgs[1];

                this.weekCount = weekCount;
                this.weekDay = weekDay;

                this.hour = hour;
                this.minute = minute;

                weeking = true;

            }catch (Exception e){
                throwInvalid();
            }

        }else if(args[1].matches("[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}")){

            args = args[1].split(" ");

            int day = 0;

            try{
                day = Integer.parseInt(args[0]);
            }catch (Exception e){
                throwInvalid();
            }

            int[] timeArgs = Util.timeStringToInt(args[1]);

            if(timeArgs == null)
                throwInvalid();

            assert timeArgs != null;

            int hour = timeArgs[0];
            int minute = timeArgs[1];


            this.monthDay = day;
            this.hour = hour;
            this.minute = minute;

            weeking = false;
        }else{
            throwInvalid();
        }

    }

    @Override
    public long calculateNext(long miliseconds) {
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(0);

        if(weeking){

            int monthNow = calendar.getMonth();
            int off = 0;

            calendar.setDay(1);

            for(int i = 0; i < weekCount;){

                if(monthNow != calendar.getMonth()){
                    if(off == 11)
                        throwInvalid();

                    i = 0;
                    off++;
                    monthNow = calendar.getMonth();
                }

                if(calendar.getWeek() == weekDay){
                    if((i+1) == weekCount){
                        if(calendar.getTimeInMillis() < miliseconds){
                            calendar.addMonth();
                            calendar.setDay(1);
                        }else{
                            break;
                        }
                    }else{
                        i++;
                        calendar.addDay(7);
                    }
                }else{
                    calendar.addDay(1);
                }
            }

        }else{

            calendar.setDay(monthDay);

            if(calendar.getTimeInMillis() < miliseconds){
                calendar.addMonth(1);
                calendar.setDay(monthDay);
            }

        }

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
        return prefix + "|" + (weeking ?
        weekCount + " " + weekDay :
        monthDay ) + " " + Util.timeIntToString(hour, minute);
    }

}
