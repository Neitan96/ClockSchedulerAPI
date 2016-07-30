package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.alarms.converters.AlarmConvetors;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 26/07/2016.
 */
public class TaskCommand extends ClockTask{

    public static TaskCommand fromString(JavaPlugin plugin, String alarm){
        Pattern pattern = Pattern.compile("\"([^\"]*)\"[ ]*\"(/)?([^\"]*)\"");
        Matcher matcher = pattern.matcher(alarm);

        if(!matcher.find()) return null;

        String alarmStr = matcher.group(1);
        String command = matcher.group(3);

        ClockAlarm clockAlarm = AlarmConvetors.convert(alarmStr);
        if(clockAlarm == null) return null;

        return new TaskCommand(plugin, command, clockAlarm);
    }

    protected final String command;

    public TaskCommand(JavaPlugin plugin, String command, ClockAlarm alarm){
        super(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command), alarm);
        this.command = command;
    }

    @Override
    public String toString(){
        return " \"" + alarm.toString() + "\" \"" + command + "\" ";
    }

}
