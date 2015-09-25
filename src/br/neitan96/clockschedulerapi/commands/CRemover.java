package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 11/09/15.
 */
public class CRemover implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(command.getPermission())){

            if(strings.length < 1){
                ClockSchedulerAPI.log("Comando inválido");
                return true;
            }

            int index;

            try{
                index = Integer.parseInt(strings[0]);
            }catch (Exception e){
                ClockSchedulerAPI.log("Comando inválido");
                return true;
            }

            if(index >= ClockSchedulerAPI.getCommandConfig().getSizeCommand()){
                ClockSchedulerAPI.log("Index inválida");
                return true;
            }

            ClockSchedulerAPI.getCommandConfig().removeIndex(index);

            ClockSchedulerAPI.log(commandSender, "Comando removido");

            return true;
        }
        return false;
    }

}
