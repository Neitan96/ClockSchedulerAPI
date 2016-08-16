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
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Neitan96 on 15/08/2016.
 */
public class AlarmTest{

    private static File fileTest = null;
    private static final YamlConfigurationUTF8 configTest = new YamlConfigurationUTF8();

    public static void setFileTest(File fileTest){
        AlarmTest.fileTest = fileTest;
        try{
            configTest.load(fileTest);
        }catch(IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public static ClockAlarm getRandomAlarm(int timeMin, int timeMax){
        int interval = new Random().nextInt(timeMax - timeMin) + timeMin + 1;

        ClockCalendar calendar = new ClockCalendar();
        calendar.addSecond(interval);

        int year = calendar.getYear(), month = calendar.getMonth(), day = calendar.getDay();
        int hour = calendar.getHour(), minute = calendar.getMinute(), second = calendar.getSecond();
        int week = calendar.getWeek(), weekCount = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

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

        return clockAlarm;
    }

    protected final AtomicReference<ClockTask> task = new AtomicReference<>();
    protected final ClockAlarm alarm;
    protected final String pathToSave;

    public AlarmTest(String pathToSave){
        this(getRandomAlarm(15, 360), pathToSave);
    }

    public ClockTask getTask(){
        return task.get();
    }

    public AlarmTest(ClockAlarm alarm, String pathToSave){
        this.alarm = alarm;
        this.pathToSave = pathToSave;

        task.set(new ClockTask(
                ClockSchedulerAPI.getInstance(),
                () -> {
                    if(task.get() != null){
                        ClockCalendar expected = new ClockCalendar(task.get().getLastExecution());
                        saveResult(pathToSave, task.get().alarm, expected);
                        task.get().disable();
                    }
                },
                alarm
        ));
    }

    private void saveResult(String pathToSave, ClockAlarm alarm, ClockCalendar expected){
        ClockCalendar now = new ClockCalendar();

        String nowStr = now.toString(true);
        String expectedStr = expected.toString(true);
        long diff = now.getTimeInMillis() - expected.getTimeInMillis();
        String approved = expectedStr.equals(nowStr) ? ".Sucess" : ".Fail";

        String pathBase = pathToSave + "." + approved;

        int count = 1;
        if(configTest.contains(pathBase))
            count = configTest.getConfigurationSection(pathBase).getKeys(false).size() + 1;

        String path = pathBase + ".Num-" + count;

        configTest.set(path + ".Alarm", alarm.toString());
        configTest.set(path + ".Now", nowStr);
        configTest.set(path + ".Expected", expectedStr);
        configTest.set(path + ".Diff", diff);

        if(fileTest != null)
            try{
                configTest.save(fileTest);
            }catch(IOException e){
                e.printStackTrace();
            }

    }


}
