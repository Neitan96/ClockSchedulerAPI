package br.com.nathanalmeida.clockschedulerapi.commands.tasks;

import br.com.nathanalmeida.clockschedulerapi.ClockSchedulerAPI;
import br.com.nathanalmeida.clockschedulerapi.sheduler.ClockTask;
import br.com.nathanalmeida.clockschedulerapi.sheduler.TaskManager;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Neitan96 on 07/08/2016.
 */
public class CTasksDisableAll implements CommandExecutor{

    public final List<ClockTask> tasks = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        List<ClockTask> tasks;

        TaskManager taskManager = ClockSchedulerAPI.getTaskManager();

        if(strings.length > 0){

            String pluginName = strings[0];
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if(plugin == null){
                ClockLang.COMMANDS_INVALIDPLUGIN.sendTo(commandSender);
                return true;
            }

            tasks = taskManager.getTasks().stream()
                    .filter(ClockTask::enabled)
                    .filter(t -> t.plugin == plugin)
                    .collect(Collectors.toList());

        }else{

            tasks = taskManager.getTasks().stream()
                    .filter(ClockTask::enabled)
                    .collect(Collectors.toList());

        }

        tasks.forEach(ClockTask::disable);
        tasks.forEach(this.tasks::add);
        taskManager.start();

        ClockLang.COMMANDS_DISABLEDTASK.sendTo(commandSender);
        return true;
    }
}
