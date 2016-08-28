package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.alarms.converters.AlarmConvetors;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 26/07/2016.
 */
public class TaskCommand extends ClockTask{

    public static TaskCommand fromString(JavaPlugin plugin, String alarm){
        Pattern pattern = Pattern.compile("[ ]*(@([^ ]+))?[ ]*\"([^\"]*)\"[ ]*\"(/)?([^\"]*)\"[ ]*");
        Matcher matcher = pattern.matcher(alarm);

        if(!matcher.find()) return null;

        TaskPriority priority = TaskPriority.fromString(matcher.group(2));
        priority = priority != null ? priority : TaskPriority.NORMAL;
        String alarmStr = matcher.group(3);
        String command = matcher.group(5);

        ClockAlarm clockAlarm = AlarmConvetors.convert(alarmStr);
        if(clockAlarm == null) return null;

        return new TaskCommand(plugin, command, clockAlarm, priority);
    }

    protected final String command;

    public TaskCommand(JavaPlugin plugin, String command, ClockAlarm alarm, TaskPriority priority){
        super(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command), alarm, priority);
        this.command = command;
    }

    public TaskCommand(JavaPlugin plugin, String command, ClockAlarm alarm){
        this(plugin, command, alarm, TaskPriority.NORMAL);
    }

    public String toStringConfig(){
        return (priority != null && priority != TaskPriority.NORMAL ? " \"@" + WordUtils.capitalize(priority.toString()) : "") +
                " \"" + alarm.toString() + "\" \"" + command + "\" ";
    }

}
