package br.neitan96.clockschedulerapi.commands.time;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
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
            ClockSchedulerAPI.log(commandSender, "Current time zone: " + ClockCalendar.defaultTimeZone.getID());
            return true;
        }

        String timezoneArg = strings[0];

        String timezoneName = Arrays.stream(TimeZone.getAvailableIDs())
                .filter(tz -> tz.equalsIgnoreCase(timezoneArg)).findFirst().orElse(null);

        if(timezoneName == null){
            ClockSchedulerAPI.log(commandSender, "Invalid time zone.");
            return true;
        }

        ClockCalendar.defaultTimeZone = TimeZone.getTimeZone(timezoneName);

        ClockSchedulerAPI.getInstance().saveCalendarToConfig();

        ClockSchedulerAPI.log(commandSender, "Timezone changed.");
        return true;
    }
}
