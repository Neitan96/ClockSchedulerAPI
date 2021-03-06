package br.com.nathanalmeida.clockschedulerapi.commands.tasks;

import br.com.nathanalmeida.clockschedulerapi.ClockSchedulerAPI;
import br.com.nathanalmeida.clockschedulerapi.sheduler.TaskCommand;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 07/08/2016.
 */
public class CTasksAdd implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        String args = String.join(" ", strings);

        TaskCommand taskCommand = TaskCommand.fromString(ClockSchedulerAPI.getInstance(), args);

        if(taskCommand == null){
            ClockLang.COMMANDS_INVALIDCOMMAND.sendTo(commandSender);
            return true;
        }

        ClockSchedulerAPI.addTask(taskCommand);
        ClockSchedulerAPI.getTasksConfig().add(taskCommand);
        ClockSchedulerAPI.getInstance().saveTasksToConfig();

        ClockLang.COMMANDS_TASKADDED.sendTo(commandSender);

        return true;
    }
}
