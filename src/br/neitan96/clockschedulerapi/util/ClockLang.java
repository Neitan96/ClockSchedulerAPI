package br.neitan96.clockschedulerapi.util;

import br.neitan96.clockschedulerapi.config.YamlConfigurationUTF8;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neitan96 on 18/08/2016.
 */
public enum ClockLang{

    CUSTOM("Messages.Custom"),

    ALARM_DAILY("Messages.Alarms.AlarmDaily"),
    ALARM_DATE("Messages.Alarms.AlarmDate"),
    ALARM_HOUR("Messages.Alarms.AlarmHour"),
    ALARM_INTERVAL("Messages.Alarms.AlarmInterval"),
    ALARM_MONTHLY("Messages.Alarms.AlarmMonthly"),
    ALARM_MONTHLYWEEK("Messages.Alarms.AlarmMonthlyWeek"),
    ALARM_WEEKLY("Messages.Alarms.AlarmWeekly"),
    ALARM_YEARLY("Messages.Alarms.AlarmYearly"),
    ALARM_MULTI("Messages.Alarms.AlarmMulti"),
    ALARM_BETWEEN("Messages.Alarms.AlarmBetween"),

    SYSTEM_REGISTERINGCOMMANDS("Messages.System.RegisteringCommands"),
    SYSTEM_PLUGINENABLED("Messages.System.PluginEnabled"),
    SYSTEM_PLUGINDISABLED("Messages.System.PluginDisabled"),
    SYSTEM_SETTINGDEBUG("Messages.System.SettingDebug"),
    SYSTEM_SETTINGTIMEZONE("Messages.System.SettingTimeZone"),
    SYSTEM_SETTINGALARMS("Messages.System.SettingAlarms"),
    SYSTEM_ERRORSAVINGCONFIG("Messages.System.ErrorSavingConfig"),

    COMMANDS_INVALIDCOMMAND("Messages.Commands.InvalidCommand"),
    COMMANDS_DEBUGCHANGED("Messages.Commands.DebugChanged"),
    COMMANDS_OFFTESTING("Messages.Commands.OffTesting"),
    COMMANDS_INVALIDSECONDS("Messages.Commands.InvalidSeconds"),
    COMMANDS_PERFORMTASKS("Messages.Commands.PerformTasks"),
    COMMANDS_ADDEDTESTS("Messages.Commands.AddedTests"),
    COMMANDS_TASKLINE("Messages.Commands.TaskLine"),
    COMMANDS_NOTASK("Messages.Commands.NoTask"),
    COMMANDS_TASKADDED("Messages.Commands.TaskAdded"),
    COMMANDS_DISABLEDTASK("Messages.Commands.DisabledTask"),
    COMMANDS_DISABLEDTASKS("Messages.Commands.DisabledTasks"),
    COMMANDS_INVALIDPLUGIN("Messages.Commands.InvalidPlugin"),
    COMMANDS_INVALIDCOMMANDUSECLOCKTASKS("Messages.Commands.InvalidCommandUseClockTasks"),
    COMMANDS_ENABLEDTASK("Messages.Commands.EnabledTask"),
    COMMANDS_ENABLEDTASKS("Messages.Commands.EnabledTasks"),
    COMMANDS_REMOVEDTASK("Messages.Commands.RemovedTask"),
    COMMANDS_TIMENOTCHANGED("Messages.Commands.TimeNotChanged"),
    COMMANDS_CURRENTSETTING("Messages.Commands.CurrentSetting"),
    COMMANDS_TIME("Messages.Commands.Time"),
    COMMANDS_CURRENTTIMEZONE("Messages.Commands.CurrentTimeZone"),
    COMMANDS_INVALIDTIMEZONE("Messages.Commands.InvalidTimeZone"),
    COMMANDS_CHANGEDTIMEZONE("Messages.Commands.ChangedTimeZone"),
    COMMANDS_PLUGINRELOAD("Messages.Commands.PluginReload"),
    COMMANDS_PLUGINDISABLING("Messages.Commands.PluginDisabling"),
    COMMANDS_PLUGINENABLING("Messages.Commands.PluginEnabling"),
    COMMANDS_STATUS_MANAGER("Messages.Commands.Status.Manager"),
    COMMANDS_STATUS_TOTALTASKS("Messages.Commands.Status.TotalTasks"),
    COMMANDS_STATUS_ACTIVETASKS("Messages.Commands.Status.ActiveTasks"),
    COMMANDS_STATUS_DISABLEDTASKS("Messages.Commands.Status.DisabledTasks"),
    COMMANDS_STATUS_NEXTTASK("Messages.Commands.Status.NextTask"),
    COMMANDS_STATUS_USINGPLUGINS("Messages.Commands.Status.UsingPlugins"),
    COMMANDS_STATUS_PLUGINS("Messages.Commands.Status.Plugins"),

    DEBUG_TASKRESTARTED("Messages.Debug.TaskRestarted"),
    DEBUG_TASKENABLED("Messages.Debug.TaskEnabled"),
    DEBUG_TASKDISABLED("Messages.Debug.TaskDisabled"),
    DEBUG_TASKRUNNING("Messages.Debug.TaskRunning"),
    DEBUG_TASKERROREXECUTE("Messages.Debug.TaskErrorExecute"),
    DEBUG_TASKADDED("Messages.Debug.TaskAdded"),
    DEBUG_TASKREMOVED("Messages.Debug.TaskRemoved"),
    DEBUG_TASKSREMOVED("Messages.Debug.TasksRemoved"),
    DEBUG_MANAGERREMOVINGDISABLEDTASKS("Messages.Debug.ManagerRemovingDisabledTasks"),
    DEBUG_MANAGERNEXTEXECUTION("Messages.Debug.ManagerNextExecution"),
    DEBUG_MANAGERSTOPPING("Messages.Debug.ManagerStopping"),
    DEBUG_MANAGERSTARTING("Messages.Debug.ManagerStarting"),
    DEBUG_MANAGERNONETASK("Messages.Debug.ManagerNoneTask");

    public static Map<String, String[]> messages = new HashMap<>();
    public static String TAG = null;
    private static final ChatColor COLOR_TAG = ChatColor.DARK_BLUE;
    private static final ChatColor COLOR_TEXT = ChatColor.DARK_GREEN;

    public static void loadConfig(YamlConfiguration config){
        for(ClockLang lang : ClockLang.values()){
            String path = lang.getPath();
            if(config.isList(path)){
                List<String> list = config.getStringList(path);
                messages.put(path, list.toArray(new String[list.size()]));
            }else if(config.isString(path)){
                messages.put(path, new String[]{config.getString(path)});
            }else{
                messages.remove(path);
            }
        }
    }

    public static void loadConfig(File file){
        try{
            YamlConfigurationUTF8 configuration = new YamlConfigurationUTF8();
            configuration.load(file);
            loadConfig(configuration);
        }catch(IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    private final String path;

    ClockLang(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public String[] getMessage(){
        if(messages.containsKey(path))
            return messages.get(path);
        else
            return new String[0];
    }

    public void sendTo(CommandSender sender, Object... binds){
        for(String line : getMessage()){

            for(int i = 1; i < binds.length; i += 2)
                if(binds[i - 1] != null)
                    line = line.replace("{" + binds[i - 1].toString() + "}",
                            binds[i] != null ? binds[i].toString() : "NULL");

            sender.sendMessage(
                    (TAG != null ? COLOR_TAG + "[" + TAG + "] " : "") + COLOR_TEXT + line
            );
        }
    }

    public void sendToConsole(){
        sendTo(Bukkit.getConsoleSender());
    }

}
