package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.manager.AlarmScheduler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Neitan96 on 11/09/15.
 */
public class CDebugLista implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(command.getPermission())){

            TreeMap<Long, List<AlarmScheduler>> schedulers = ClockSchedulerAPI.getAlarmsManager().getSchedulers();


            boolean show = false;

            int i = 1;

                for (Map.Entry<Long, List<AlarmScheduler>> longListEntry : schedulers.entrySet()) {
                    for (AlarmScheduler scheduler : longListEntry.getValue()) {
                        if(scheduler.getAlarm().alarmActive()) {
                            ClockSchedulerAPI.log(commandSender, (i++)+" - "+scheduler.getAlarm().toString());
                            show = true;
                        }
                    }
                }

            if(!show)
                ClockSchedulerAPI.log(commandSender, "Nenhum alarme");

            return true;
        }
        return false;
    }
}
