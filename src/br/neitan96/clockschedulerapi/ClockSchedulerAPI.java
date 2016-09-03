package br.neitan96.clockschedulerapi;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.alarms.converters.AlarmConvetors;
import br.neitan96.clockschedulerapi.commands.CReload;
import br.neitan96.clockschedulerapi.commands.debug.*;
import br.neitan96.clockschedulerapi.commands.tasks.*;
import br.neitan96.clockschedulerapi.commands.time.CSetTime;
import br.neitan96.clockschedulerapi.commands.time.CSetTimezone;
import br.neitan96.clockschedulerapi.commands.time.CTime;
import br.neitan96.clockschedulerapi.commands.tmptaks.CTmpTaskAdd;
import br.neitan96.clockschedulerapi.commands.tmptaks.CTmpTaskRemove;
import br.neitan96.clockschedulerapi.commands.tmptaks.CTmpTasks;
import br.neitan96.clockschedulerapi.config.TasksConfig;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.sheduler.TaskCommand;
import br.neitan96.clockschedulerapi.sheduler.TaskManager;
import br.neitan96.clockschedulerapi.util.AdjustmentTime;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockDebug;
import br.neitan96.clockschedulerapi.util.ClockLang;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Neitan96 on 07/09/15.
 */
public class ClockSchedulerAPI extends JavaPlugin{

    private static ClockSchedulerAPI clockSchedulerAPI = null;
    private static TasksConfig tasksConfig = null;
    private static TaskManager taskManager = null;

    private static final ChatColor COLOR_TAG = ChatColor.DARK_BLUE;
    private static final ChatColor COLOR_TEXT = ChatColor.DARK_GREEN;
    private static String TAG = null;


    @Override
    public void onEnable(){

        ClockLang.TAG = getDescription().getName();

        clockSchedulerAPI = this;
        taskManager = new TaskManager();
        taskManager.start();

        saveIfNotExists("comandos.yml");
        saveIfNotExists("exemplos.yml");
        saveIfNotExists("config.yml");
        saveIfNotExists("lang/pt-br.yml");

        AlarmTest.setFileTest(new File(getDataFolder(), "Tests.yml"));

        reloadConfig(true);

        ClockLang.SYSTEM_REGISTERINGCOMMANDS.sendToConsole();
        getCommand("clockstatus").setExecutor(new CStatus());
        getCommand("clocktime").setExecutor(new CTime());
        getCommand("clocksettime").setExecutor(new CSetTime());
        getCommand("clocksettimezone").setExecutor(new CSetTimezone());
        getCommand("clockdebug").setExecutor(new CDebug());
        getCommand("clocktest").setExecutor(new CTest());
        getCommand("clockteststress").setExecutor(new CTestStress());

        CTmpTasks cTmpTasks = new CTmpTasks();
        getCommand("clocktmptasks").setExecutor(cTmpTasks);
        getCommand("clocktmptasksadd").setExecutor(new CTmpTaskAdd(cTmpTasks));
        getCommand("clocktmptasksremove").setExecutor(new CTmpTaskRemove(cTmpTasks));

        CTasks cTasks = new CTasks();
        getCommand("clocktasks").setExecutor(cTasks);
        getCommand("clocktasksadd").setExecutor(new CTasksAdd());
        getCommand("clocktasksremove").setExecutor(new CTasksRemove(cTasks));
        getCommand("clocktasksdisable").setExecutor(new CTasksDisable(cTasks));
        getCommand("clocktasksenable").setExecutor(new CTasksEnable(cTasks));

        CTasksDisableAll cTasksDisableAll = new CTasksDisableAll();
        getCommand("clocktasksdisableall").setExecutor(cTasksDisableAll);
        getCommand("clocktasksenablell").setExecutor(new CTasksEnableAll(cTasksDisableAll));
        getCommand("clockreload").setExecutor(new CReload());

        ClockLang.SYSTEM_PLUGINENABLED.sendToConsole();
    }

    @Override
    public void onDisable(){
        taskManager.removeAll();
        HandlerList.unregisterAll(this);
        ClockLang.SYSTEM_PLUGINDISABLED.sendToConsole();
    }

