package br.neitan96.clockschedulerapi.commands.debug;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.alarms.*;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Neitan96 on 05/08/2016.
 */
public class CTest implements CommandExecutor{
    protected ClockTask tasks[] = null;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        if(tasks != null){

            Arrays.stream(tasks).filter(t -> t != null)
                    .filter(t -> t != null)
                    .forEach(t -> {
                        ClockSchedulerAPI.getTaskManager().removeTask(t);
                        t.disable();
                    });

            tasks = null;

            ClockSchedulerAPI.log(commandSender, "Off testing.");
            return true;

        }else{

            int secondToTask = 10;

            if(strings.length > 0){
                if(strings[0].matches("[^0-9]")){
                    ClockSchedulerAPI.log(commandSender, "Invalid seconds.");
                    return true;
                }
                secondToTask = Integer.parseInt(strings[0]);
            }

            ClockCalendar calendar = new ClockCalendar();
            calendar.addSecond(secondToTask);

            int year = calendar.getYear();
            int month = calendar.getMonth();
            int day = calendar.getDay();

            int hour = calendar.getHour();
            int minute = calendar.getMinute();
            int second = calendar.getSecond();

            int week = calendar.getWeek();
            int weekCount = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

            String pathToSave = String.format("%d-%02d-%02d-%02d:%02d:%02d",
                    year, month, day, hour, minute, second);

            ClockSchedulerAPI plugin = ClockSchedulerAPI.getInstance();
            tasks = new ClockTask[8];
            int i = 0;

            AlarmHour alarmHour = new AlarmHour(minute, second);
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmHour, pathToSave).getTask()
            );

            AlarmDaily alarmDaily = new AlarmDaily(hour, minute, second);
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmDaily, pathToSave).getTask()
            );

            AlarmWeekly alarmWeekly = new AlarmWeekly(week, hour, minute, second);
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmWeekly, pathToSave).getTask()
            );

            AlarmMonthly alarmMonthly = new AlarmMonthly(day, hour, minute, second);
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmMonthly, pathToSave).getTask()
            );

            AlarmMonthlyWeek alarmMonthly1 = new AlarmMonthlyWeek(weekCount, week, hour, minute, second);
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmMonthly1, pathToSave).getTask()
            );

            AlarmYearly alarmYearly = new AlarmYearly(day, month, hour, minute, second);
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmYearly, pathToSave).getTask()
            );

            AlarmDate alarmDate = new AlarmDate(year, month, day, hour, minute, second);
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmDate, pathToSave).getTask()
            );

            AlarmInterval alarmInterval = new AlarmInterval(10);
            ClockSchedulerAPI.addTask(
                    tasks[i] = new AlarmTest(alarmInterval, pathToSave).getTask()
            );

            ClockSchedulerAPI.log(commandSender, "Tasks will be performed at " + calendar.toShortString(true));
        }
        return true;
    }

}
