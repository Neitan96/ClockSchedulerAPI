package br.com.nathanalmeida.clockschedulerapi.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Neitan96 on 08/09/15.
 */
public class ClockCalendar extends GregorianCalendar{

    public static long getClockMilisecond(){
        return new ClockCalendar().getTimeInMillis();
    }

    public static String getString(long milisecond, boolean second){
        return new ClockCalendar(milisecond).toString(second);
    }

    public static String getShortString(long milisecond, boolean second){
        return new ClockCalendar(milisecond).toShortString(second);
    }

    public static TimeZone defaultTimeZone = null;
    public static final AdjustmentTime adjustment = new AdjustmentTime(0);

    public ClockCalendar(){
        super();
        if(defaultTimeZone != null)
            setTimeZone(defaultTimeZone);
        adjustment.adjust(this);
    }

    public ClockCalendar(long miliseconds){
        this();
        setTimeInMillis(miliseconds);
    }


    public int getMaxDays(){
        return getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public int getDay(){
        return get(Calendar.DAY_OF_MONTH);
    }

    public void addDay(){
        addDay(1);
    }

    public void addDay(int day){
        add(Calendar.DAY_OF_MONTH, day);
    }

    public void setDay(int day){
        set(Calendar.DAY_OF_MONTH, day);
    }


    public int getMonth(){
        return get(Calendar.MONTH);
    }

    public void addMonth(int month){
        add(Calendar.MONTH, month);
    }

    public void addMonth(){
        addMonth(1);
    }

    public void setMonth(int month){
        set(Calendar.MONTH, month);
    }


    public int getYear(){
        return get(Calendar.YEAR);
    }

    public void addYear(int year){
        add(Calendar.YEAR, year);
    }

    public void addYear(){
        addYear(1);
    }

    public void setYear(int year){
        set(Calendar.YEAR, year);
    }


    public int getHour(){
        return get(Calendar.HOUR_OF_DAY);
    }

    public void addHour(int hour){
        add(Calendar.HOUR_OF_DAY, hour);
    }

    public void addHour(){
        addHour(1);
    }

    public void setHour(int hour){
        set(Calendar.HOUR_OF_DAY, hour);
    }


    public int getMinute(){
        return get(Calendar.MINUTE);
    }

    public void addMinute(int minute){
        add(Calendar.MINUTE, minute);
    }

    public void addMinute(){
        addMinute(1);
    }

    public void setMinute(int minute){
        set(Calendar.MINUTE, minute);
    }


    public int getWeek(){
        return get(Calendar.DAY_OF_WEEK);
    }

    public void addWeek(int weekDay){
        add(Calendar.DAY_OF_WEEK, weekDay);
    }

    public void addWeek(){
        addWeek(1);
    }

    public void setWeek(int weekDay){
        set(Calendar.DAY_OF_WEEK, weekDay);
    }


    public int getSecond(){
        return get(Calendar.SECOND);
    }

    public void addSecond(int second){
        add(Calendar.SECOND, second);
    }

    public void addSecond(){
        addSecond(1);
    }

    public void setSecond(int second){
        set(Calendar.SECOND, second);
    }


    public int getMilisecond(){
        return get(Calendar.MILLISECOND);
    }

    public void addMilisecond(int milisecond){
        add(Calendar.MILLISECOND, milisecond);
    }

    public void addMilisecond(){
        addMilisecond(1);
    }

    public void setMilisecond(int milisecond){
        set(Calendar.MILLISECOND, milisecond);
    }

    @Override
    public ClockCalendar clone(){
        return new ClockCalendar(getTimeInMillis());
    }

    @Override
    public String toString(){
        return toString(false);
    }

    public String toString(boolean seconds){
        if(seconds)
            return String.format("%02d de %s de %d (%s) as %02d:%02d:%02d",
                    getDay(), Util.getMonth(getMonth()), getYear(), Util.getWeek(getWeek()),
                    getHour(), getMinute(), getSecond());
        else
            return String.format("%02d de %s de %d (%s) as %02d:%02d",
                    getDay(), Util.getMonth(getMonth()), getYear(), Util.getWeek(getWeek()),
                    getHour(), getMinute());
    }

    public String toShortString(boolean seconds){
        if(seconds)
            return String.format("%02d de %s as %02d:%02d:%02d",
                    getDay(), Util.getMonth(getMonth()), getHour(), getMinute(), getSecond());
        else
            return String.format("%02d de %s as %02d:%02d",
                    getDay(), Util.getMonth(getMonth()), getHour(), getMinute());
    }

}
