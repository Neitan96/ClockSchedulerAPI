package br.neitan96.clockschedulerapi.commands.time;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.TaskManager;
import br.neitan96.clockschedulerapi.util.AdjustmentTime;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * Created by Neitan96 on 01/08/2016.
 */
public class CSetTime implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(strings.length < 2){
            if(ClockCalendar.adjustment.getInMiliseconds() == 0)
                ClockLang.COMMANDS_TIMENOTCHANGED.sendTo(commandSender);
            else
                ClockLang.COMMANDS_CURRENTSETTING.sendTo(commandSender, "settings", ClockCalendar.adjustment.toString());
            return true;
        }

        String[] adjustArgs = Arrays.copyOfRange(strings, 1, strings.length);
        long milliseconds = AdjustmentTime.getMilliseconds(adjustArgs);

        if(milliseconds == 0){
            ClockLang.COMMANDS_TIMENOTCHANGED.sendTo(commandSender);
            return true;
        }

        ClockSchedulerAPI instance = ClockSchedulerAPI.getInstance();

        ClockCalendar.adjustment.addMiliseconds(milliseconds);

        instance.saveCalendarToConfig();

        String adjustToString = ClockCalendar.adjustment.toString();

        ClockLang.COMMANDS_CURRENTSETTING.sendTo(commandSender,
                "settings", ClockCalendar.adjustment.toString());

        if(strings[0].equalsIgnoreCase("true")){
            TaskManager manager = ClockSchedulerAPI.getTaskManager();
            manager.getTasks().forEach(task -> {
                task.reset();
                ClockSchedulerAPI.getTaskManager().updateItem(task);
            });
            ClockLang.DEBUG_TASKRESTARTED.sendTo(commandSender);
        }

        ClockLang.COMMANDS_TIME.sendTo(commandSender, "adjustToString", new ClockCalendar().toString(true));
        return true;
    }
}
