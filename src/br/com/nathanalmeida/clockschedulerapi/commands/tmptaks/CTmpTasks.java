package br.com.nathanalmeida.clockschedulerapi.commands.tmptaks;

import br.com.nathanalmeida.clockschedulerapi.sheduler.ClockTask;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neitan96 on 05/08/2016.
 */
public class CTmpTasks implements CommandExecutor{

    public final List<ClockTask> tasks;

    public CTmpTasks(List<ClockTask> tasks){
        this.tasks = tasks;
    }

    public CTmpTasks(){
        this(new ArrayList<>());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        if(tasks.size() > 0)
            for(int i = 0; i < tasks.size(); i++){
                ClockLang.COMMANDS_TASKLINE.sendTo(commandSender,
                        "index", i + 1,
                        "task", tasks.get(i),
                        "priority", tasks.get(i).priority);
            }
        else
            ClockLang.COMMANDS_NOTASK.sendTo(commandSender);
        return true;
    }
}
