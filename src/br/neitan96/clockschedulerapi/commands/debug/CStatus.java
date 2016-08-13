package br.neitan96.clockschedulerapi.commands.debug;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.sheduler.TaskManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neitan96 on 11/08/2016.
 */
public class CStatus implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        TaskManager taskManager = ClockSchedulerAPI.getTaskManager();
        Set<ClockTask> tasks = taskManager.getTasks();

        ClockSchedulerAPI.log(commandSender, "Gerenciador: " + taskManager.started());

        ClockSchedulerAPI.log(commandSender, "Tasks total: " + tasks.size());
        ClockSchedulerAPI.log(commandSender, "Tasks ativas: " + tasks.stream().
                filter(ClockTask::enabled).count());
        ClockSchedulerAPI.log(commandSender, "Tasks desativadas: " + tasks.stream()
                .filter(t -> !t.enabled()).count());
        ClockSchedulerAPI.log(commandSender, "Próxima task: " + taskManager.getNextTask());

        Set<String> plugins = new HashSet<>();
        tasks.forEach(t -> plugins.add(t.plugin.getName()));

        ClockSchedulerAPI.log(commandSender, "Plugins usando: " + plugins.size());

        if(plugins.size() < 6)
            ClockSchedulerAPI.log(commandSender, "Plugins: " + String.join(", ", plugins));

        return true;
    }
}
