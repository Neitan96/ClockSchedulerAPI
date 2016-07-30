package br.neitan96.clockschedulerapi;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.alarms.converters.AlarmConvetors;
import br.neitan96.clockschedulerapi.commands.*;
import br.neitan96.clockschedulerapi.config.TasksConfig;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.sheduler.TaskCommand;
import br.neitan96.clockschedulerapi.sheduler.TaskManager;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockDebug;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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
    public void onEnable() {

        clockSchedulerAPI = this;
        taskManager = new TaskManager();

        saveIfNotExists("comandos.yml");
        saveIfNotExists("exemplos.yml");
        saveIfNotExists("config.yml");

        reloadConfig();

        TAG = "[" + getDescription().getName() + "] ";

        List<String> debugTagsList = getConfig().getStringList("Debug");
        if(debugTagsList != null){
            log("Definindo o debug...");
            String[] debugTags = debugTagsList.toArray(new String[debugTagsList.size()]);
            ClockDebug.setTags(debugTags);
        }


        log("Definindo fuso horário...");
        ClockCalendar.defaultTimeZone = TimeZone.getTimeZone(
                getConfig().getString("FusoHorario.TimeZone", "America/Sao_Paulo")
        );
        ClockCalendar.ajusteDays = getConfig().getInt("FusoHorario.Ajuste.Dias", 0);
        ClockCalendar.ajusteHours = getConfig().getInt("FusoHorario.Ajuste.Horas", 0);
        ClockCalendar.ajusteMinutes = getConfig().getInt("FusoHorario.Ajuste.Minutos", 0);
        ClockCalendar.ajusteSeconds = getConfig().getInt("FusoHorario.Ajuste.Segundos", 0);

        log("Registrando comandos...");
        CTempComando tempCommand = new CTempComando();
        getCommand("clockhoracerta").setExecutor(new CHoraCerta());
        getCommand("clockteste").setExecutor(new CTeste());
        getCommand("clocktempcomando").setExecutor(tempCommand);
        getCommand("clockpermcomando").setExecutor(new CPermComando());
        getCommand("clockdesativar").setExecutor(new CDesativar());
        getCommand("clockremover").setExecutor(new CRemover());
        getCommand("clockcomandos").setExecutor(new CComandos());
        getCommand("clockdesativarperms").setExecutor(new CDesativarPerms());
        getCommand("clockreativarperms").setExecutor(new CReativarPerms());
        getCommand("clockdesativartemps").setExecutor(new CDesativarTemps(tempCommand));
        getCommand("clockdebuglista").setExecutor(new CDebugLista());
        getCommand("clocklimparcache").setExecutor(new CLimparCache());
        getCommand("clockreload").setExecutor(new CReload());

        log("Registrando alarmes dos comandos...");
        File configFile = new File(getDataFolder(), "comandos.yml");
        tasksConfig = new TasksConfig(configFile);

        log("ClockSchedulerAPI iniciado!");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        log("ClockSchedulerAPI terminado!");
    }


    public void saveIfNotExists(String file){
        File configFile = new File(getDataFolder(), file);
        if(!configFile.exists())
            saveResource(file, false);
    }


    public static ClockSchedulerAPI getInstance() {
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

    public static void addTask(ClockAlarm alarm, Runnable runnable, JavaPlugin plugin){
        ClockTask task = new ClockTask(plugin, runnable, alarm);
        addTask(task);
    }

    public static void addTask(ClockAlarm alarm, String command, JavaPlugin plugin){
        ClockTask task = new TaskCommand(plugin, command, alarm);
        addTask(task);
    }

    public static void addTask(String alarm, Runnable runnable, JavaPlugin plugin){
        addTask(AlarmConvetors.convert(alarm), runnable, plugin);
    }


    public static void removeTask(ClockTask task){
        getTaskManager().removeTask(task);
    }

    public static void removeTasksPlugin(JavaPlugin plugin){
        getTaskManager().removeAll(plugin);
    }


    public static void log(String msg){
        Bukkit.getConsoleSender().sendMessage(COLOR_TAG + TAG + COLOR_TEXT + msg);
    }

    public static void log(CommandSender sender, String msg){
        sender.sendMessage(COLOR_TAG + TAG + COLOR_TEXT + msg);
    }

}
