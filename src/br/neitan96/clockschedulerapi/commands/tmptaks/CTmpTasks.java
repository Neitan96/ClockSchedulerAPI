package br.neitan96.clockschedulerapi.commands.tmptaks;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
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
                ClockSchedulerAPI.log(commandSender, (i + 1) + " - " + tasks.get(i));
            }
        else
            ClockSchedulerAPI.log(commandSender, "No task.");
        return true;
    }
}
