package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 11/09/15.
 */
public class CDesativarTemps implements CommandExecutor{

    protected CTempComando tempCommand;

    public CDesativarTemps(CTempComando tempCommand) {
        this.tempCommand = tempCommand;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(command.getPermission())){

            tempCommand.desativarAlarms();

            ClockSchedulerAPI.log(commandSender, "Alarmes desativados");

            return true;
        }
        return false;
    }
}
