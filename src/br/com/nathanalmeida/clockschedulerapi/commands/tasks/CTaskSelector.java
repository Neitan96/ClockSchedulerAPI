package br.com.nathanalmeida.clockschedulerapi.commands.tasks;

import br.com.nathanalmeida.clockschedulerapi.sheduler.ClockTask;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Neitan96 on 12/08/2016.
 */
public abstract class CTaskSelector implements CommandExecutor{

    protected final CTasks cTasks;

    protected CTaskSelector(CTasks cTasks){
        this.cTasks = cTasks;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(strings.length < 1 || strings[0].matches("[^0-9]")){
            ClockLang.COMMANDS_INVALIDCOMMAND.sendTo(commandSender);
            return true;
        }

        int index = Integer.parseInt(strings[0]) - 1;

        List<ClockTask> tasks = cTasks.getTasks();

        ClockTask clockTask;
        if(index >= tasks.size() || index < 0 || ((clockTask = tasks.get(index)) == null)){
            ClockLang.COMMANDS_INVALIDCOMMANDUSECLOCKTASKS.sendTo(commandSender);
            return true;
        }

        return onCmd(commandSender, clockTask, command, s, strings);
    }

    abstract boolean onCmd(CommandSender commandSender, ClockTask task, Command command, String s, String[] strings);
}
