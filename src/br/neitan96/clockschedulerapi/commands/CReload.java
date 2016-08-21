package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 01/07/2016.
 */
public class CReload implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        ClockLang.COMMANDS_PLUGINRELOAD.sendTo(commandSender);
        ClockLang.COMMANDS_PLUGINDISABLING.sendTo(commandSender);
        ClockSchedulerAPI.getInstance().onDisable();
        ClockLang.COMMANDS_PLUGINENABLING.sendTo(commandSender);
        ClockSchedulerAPI.getInstance().reloadConfig();
        ClockSchedulerAPI.getInstance().onEnable();
        return true;
    }

}
