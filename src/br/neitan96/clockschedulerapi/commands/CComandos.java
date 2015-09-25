package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Neitan96 on 11/09/15.
 */
public class CComandos implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(command.getPermission())){

            List<String> commands = ClockSchedulerAPI.getCommandConfig().getCommands();

            for(int i = 0; i < commands.size(); i++){
                ClockSchedulerAPI.log(commandSender, i+" - "+commands.get(i));
            }

            return true;
        }
        return false;
    }

}
