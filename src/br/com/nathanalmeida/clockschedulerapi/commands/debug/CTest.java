package br.com.nathanalmeida.clockschedulerapi.commands.debug;

import br.com.nathanalmeida.clockschedulerapi.ClockSchedulerAPI;
import br.com.nathanalmeida.clockschedulerapi.alarms.*;
import br.com.nathanalmeida.clockschedulerapi.alarms.converters.AlarmConvetors;
import br.com.nathanalmeida.clockschedulerapi.sheduler.ClockTask;
import br.com.nathanalmeida.clockschedulerapi.util.ClockCalendar;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
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

            ClockSchedulerAPI.getInstance().saveTests();
            ClockLang.COMMANDS_OFFTESTING.sendTo(commandSender);
            return true;

        }else{

            int secondToTask = 10;

            if(strings.length > 0){
                if(strings[0].matches("[^0-9]")){
                    ClockLang.COMMANDS_INVALIDSECONDS.sendTo(commandSender);
                    return true;
                }
                secondToTask = Integer.parseInt(strings[0]);
            }

            ClockCalendar calendar = new ClockCalendar();
            long initialized = calendar.getTimeInMillis();
            calendar.addSecond(secondToTask);

            int year = calendar.getYear();
            int month = calendar.getMonth();
            int day = calendar.getDay();

            int hour = calendar.getHour();
            int minute = calendar.getMinute();
            int second = calendar.getSecond();

            int week = calendar.getWeek();
            int weekCount = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

            ClockSchedulerAPI plugin = ClockSchedulerAPI.getInstance();
            tasks = new ClockTask[10];
            int i = 0;

            ClockAlarm alarmHour = AlarmConvetors.convert(
                    new AlarmHour(minute, second).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmHour, initialized).getTask()
            );

            ClockAlarm alarmDaily = AlarmConvetors.convert(
                    new AlarmDaily(hour, minute, second).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmDaily, initialized).getTask()
            );

            ClockAlarm alarmWeekly = AlarmConvetors.convert(
                    new AlarmWeekly(week, hour, minute, second).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmWeekly, initialized).getTask()
            );

            ClockAlarm alarmMonthly = AlarmConvetors.convert(
                    new AlarmMonthly(day, hour, minute, second).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmMonthly, initialized).getTask()
            );

            ClockAlarm alarmMonthly1 = AlarmConvetors.convert(
                    new AlarmMonthlyWeek(weekCount, week, hour, minute, second).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmMonthly1, initialized).getTask()
            );

            ClockAlarm alarmYearly = AlarmConvetors.convert(
                    new AlarmYearly(day, month, hour, minute, second).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmYearly, initialized).getTask()
            );

            ClockAlarm alarmDate = AlarmConvetors.convert(
                    new AlarmDate(year, month, day, hour, minute, second).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmDate, initialized).getTask()
            );

            ClockAlarm alarmInterval = AlarmConvetors.convert(
                    new AlarmInterval(10).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmInterval, initialized).getTask()
            );

            ClockAlarm alarmMulti = AlarmConvetors.convert(
                    new AlarmMulti(
                            alarmInterval, alarmDate, alarmYearly
                    ).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i++] = new AlarmTest(alarmMulti, initialized).getTask()
            );

            ClockAlarm alarmBetween = AlarmConvetors.convert(
                    new AlarmBetween(
                            alarmDate, new AlarmInterval(15), new AlarmInterval(0)
                    ).toString()
            );
            ClockSchedulerAPI.addTask(
                    tasks[i] = new AlarmTest(alarmBetween, initialized).getTask()
            );

            ClockLang.COMMANDS_PERFORMTASKS.sendTo(commandSender, "date", calendar.toString(true));
        }
        return true;
    }

}
