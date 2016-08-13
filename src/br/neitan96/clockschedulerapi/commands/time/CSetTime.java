package br.neitan96.clockschedulerapi.commands.time;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.TaskManager;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 01/08/2016.
 */
public class CSetTime implements CommandExecutor{

    public static String getSettings(){
        return (ClockCalendar.ajusteDays != 0 ? ClockCalendar.ajusteDays + "d " : "") +
                (ClockCalendar.ajusteHours != 0 ? ClockCalendar.ajusteHours + "h " : "") +
                (ClockCalendar.ajusteMinutes != 0 ? ClockCalendar.ajusteMinutes + "m " : "") +
                (ClockCalendar.ajusteSeconds != 0 ? ClockCalendar.ajusteSeconds + "s" : "").trim();

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(strings.length < 2){
            String settings = getSettings();
            if(settings.isEmpty())
                ClockSchedulerAPI.log(commandSender, "So far you have not made any adjustment time.");
            else
                ClockSchedulerAPI.log(commandSender, "Current setting: " + getSettings());
            return true;
        }

        Pattern pattern = Pattern.compile("[ ]*(-)?([0-9]+)([dhms])[ ]*");

        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        for(int i = 1; i < strings.length; i++){
            Matcher matcher = pattern.matcher(strings[i]);
            if(!matcher.matches()) continue;

            int number = Integer.parseInt(
                    (matcher.group(1) != null ? "-" : "") + matcher.group(2)
            );

            switch(matcher.group(3)){
                case "d":
                    days += number;
                    break;
                case "h":
                    hours += number;
                    break;
                case "m":
                    minutes += number;
                    break;
                case "s":
                    seconds += number;
                    break;
            }

        }

        if(days == 0 && hours == 0 && minutes == 0 && seconds == 0){
            ClockSchedulerAPI.log(commandSender, "Sorry, time has not changed.");
            return true;
        }

        ClockSchedulerAPI instance = ClockSchedulerAPI.getInstance();

        ClockCalendar.ajusteDays += days;
        ClockCalendar.ajusteHours += hours;
        ClockCalendar.ajusteMinutes += minutes;
        ClockCalendar.ajusteSeconds += seconds;

        while(ClockCalendar.ajusteSeconds > 59){
            ClockCalendar.ajusteSeconds -= 60;
            ClockCalendar.ajusteMinutes++;
        }

        while(ClockCalendar.ajusteMinutes > 59){
            ClockCalendar.ajusteMinutes -= 60;
            ClockCalendar.ajusteHours++;
        }

        while(ClockCalendar.ajusteHours > 23){
            ClockCalendar.ajusteHours -= 24;
            ClockCalendar.ajusteDays++;
        }

        instance.saveCalendarToConfig();

        String time = getSettings();

        if(!time.isEmpty())
            ClockSchedulerAPI.log(commandSender, "Now the clock is set to " + time);

        if(strings[0].equalsIgnoreCase("true")){
            TaskManager manager = ClockSchedulerAPI.getTaskManager();
            manager.start(true);
            ClockSchedulerAPI.log(commandSender, "Restarted tasks");
        }

        ClockSchedulerAPI.log(commandSender, "Time: " + new ClockCalendar().toString(true));

        return true;
    }
}