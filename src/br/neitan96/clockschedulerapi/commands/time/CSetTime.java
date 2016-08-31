package br.neitan96.clockschedulerapi.commands.time;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.sheduler.TaskManager;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockLang;
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
        long adjustmentMiliseconds = ClockCalendar.adjustmentMiliseconds;
        int adjustmentSeconds = 0;
        int adjustmentMinutes = 0;
        int adjustmentHours = 0;
        int adjustmentDays = 0;

        while(adjustmentMiliseconds >= 86400000){
            adjustmentMiliseconds -= 86400000;
            adjustmentDays++;
        }

        while(adjustmentMiliseconds >= 3600000){
            adjustmentMiliseconds -= 3600000;
            adjustmentHours++;
        }

        while(adjustmentMiliseconds >= 60000){
            adjustmentMiliseconds -= 60000;
            adjustmentMinutes++;
        }

        while(adjustmentMiliseconds >= 1000){
            adjustmentMiliseconds -= 1000;
            adjustmentSeconds++;
        }

        return (adjustmentDays != 0 ? adjustmentDays + "d " : "") +
                (adjustmentHours != 0 ? adjustmentHours + "h " : "") +
                (adjustmentMinutes != 0 ? adjustmentMinutes + "m " : "") +
                (adjustmentSeconds != 0 ? adjustmentSeconds + "s" : "").trim();

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(strings.length < 2){
            String settings = getSettings();
            if(settings.isEmpty())
                ClockLang.COMMANDS_TIMENOTCHANGED.sendTo(commandSender);
            else
                ClockLang.COMMANDS_CURRENTSETTING.sendTo(commandSender, "settings", getSettings());
            return true;
        }

        Pattern pattern = Pattern.compile("[ ]*(-)?([0-9]+)([dhms])[ ]*");

        int milisecond = 0;

        for(int i = 1; i < strings.length; i++){
            Matcher matcher = pattern.matcher(strings[i]);
            if(!matcher.matches()) continue;

            int number = Integer.parseInt(
                    (matcher.group(1) != null ? "-" : "") + matcher.group(2)
            );

            switch(matcher.group(3)){
                case "d":
                    milisecond += 86400000;
                    break;
                case "h":
                    milisecond += 3600000;
                    break;
                case "m":
                    milisecond += 60000;
                    break;
                case "s":
                    milisecond += 1000;
                    break;
            }

        }

        if(milisecond == 0){
            ClockLang.COMMANDS_TIMENOTCHANGED.sendTo(commandSender);
            return true;
        }

        ClockSchedulerAPI instance = ClockSchedulerAPI.getInstance();

        ClockCalendar.adjustmentMiliseconds += milisecond;

        instance.saveCalendarToConfig();

        String time = getSettings();

        if(!time.isEmpty())
            ClockLang.COMMANDS_CURRENTSETTING.sendTo(commandSender, "settings", getSettings());

        if(strings[0].equalsIgnoreCase("true")){
            TaskManager manager = ClockSchedulerAPI.getTaskManager();
            manager.getTasks().forEach(ClockTask::reset);
            manager.start();
            ClockLang.DEBUG_TASKRESTARTED.sendTo(commandSender);
        }

        ClockLang.COMMANDS_TIME.sendTo(commandSender, "time", new ClockCalendar().toString(true));
        return true;
    }
}
