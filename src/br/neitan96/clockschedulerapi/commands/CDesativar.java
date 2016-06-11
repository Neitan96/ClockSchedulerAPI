package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 10/09/15.
 */
public class CDesativar implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(command.getPermission())){

            ClockSchedulerAPI.getAlarmsManager().removeAll();

            ClockSchedulerAPI.log(commandSender, "Alarmes removidos!");

            return true;
        }
        return false;
    }

}
