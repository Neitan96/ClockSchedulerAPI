package br.neitan96.clockschedulerapi.commands.tasks;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.sheduler.TaskCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 07/08/2016.
 */
public class CTasksRemove extends CTaskSelector{

    public CTasksRemove(CTasks cTasks){
        super(cTasks);
    }

    @Override
    boolean onCmd(CommandSender commandSender, ClockTask task, Command command, String s, String[] strings){
        task.disable();
        ClockSchedulerAPI.getTaskManager().removeTask(task);

        if(task instanceof TaskCommand){
            ClockSchedulerAPI.getTasksConfig().remove((TaskCommand) task);
            ClockSchedulerAPI.getInstance().saveTasksToConfig();
        }

        ClockSchedulerAPI.log(commandSender, "Task removed.");
        return true;
    }

}
