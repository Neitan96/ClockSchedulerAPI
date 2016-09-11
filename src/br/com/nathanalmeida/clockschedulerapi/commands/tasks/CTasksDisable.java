package br.com.nathanalmeida.clockschedulerapi.commands.tasks;

import br.com.nathanalmeida.clockschedulerapi.sheduler.ClockTask;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
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

        ClockLang.COMMANDS_DISABLEDTASK.sendTo(commandSender);
        return true;
    }

}
