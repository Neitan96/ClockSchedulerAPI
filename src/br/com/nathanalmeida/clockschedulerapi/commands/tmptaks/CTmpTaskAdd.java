package br.com.nathanalmeida.clockschedulerapi.commands.tmptaks;

import br.com.nathanalmeida.clockschedulerapi.ClockSchedulerAPI;
import br.com.nathanalmeida.clockschedulerapi.sheduler.TaskCommand;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
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
            ClockLang.COMMANDS_INVALIDCOMMAND.sendTo(commandSender);
            return true;
        }

        String args = String.join(" ", strings);

        TaskCommand taskCommand = TaskCommand.fromString(ClockSchedulerAPI.getInstance(), args);

        if(taskCommand == null){
            ClockLang.COMMANDS_INVALIDCOMMAND.sendTo(commandSender);
            return true;
        }

        commandTmpTasks.tasks.add(taskCommand);
        ClockSchedulerAPI.addTask(taskCommand);

        ClockLang.COMMANDS_TASKADDED.sendTo(commandSender);

        return true;
    }
}
