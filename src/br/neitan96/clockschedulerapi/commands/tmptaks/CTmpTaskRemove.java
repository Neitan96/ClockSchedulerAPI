package br.neitan96.clockschedulerapi.commands.tmptaks;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 05/08/2016.
 */
public class CTmpTaskRemove implements CommandExecutor{

    protected final CTmpTasks cTmpTasks;

    public CTmpTaskRemove(CTmpTasks cTmpTasks){
        this.cTmpTasks = cTmpTasks;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(strings.length < 1 || strings[0].matches("[^0-9]")){
            ClockLang.COMMANDS_INVALIDCOMMAND.sendTo(commandSender);
            return true;
        }

        int index = Integer.parseInt(strings[0]) - 1;

        if(index >= cTmpTasks.tasks.size() || index < 0){
            ClockLang.COMMANDS_INVALIDCOMMAND.sendTo(commandSender);
            return true;
        }

        ClockTask task = cTmpTasks.tasks.remove(index);
        ClockSchedulerAPI.getTaskManager().removeTask(task);
        task.disable();

        ClockLang.DEBUG_TASKREMOVED.sendTo(commandSender);
        return true;
    }
}
