package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.alarms.*;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 10/09/15.
 */
public class CTeste implements CommandExecutor{
    @Override
    public boolean onCommand(final CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(command.getPermission())){

            if(strings.length > 0){

                String alarm = String.join(" ", strings);

                ClockAlarm alarm1 = ClockAlarm.getFromString(alarm);

                if(alarm1 == null) {
                    ClockSchedulerAPI.log(commandSender, "Alarme inválido");
                    return true;
                }

                ClockCalendar calendar = new ClockCalendar(alarm1.getNextTime());
                final String dataHora = calendar.toString();

                ClockSchedulerAPI.addAlarm(alarm1, new Runnable() {
                    @Override
                    public void run() {
                        ClockSchedulerAPI.log(commandSender, "Esse alarme era para despetar: " + dataHora);
                    }
                }, ClockSchedulerAPI.getInstance());


                ClockSchedulerAPI.log(commandSender, "Alarme programado para: "+dataHora);

                return true;

            }else{

                ClockCalendar calendar = new ClockCalendar();
                calendar.setSecond(0);
                calendar.addMinute(1);

                int year = calendar.getYear();
                int month = calendar.getMonth();
                int day = calendar.getDay();

                int hour = calendar.getHour();
                int minute = calendar.getMinute();

                int week = calendar.getWeek();

                int weekCount = 0;

                ClockCalendar calendar2 = new ClockCalendar();
                while (calendar2.getMonth() == month){
                    weekCount++;
                    calendar2.addDay(-7);
                }

                AlarmHour alarmHour = new AlarmHour(minute);
                ClockSchedulerAPI.addAlarm(alarmHour, getRunAlarm(alarmHour), ClockSchedulerAPI.getInstance());

                AlarmDaily alarmDaily = new AlarmDaily(hour, minute);
                ClockSchedulerAPI.addAlarm(alarmDaily, getRunAlarm(alarmDaily), ClockSchedulerAPI.getInstance());

                AlarmWeekly alarmWeekly = new AlarmWeekly(week, hour, minute);
                ClockSchedulerAPI.addAlarm(alarmWeekly, getRunAlarm(alarmWeekly), ClockSchedulerAPI.getInstance());

                AlarmMothly alarmMothly = new AlarmMothly(day, hour, minute);
                ClockSchedulerAPI.addAlarm(alarmMothly, getRunAlarm(alarmMothly), ClockSchedulerAPI.getInstance());

                AlarmMothly alarmMothly1 = new AlarmMothly(weekCount, week, hour, minute);
                ClockSchedulerAPI.addAlarm(alarmMothly1, getRunAlarm(alarmMothly1), ClockSchedulerAPI.getInstance());

                AlarmYearly alarmYearly = new AlarmYearly(day, month, hour, minute);
                ClockSchedulerAPI.addAlarm(alarmYearly, getRunAlarm(alarmYearly), ClockSchedulerAPI.getInstance());

                AlarmDate alarmDate = new AlarmDate(year, month, day, hour, minute);
                ClockSchedulerAPI.addAlarm(alarmDate, getRunAlarm(alarmDate), ClockSchedulerAPI.getInstance());

                ClockSchedulerAPI.log(commandSender, "Alarmes vão despertar: " + calendar.toString());

                return true;
            }

        }
        return false;
    }

    private static Runnable getRunAlarm(final ClockAlarm alarm){
        ClockCalendar clockCalendar = new ClockCalendar(alarm.getNextTime());
        final String dataHora = clockCalendar.toString();
        final String autor = ClockSchedulerAPI.getInstance().getDescription().getAuthors().get(0);

        return new Runnable() {
            @Override
            public void run() {
                ClockSchedulerAPI.log("Esse alarme " + alarm.toString() + " era para despertar: " + dataHora);
            }
        };
    }
}
