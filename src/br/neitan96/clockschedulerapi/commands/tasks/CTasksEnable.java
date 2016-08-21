package br.neitan96.clockschedulerapi.commands.tasks;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 11/08/2016.
 */
public class CTasksEnable extends CTaskSelector{

    public CTasksEnable(CTasks cTasks){
        super(cTasks);
    }

    @Override
    boolean onCmd(CommandSender commandSender, ClockTask task, Command command, String s, String[] strings){

        if(!task.enabled()){
            task.enable();
            ClockSchedulerAPI.getTaskManager().start();
        }

        ClockLang.COMMANDS_ENABLEDTASK.sendTo(commandSender);
        return true;
    }

}
