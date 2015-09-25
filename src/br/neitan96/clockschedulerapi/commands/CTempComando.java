package br.neitan96.clockschedulerapi.commands;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Neitan96 on 10/09/15.
 */
public class CTempComando implements CommandExecutor{

    protected List<ClockAlarm> alarms = new ArrayList<>();

    public void desativarAlarms(){
        for(ClockAlarm alarm : alarms)
            alarm.cancelAlarm();
        alarms.clear();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission(command.getPermission())){

            final Map.Entry<String, String> alarmAndCommand = Util.alarmCommandStringToClass(strings);

            if(alarmAndCommand == null){
                ClockSchedulerAPI.log(commandSender, "Comando inválido");
                return true;
            }

            ClockAlarm alarm = ClockAlarm.getFromString(alarmAndCommand.getKey());

            if(alarm == null){
                ClockSchedulerAPI.log(commandSender, "Alarme inválido");
                return true;
            }

            alarms.add(alarm);

            ClockSchedulerAPI.addAlarm(alarm, alarmAndCommand.getValue(), ClockSchedulerAPI.getInstance());

            ClockSchedulerAPI.log(commandSender, "Alarme adicionado");

            return true;
        }
        return false;
    }
}
