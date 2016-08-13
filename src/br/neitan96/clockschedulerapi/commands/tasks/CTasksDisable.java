package br.neitan96.clockschedulerapi.commands.tasks;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 11/08/2016.
 */
public class CTasksDisable extends CTaskSelector{

    public CTasksDisable(CTasks cTasks){
        super(cTasks);
    }

    @Override
    boolean onCmd(CommandSender commandSender, ClockTask task, Command command, String s, String[] strings){

        if(task.enabled()) task.disable();

        ClockSchedulerAPI.log(commandSender, "Disabled task.");
        return true;
    }

}