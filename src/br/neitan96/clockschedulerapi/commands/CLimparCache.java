package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 11/09/15.
 */
public class CLimparCache implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(command.getPermission())){

            ClockSchedulerAPI.getAlarmsManager().removeBefore();

            ClockSchedulerAPI.log(commandSender, "Cache limpo");

            return true;
        }
        return false;
    }
}
