package br.neitan96.clockschedulerapi.commands.time;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.TimeZone;

/**
 * Created by Neitan96 on 01/08/2016.
 */
public class CSetTimezone implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(strings.length < 1){
            ClockLang.COMMANDS_CURRENTTIMEZONE.sendTo(commandSender, "timezone", ClockCalendar.defaultTimeZone.getID());
            return true;
        }

        String timezoneArg = strings[0];

        String timezoneName = Arrays.stream(TimeZone.getAvailableIDs())
                .filter(tz -> tz.equalsIgnoreCase(timezoneArg)).findFirst().orElse(null);

        if(timezoneName == null){
            ClockLang.COMMANDS_INVALIDTIMEZONE.sendTo(commandSender);
            return true;
        }

        ClockCalendar.defaultTimeZone = TimeZone.getTimeZone(timezoneName);

        ClockSchedulerAPI.getInstance().saveCalendarToConfig();

        ClockLang.COMMANDS_TIMENOTCHANGED.sendTo(commandSender);
        return true;
    }
}
