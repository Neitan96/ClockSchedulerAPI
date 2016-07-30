package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockDebug;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by Neitan96 on 25/07/2016.
 */
public class TaskManager{

    protected final TreeSet<ClockTask> tasks = new TreeSet<>();
    protected final TaskExecutorDefault executor = new TaskExecutorDefault(this::start);

    public Set<ClockTask> getTasks(){
        return Collections.unmodifiableSet(tasks);
    }

    public void addTask(ClockTask task){
        tasks.add(task);
        ClockDebug.log(ClockDebug.TASK_ADDED, "Task Adicionada: " + task.toString());
        start();
    }

    public void removeTask(ClockTask task){
        tasks.remove(task);
        ClockDebug.log(ClockDebug.TASK_REMOVED, "Task removida: " + task.toString());
        start();
    }

    public synchronized void removeAll(){
        tasks.clear();
    }

    public synchronized void removeAll(JavaPlugin plugin){
        this.tasks.stream()
                .filter(s -> s.plugin == plugin)
                .forEach(this::removeTask);
    }

    public synchronized void removeDisableds(){
        ClockDebug.log(ClockDebug.MANAGER_REMOVING_DISABLED, "Removendo tasks desativadas: " + toString());
        Set<ClockTask> tasksDisabled = new HashSet<>();
        tasks.stream().filter(ClockTask::enabled).forEach(tasksDisabled::add);
        tasksDisabled.forEach(this::removeTask);
    }

    public void stop(){
        ClockDebug.log(ClockDebug.MANAGER_STOPPING, "Parando gerenciador de tasks: " + toString());
        executor.stop();
    }

    public void start(){
        if(!executor.running())
            ClockDebug.log(ClockDebug.MANAGER_STARTING, "Iniciando gerenciador de tasks");

        Optional<ClockTask> next = tasks.stream().
                filter(ClockTask::enabled).findFirst();

        if(next.isPresent()){
            ClockTask task = next.get();
            if(executor.getClockTask() != task){
                ClockDebug.log(ClockDebug.MANAGER_NEXT_EXECUTION,
                        "Proxima task a ser executada é: " + task.toString());
                ClockDebug.log(ClockDebug.MANAGER_NEXT_EXECUTION,
                        "Proxima task a sera executada em: " + new ClockCalendar(task.getNextExection()).toString(true));
                executor.executeNext(task);
            }
        }else{
            ClockDebug.log(ClockDebug.MANAGER_NONE_TASK, "Nenhuma task a ser executada");
            stop();
        }
    }

}
