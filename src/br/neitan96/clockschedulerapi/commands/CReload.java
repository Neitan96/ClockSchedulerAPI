package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 01/07/2016.
 */
public class CReload implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        ClockSchedulerAPI.log("Recarregando plugin...");
        ClockSchedulerAPI.log("Desativando plugin...");
        ClockSchedulerAPI.getInstance().onDisable();
        ClockSchedulerAPI.log("Ativando plugin...");
        ClockSchedulerAPI.getInstance().reloadConfig();
        ClockSchedulerAPI.getInstance().onEnable();
        return true;
    }

}
