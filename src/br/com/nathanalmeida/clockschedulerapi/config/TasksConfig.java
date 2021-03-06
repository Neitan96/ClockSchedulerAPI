package br.com.nathanalmeida.clockschedulerapi.config;

import br.com.nathanalmeida.clockschedulerapi.ClockSchedulerAPI;
import br.com.nathanalmeida.clockschedulerapi.sheduler.TaskCommand;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Neitan96 on 26/07/2016.
 */
public class TasksConfig{

    protected final String path;
    protected final File file;
    protected final YamlConfigurationUTF8 configuration = new YamlConfigurationUTF8();
    protected final List<TaskCommand> taskCommands = new ArrayList<>();

    public TasksConfig(String path, File file){
        this.path = path;
        this.file = file;
        load();
    }

    public TasksConfig(File file){
        this("Comandos", file);
    }

    public void load(){
        try{
            configuration.load(file);
        }catch(IOException | InvalidConfigurationException e){
            e.printStackTrace();
            return;
        }

        for(String alarmCommandStr : configuration.getStringList(path)){

            TaskCommand taskCommand = TaskCommand.fromString(
                    ClockSchedulerAPI.getInstance(), alarmCommandStr);

            taskCommands.add(taskCommand);
        }
    }

    protected void exportAlarmsToConfig(){
        ArrayList<String> alarmsString = taskCommands.stream()
                .map(TaskCommand::toStringConfig)
                .collect(Collectors.toCollection(ArrayList::new));
        configuration.set(path, alarmsString);
    }

    public List<TaskCommand> getTasks(){
        return Collections.unmodifiableList(taskCommands);
    }

    public void add(TaskCommand taskCommand){
        if(!taskCommands.contains(taskCommand)){
            taskCommands.add(taskCommand);
            exportAlarmsToConfig();
        }
    }

    public void remove(int index){
        if(index >= 0 && index < taskCommands.size()){
            taskCommands.remove(index);
            exportAlarmsToConfig();
        }
    }

    public void remove(TaskCommand taskCommand){
        if(taskCommands.contains(taskCommand)){
            taskCommands.remove(taskCommand);
            exportAlarmsToConfig();
        }
    }

    public void save() throws IOException{
        configuration.save(file);
    }

    public void save(File file) throws IOException{
        configuration.save(file);
    }

    public void save(OutputStream stream) throws IOException{
        configuration.save(stream);
    }

    public String saveToString(){
        return configuration.saveToString();
    }

}
