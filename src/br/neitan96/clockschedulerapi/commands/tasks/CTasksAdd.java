package br.neitan96.clockschedulerapi.commands.tasks;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.TaskCommand;
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
            ClockSchedulerAPI.log(commandSender, "Invalid command.");
            return true;
        }

        ClockSchedulerAPI.addTask(taskCommand);
        ClockSchedulerAPI.getTasksConfig().add(taskCommand);
        ClockSchedulerAPI.getInstance().saveTasksToConfig();

        ClockSchedulerAPI.log(commandSender, "Task added.");

        return true;
    }
}