package br.neitan96.clockschedulerapi.commands.tasks;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Neitan96 on 07/08/2016.
 */
public class CTasks implements CommandExecutor{

    protected final List<ClockTask> tasks = new ArrayList<>();

    public List<ClockTask> getTasks(){
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        List<ClockTask> tasks = ClockSchedulerAPI.getTaskManager().getTasks();

        this.tasks.clear();

        if(tasks.size() > 0){
            Iterator<ClockTask> iterator = tasks.iterator();
            for(int i = 0; iterator.hasNext(); i++){
                ClockTask next = iterator.next();
                this.tasks.add(next);
                ClockLang.COMMANDS_TASKLINE.sendTo(commandSender,
                        "index", i + 1,
                        "task", next,
                        "priority", next.priority.toString());
            }
        }else{
            ClockLang.COMMANDS_NOTASK.sendTo(commandSender);
        }

        return true;
    }
}
