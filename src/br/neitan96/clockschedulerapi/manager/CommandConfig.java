package br.neitan96.clockschedulerapi.manager;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.alarms.ClockAlarm;
import br.neitan96.clockschedulerapi.util.Util;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Neitan96 on 10/09/15.
 */
public class CommandConfig extends YamlConfiguration{

    public static String path = "Comandos";

    protected File file;

    protected List<ClockAlarm> alarms = new ArrayList<>();

    public int getSizeCommand(){
        return getStringList(path).size();
    }

    public List<String> getCommands(){
        return getStringList(path);
    }

    public void removeIndex(int index){
        List<String> stringList = getStringList(path);
        stringList.remove(index);
        set(path, stringList);
        save();
    }

    public CommandConfig(File file) {
        this.file = file;
        try {
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            super.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAlarm(ClockAlarm alarm, String command){

        List<String> comandos = getStringList(path);

        comandos.add(Util.alarmCommandClassToString(alarm, command));

        set(path, comandos);

        save();
    }

    public void addAlarmAndRegister(ClockAlarm alarm, final String command){
        addAlarm(alarm, command);
        alarms.add(alarm);
        ClockSchedulerAPI.addAlarm(alarm, command, ClockSchedulerAPI.getInstance());
    }

    public void desativarAlarms(){
        for(ClockAlarm alarm : alarms)
            alarm.cancelAlarm();
        alarms.clear();
    }

    public void registerAll(){
        for (String alarmCommand : getStringList(path)){

            Map.Entry<String, String> alarmEntry = Util.alarmCommandStringToClass(alarmCommand);

            if(alarmEntry == null){
                ClockSchedulerAPI.log("Uma configuração inválida foi achada: " + alarmCommand);
                continue;
            }

            ClockAlarm alarm = ClockAlarm.getFromString(alarmEntry.getKey());

            if(alarm == null){
                ClockSchedulerAPI.log("Um alarme inválido foi achado na configuração: " + alarmEntry.getKey());
                continue;
            }

            alarms.add(alarm);

            ClockSchedulerAPI.addAlarm(alarm, alarmEntry.getValue(), ClockSchedulerAPI.getInstance());

        }
    }

}
