package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 11/09/15.
 */
public class CDesativarPerms implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(command.getPermission())){

            ClockSchedulerAPI.getCommandConfig().desativarAlarms();

            ClockSchedulerAPI.log(commandSender, "Alarmes desativados");

            return true;
        }
        return false;
    }

}