    @Override
    public void reloadConfig(){
        reloadConfig(false);
    }

    @Override
    public void saveConfig(){
        saveDebugFlagsToConfig();
        saveCalendarToConfig();
        saveTasksToConfig();
    }


    public synchronized void reloadConfig(boolean log){
        super.reloadConfig();

        ClockLang.loadConfig(new File(getDataFolder(), "lang/" + getConfig().getString("Language", "pt-br") + ".yml"));

        List<String> debugTagsList = getConfig().getStringList("Debug");
        if(debugTagsList != null){
            if(log) ClockLang.SYSTEM_SETTINGDEBUG.sendToConsole();
            String[] debugTags = debugTagsList.toArray(new String[debugTagsList.size()]);
            ClockDebug.setTags(debugTags);
        }

        if(log) ClockLang.SYSTEM_SETTINGTIMEZONE.sendToConsole();
        ClockCalendar.defaultTimeZone = TimeZone.getTimeZone(
                getConfig().getString("FusoHorario.TimeZone", "America/Sao_Paulo")
        );
        ClockCalendar.adjustment.addMiliseconds(
                AdjustmentTime.getMilliseconds(getConfig().getString("FusoHorario.Ajuste", ""))
        );

        if(log) ClockLang.SYSTEM_REGISTERINGCOMMANDS.sendToConsole();
        if(tasksConfig != null && taskManager != null)
            tasksConfig.getTasks().forEach(ClockTask::disable);
        File configFile = new File(getDataFolder(), "comandos.yml");
        tasksConfig = new TasksConfig(configFile);
        tasksConfig.getTasks().forEach(ClockSchedulerAPI::addTask);

    }

    public void saveDebugFlagsToConfig(){
        String[] debugTags = ClockDebug.getDebugTags();
        getConfig().set("Debug", debugTags);
        super.saveConfig();
    }

    public void saveCalendarToConfig(){
        if(ClockCalendar.defaultTimeZone != null)
            getConfig().set("FusoHorario.TimeZone", ClockCalendar.defaultTimeZone.getID());
        else
            getConfig().set("FusoHorario.TimeZone", null);

        getConfig().set("FusoHorario.Ajuste", ClockCalendar.adjustment.toString());

        super.saveConfig();
    }

    public void saveTasksToConfig(){
        if(tasksConfig != null){
            try{

                tasksConfig.save();
            }catch(IOException e){
                e.printStackTrace();
                ClockLang.SYSTEM_ERRORSAVINGCONFIG.sendToConsole();
            }
        }

        super.saveConfig();
    }


    public void saveIfNotExists(String file){
        File configFile = new File(getDataFolder(), file);
        if(!configFile.exists())
            saveResource(file, false);
    }


    public static ClockSchedulerAPI getInstance(){
        return clockSchedulerAPI;
    }


    public static ClockAlarm getAlarm(String alarm){
        return AlarmConvetors.convert(alarm);
    }


    public static TaskManager getTaskManager(){
        return taskManager;
    }

    public static TasksConfig getTasksConfig(){
        return tasksConfig;
    }


    public static void addTask(ClockTask task){
        taskManager.addTask(task);
    }

    public static ClockTask addTask(ClockAlarm alarm, Runnable runnable, JavaPlugin plugin){
        ClockTask task = new ClockTask(plugin, runnable, alarm);
        addTask(task);
        return task;
    }

    public static ClockTask addTask(ClockAlarm alarm, String command, JavaPlugin plugin){
        ClockTask task = new TaskCommand(plugin, command, alarm);
        addTask(task);
        return task;
    }

    public static ClockTask addTask(String alarm, Runnable runnable, JavaPlugin plugin){
        return addTask(AlarmConvetors.convert(alarm), runnable, plugin);
    }


    public static void removeTask(ClockTask task){
        getTaskManager().removeTask(task);
    }

    public static void removeTasksPlugin(JavaPlugin plugin){
        getTaskManager().removeAll(plugin);
    }

}
