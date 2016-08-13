package br.neitan96.clockschedulerapi.commands.tmptaks;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.TaskCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 05/08/2016.
 */
public class CTmpTaskAdd implements CommandExecutor{

    private final CTmpTasks commandTmpTasks;

    public CTmpTaskAdd(CTmpTasks commandTmpTasks){
        this.commandTmpTasks = commandTmpTasks;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(strings.length < 1){
            ClockSchedulerAPI.log(commandSender, "Invalid command.");
            return true;
        }

        String args = String.join(" ", strings);

        TaskCommand taskCommand = TaskCommand.fromString(ClockSchedulerAPI.getInstance(), args);

        if(taskCommand == null){
            ClockSchedulerAPI.log(commandSender, "Invalid command.");
            return true;
        }

        commandTmpTasks.tasks.add(taskCommand);
        ClockSchedulerAPI.addTask(taskCommand);

        ClockSchedulerAPI.log(commandSender, "Temporary task added.");

        return true;
    }
}
