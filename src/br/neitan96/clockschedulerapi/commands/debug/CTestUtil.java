package br.neitan96.clockschedulerapi.commands.debug;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.alarms.*;
import br.neitan96.clockschedulerapi.config.YamlConfigurationUTF8;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Neitan96 on 12/08/2016.
 */
public class CTestUtil{

    public static ClockTask getRandomTask(String fileOut){
        ClockCalendar calendar = new ClockCalendar();
        int interval = new Random().nextInt(30 * 60) + 1;
        calendar.addSecond(interval);

        int year = calendar.getYear();
        int month = calendar.getMonth();
        int day = calendar.getDay();

        int hour = calendar.getHour();
        int minute = calendar.getMinute();
        int second = calendar.getSecond();

        int week = calendar.getWeek();
        int weekCount = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        ClockAlarm clockAlarm;
        switch(new Random().nextInt(8)){
            case 0:
                clockAlarm = new AlarmDaily(hour, minute, second);
                break;
            case 1:
                clockAlarm = new AlarmDate(year, month, day, hour, minute, second);
                break;
            case 2:
                clockAlarm = new AlarmHour(minute, second);
                break;
            case 3:
                clockAlarm = new AlarmInterval(interval);
                break;
            case 4:
                clockAlarm = new AlarmMonthly(day, hour, minute, second);
                break;
            case 5:
                clockAlarm = new AlarmMonthlyWeek(weekCount, week, hour, minute, second);
                break;
            case 6:
                clockAlarm = new AlarmWeekly(week, hour, minute, second);
                break;
            case 7:
                clockAlarm = new AlarmYearly(day, month, hour, minute, second);
                break;
            default:
                return null;
        }

        return new ClockTask(
                ClockSchedulerAPI.getInstance(),
                getRunTask(clockAlarm, calendar.getTimeInMillis(), fileOut),
                clockAlarm
        );
    }

    public static ClockTask getRandomTask(){
        return getRandomTask("Test.yml");
    }

    public static Runnable getRunTask(ClockAlarm alarm, Calendar calendar, String fileOut){
        return getRunTask(alarm, calendar.getTimeInMillis(), fileOut);
    }

    public static Runnable getRunTask(ClockAlarm alarm, Calendar calendar){
        return getRunTask(alarm, calendar, "Test.yml");
    }

    public static Runnable getRunTask(ClockAlarm alarm, long miliseconds, String fileOut){
        final AtomicBoolean logged = new AtomicBoolean(false);
        return () -> {
            ClockCalendar calendarExpected = new ClockCalendar(miliseconds);

            if(!logged.get() && fileOut != null){
                logged.set(true);
                saveTestResult(alarm, calendarExpected, fileOut);
            }

            ClockSchedulerAPI.log(
                    "The testing task " + alarm.toString() + " it was to awaken: " + new ClockCalendar().toString(true)
            );
        };
    }

    public static Runnable getRunTask(ClockAlarm alarm, long miliseconds){
        return getRunTask(alarm, miliseconds, null);
    }

    public static synchronized void saveTestResult(ClockAlarm alarm, ClockCalendar expected, String fileOut){
        ClockCalendar now = new ClockCalendar();

        String alarmStr = alarm.toString();
        String nowStr = now.toString(true);
        String expectedStr = expected.toString(true);
        long diff = now.getTimeInMillis() - expected.getTimeInMillis();
        boolean approved = expectedStr.equals(nowStr);

        String date = now.getDay() + "/" + now.getMonth() + "/" + now.getYear();

        File file = new File(ClockSchedulerAPI.getInstance().getDataFolder(), fileOut);
        YamlConfigurationUTF8 config = new YamlConfigurationUTF8();

        try{
            config.load(file);

            String path;
            int count = config.getKeys(false).size();

            do path = date + (approved ? ".Sucess" : ".Fail") + ".Teste-" + (count++);
            while(config.contains(path));

            config.set(path + ".Alarm", alarmStr);
            config.set(path + ".Now", nowStr);
            config.set(path + ".Expected", expectedStr);
            config.set(path + ".Diff", diff);
            config.save(file);

        }catch(IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }

    }

}
