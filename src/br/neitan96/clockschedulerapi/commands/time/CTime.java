package br.neitan96.clockschedulerapi.commands.time;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 01/08/2016.
 */
public class CTime implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        ClockSchedulerAPI.log(commandSender, new ClockCalendar().toString(true));
        return true;
    }

}
