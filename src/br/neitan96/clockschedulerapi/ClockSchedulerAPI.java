package br.neitan96.clockschedulerapi;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.commands.*;
import br.neitan96.clockschedulerapi.manager.*;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.TimeZone;

/**
 * Created by Neitan96 on 07/09/15.
 */
public class ClockSchedulerAPI extends JavaPlugin{

    private static CommandConfig commandConfig= null;
    private static ClockSchedulerAPI clockSchedulerAPI = null;
    private static AlarmsManager alarmsManager = null;

    private static ChatColor colorTag = ChatColor.DARK_BLUE;
    private static ChatColor colorText = ChatColor.DARK_GREEN;
    private static String tag = null;

    private static int debug = 0;

    @Override
    public void onEnable() {

        clockSchedulerAPI = this;

        saveIfNotExists("comandos.yml");
        saveIfNotExists("exemplos.yml");
        saveIfNotExists("config.yml");

        tag = "["+getDescription().getName()+"] ";

        debug = getConfig().getInt("Debug", 0);

        log("Iniciando gerenciador...");
        if(getConfig().getBoolean("Performance.TempoReal"))
            alarmsManager = new AlarmsManagerReal();
        else
            alarmsManager = new AlarmsManagerInterval(
                    getConfig().getLong("Performance.IntervaloDespertador", 50)
            );


        log("Definindo fuso horário...");
        ClockCalendar.defaultTimeZone = TimeZone.getTimeZone(
                getConfig().getString("FusoHorario.TimeZone", "America/Sao_Paulo")
        );
        ClockCalendar.ajusteDays = getConfig().getInt("FusoHorario.Ajuste.Dias", 0);
        ClockCalendar.ajusteHours = getConfig().getInt("FusoHorario.Ajuste.Horas", 0);
        ClockCalendar.ajusteMinutes = getConfig().getInt("FusoHorario.Ajuste.Minutos", 0);
        ClockCalendar.ajusteSeconds = getConfig().getInt("FusoHorario.Ajuste.Segundos", 0);


        log("Registrando tipos de alarmes...");
        ClockAlarm.registerAllAlarms();


        log("Registrando comandos...");
        CTempComando tempCommand;
        getCommand("clockhoracerta").setExecutor(new CHoraCerta());
        getCommand("clockteste").setExecutor(new CTeste());
        getCommand("clocktempcomando").setExecutor((tempCommand = new CTempComando()));
        getCommand("clockpermcomando").setExecutor(new CPermComando());
        getCommand("clockdesativar").setExecutor(new CDesativar());
        getCommand("clockremover").setExecutor(new CRemover());
        getCommand("clockcomandos").setExecutor(new CComandos());
        getCommand("clockdesativarperms").setExecutor(new CDesativarPerms());
        getCommand("clockreativarperms").setExecutor(new CReativarPerms());
        getCommand("clockdesativartemps").setExecutor(new CDesativarTemps(tempCommand));
        getCommand("clockdebuglista").setExecutor(new CDebugLista());
        getCommand("clocklimparcache").setExecutor(new CLimparCache());

        log("Registrando alarmes dos comandos...");
        File configFile = new File(getDataFolder(), "comandos.yml");
        commandConfig = new CommandConfig(configFile);
        commandConfig.registerAll();

        log("ClockSchedulerAPI iniciado!");
    }

    @Override
    public void onDisable() {
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
        return ClockAlarm.getFromString(alarm);
    }


    public static AlarmsManager getAlarmsManager() {
        return alarmsManager;
    }

    public static CommandConfig getCommandConfig() {
        return commandConfig;
    }


    public static void addAlarm(ClockAlarm alarm, Runnable runnable, JavaPlugin plugin){
        alarmsManager.addScheduler(alarm, runnable, plugin);
    }

    public static void addAlarm(AlarmScheduler alarmScheduler){
        alarmsManager.addScheduler(alarmScheduler);
    }

    public static ClockAlarm addAlarm(String alarm, Runnable runnable, JavaPlugin plugin){
        ClockAlarm clockAlarm = ClockAlarm.getFromString(alarm);
        addAlarm(clockAlarm, runnable, plugin);
        return clockAlarm;
    }

    public static void addAlarm(ClockAlarm alarm, String command, JavaPlugin plugin){
        final String commandconsole = Util.removeBar(command);
        Runnable run = () -> Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                commandconsole
        );
        getAlarmsManager().addScheduler(alarm, run, plugin);
    }


    public static synchronized void removeAlarmsFromPlugin(JavaPlugin plugin){
        getAlarmsManager().removeAllPlugin(plugin);
    }

    public static void log(String msg){
        Bukkit.getConsoleSender().sendMessage(colorTag + tag + colorText + msg);
    }

    public static void log(CommandSender sender, String msg){
        sender.sendMessage(colorTag + tag + colorText + msg);
    }


    public static boolean debug(){
        return debug > 0;
    }

    public static void debug(String msg, int lv){
        if(debug == lv)
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"[ClockDebug] "+ChatColor.DARK_GREEN+msg);
    }
}
