package br.neitan96.clockschedulerapi.commands.debug;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.alarms.*;
import br.neitan96.clockschedulerapi.config.YamlConfigurationUTF8;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.sheduler.TaskPriority;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Neitan96 on 15/08/2016.
 */
public class AlarmTest{

    private static final List<TestResult> results = new ArrayList<>();

    public static void saveTests(File fileTest){
        if(results.size() > 0){
            try{

                YamlConfigurationUTF8 config = new YamlConfigurationUTF8();
                config.load(fileTest);
                results.forEach(r -> r.saveIn(config));
                config.save(fileTest);
                results.clear();

            }catch(IOException | InvalidConfigurationException e){
                e.printStackTrace();
            }
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
        switch(new Random().nextInt(10)){
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
            case 8:
                clockAlarm = new AlarmMulti(
                        new AlarmDaily(hour, minute, second),
                        new AlarmDate(year, month, day, hour, minute, second),
                        new AlarmHour(minute, second),
                        new AlarmInterval(interval),
                        new AlarmMonthly(day, hour, minute, second),
                        new AlarmMonthlyWeek(weekCount, week, hour, minute, second),
                        new AlarmWeekly(week, hour, minute, second),
                        new AlarmYearly(day, month, hour, minute, second)
                );
                break;
            case 9:
                clockAlarm = new AlarmBetween(
                        new AlarmDaily(hour, minute, second),
                        new AlarmHour(minute, second),
                        new AlarmMonthly(day, hour, minute, second)
                );
                break;
            default:
                return null;
        }

        return clockAlarm;
    }

    protected final AtomicReference<ClockTask> task = new AtomicReference<>();
    protected final ClockAlarm alarm;
    public final long initialized;

    public AlarmTest(long initialized){
        this(getRandomAlarm(15, 360), initialized);
    }

    public AlarmTest(ClockAlarm alarm, long initialized){
        this.alarm = alarm;
        this.initialized = initialized;

        task.set(new ClockTask(
                ClockSchedulerAPI.getInstance(),
                () -> {
                    if(task.get() != null){
                        TestResult result = new TestResult(alarm, initialized,
                                ClockCalendar.getClockMilisecond(), task.get().getLastExecution());
                        results.add(result);
                        task.get().disable();
                    }
                },
                alarm,
                TaskPriority.values()[new Random().nextInt(TaskPriority.HIGHEST.getOrder() + 1)]
        ));
    }

    public ClockTask getTask(){
        return task.get();
    }

}
