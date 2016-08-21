package br.neitan96.clockschedulerapi.commands.tasks;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.util.ClockLang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Neitan96 on 08/08/2016.
 */
public class CTasksEnableAll implements CommandExecutor{

    protected final CTasksDisableAll cTasksDisableAll;

    public CTasksEnableAll(CTasksDisableAll cTasksDisableAll){
        this.cTasksDisableAll = cTasksDisableAll;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        List<ClockTask> tasks;

        if(strings.length > 0){

            String pluginName = strings[0];
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if(plugin == null){
                ClockLang.COMMANDS_INVALIDPLUGIN.sendTo(commandSender);
                return true;
            }

            tasks = cTasksDisableAll.tasks.stream()
                    .filter(t -> t.plugin == plugin)
                    .collect(Collectors.toList());

        }else{

            tasks = cTasksDisableAll.tasks.stream()
                    .collect(Collectors.toList());

        }

        tasks.forEach((t) -> {
            t.enable();
            ClockSchedulerAPI.addTask(t);
            cTasksDisableAll.tasks.remove(t);
        });

        ClockLang.COMMANDS_ENABLEDTASKS.sendTo(commandSender);
        return true;
    }
}
