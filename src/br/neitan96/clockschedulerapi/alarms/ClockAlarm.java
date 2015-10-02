package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neitan96 on 07/09/15.
 */
public abstract class ClockAlarm {


    protected static List<Class<? extends ClockAlarm>> alarms = new ArrayList<>();


    public static void registerAlarm(Class<? extends ClockAlarm> alarmClass){
        alarms.add(alarmClass);
    }

    public static void registerAllAlarms(){
        registerAlarm(AlarmDaily.class);
        registerAlarm(AlarmDate.class);
        registerAlarm(AlarmHour.class);
        registerAlarm(AlarmMothly.class);
        registerAlarm(AlarmWeekly.class);
        registerAlarm(AlarmYearly.class);
        registerAlarm(AlarmInterval.class);
    }

    public static ClockAlarm getFromString(String alarm){
        if(alarms.size() == 0)
            registerAllAlarms();

        for(Class<? extends ClockAlarm> alarmClass : alarms){
            try{

                Constructor<? extends ClockAlarm> constructor = alarmClass.getConstructor(String.class);
                return constructor.newInstance(alarm);

            }catch (Exception ignored){}
        }

        return  null;
    }





    protected long next = 0;

    protected boolean active = true;


    public abstract long calculateNext(long miliseconds);

    public abstract long calculateNext(ClockCalendar calendar);

    public abstract long calculateNext();


    public void throwInvalid(){
        throwInvalid(null);
    }

    public void throwInvalid(String msg){
        if(msg == null)
            msg = "Sheduler inválido: "+this.getClass().getSimpleName();

        throw new RuntimeException(msg);
    }


    public boolean cacheValid(long miliseconds){
        return next != 0 && next >= miliseconds;
    }

    public boolean cacheValid(){
        return cacheValid(new ClockCalendar().getTimeInMillis());
    }


    public long getNextTime(long miliseconds) {
        if(!active)
            return -1;
        if(!cacheValid(miliseconds))
            next = calculateNext(miliseconds);
        return next;
    }

    public long getNextTime() {
        return getNextTime(new ClockCalendar().getTimeInMillis());
    }


    public void cancelAlarm(){
        active = false;
    }


    public boolean alarmActive(){
        return active;
    }

}
