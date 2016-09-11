package br.com.nathanalmeida.clockschedulerapi.commands.debug;

import br.com.nathanalmeida.clockschedulerapi.ClockSchedulerAPI;
import br.com.nathanalmeida.clockschedulerapi.sheduler.ClockTask;
import br.com.nathanalmeida.clockschedulerapi.sheduler.TaskManager;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Neitan96 on 11/08/2016.
 */
public class CStatus implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        TaskManager taskManager = ClockSchedulerAPI.getTaskManager();
        List<ClockTask> tasks = taskManager.getTasks();

        ClockLang.COMMANDS_STATUS_MANAGER.sendTo(commandSender, "status", taskManager.running());

        ClockLang.COMMANDS_STATUS_TOTALTASKS.sendTo(commandSender, "total", tasks.size());
        ClockLang.COMMANDS_STATUS_ACTIVETASKS.sendTo(commandSender, "total",
                tasks.stream().filter(ClockTask::enabled).count());
        ClockLang.COMMANDS_STATUS_DISABLEDTASKS.sendTo(commandSender, "total",
                tasks.stream().filter(t -> !t.enabled()).count());
        ClockLang.COMMANDS_STATUS_NEXTTASK.sendTo(commandSender, "task", taskManager.getNextTask());

        Set<String> plugins = new HashSet<>();
        tasks.forEach(t -> plugins.add(t.plugin.getName()));

        ClockLang.COMMANDS_STATUS_USINGPLUGINS.sendTo(commandSender, "plugins", plugins.size());

        if(plugins.size() < 6 && plugins.size() > 0)
            ClockLang.COMMANDS_STATUS_PLUGINS.sendTo(commandSender, "plugins", String.join(", ", plugins));

        return true;
    }
}
