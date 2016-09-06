package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.util.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Neitan96 on 25/07/2016.
 */
public class TaskManager{

    protected final OptimizedList<ClockTask> tasks = new OptimizedList<>(new ClockTaskComparator());
    protected final TaskExecutor executor = new TaskExecutorDefault(this);

    protected boolean enabled = false;

    public List<ClockTask> getTasks(){
        return Collections.unmodifiableList(tasks);
    }

    public ClockTask getNextTask(){
        return executor.getCurrentTask();
    }

    public long getNextExecution(){
        return getNextTask() != null ? getNextTask().getNextExecution() : -1;
    }

    public boolean running(){
        return enabled && getNextExecution() >= 0;
    }


    public void updateItem(ClockTask task){
        tasks.updateItem(task);
        start();
    }

    public boolean addTask(ClockTask task){
        if(tasks.add(task)){
            ClockDebug.log(DebugFlags.TASK_ADDED, "Task Adicionada: " + task.toString());
            start();
            return true;
        }
        return false;
    }

    public boolean removeTask(ClockTask task){
        if(tasks.remove(task)){
            ClockDebug.log(DebugFlags.TASK_REMOVED, "Task removida: " + task.toString());
            if(enabled && task == getNextTask())
                start();
            return true;
        }
        return false;
    }

    public synchronized void removeAll(){
        stop();
        tasks.clear();
        ClockDebug.log(DebugFlags.TASKS_REMOVED, "Todas tasks removidas");
    }

    public synchronized void removeAll(JavaPlugin plugin){
        HashSet<ClockTask> tasks = new HashSet<>();
        this.tasks.stream()
                .filter(s -> s.plugin == plugin)
                .forEach(tasks::add);
        tasks.forEach(this::removeTask);
    }

    public synchronized void removeDisableds(){
        ClockDebug.log(DebugFlags.MANAGER_REMOVING_DISABLED, "Removendo tasks desativadas: " + hashCode());
        Set<ClockTask> tasksDisabled = new HashSet<>();
        tasks.stream()
                .filter(t -> !t.enabled())
                .forEach(tasksDisabled::add);
        tasksDisabled.forEach(this::removeTask);
    }


    private void setNextTask(ClockTask task){
        if(task != getNextTask()){
            executor.executeNext(task);
            if(ClockCalendar.getClockMilisecond() - task.getNextExecution() > 60 * 1000)
                ClockDebug.log(DebugFlags.MANAGER_NEXT_EXECUTION,
                        "Proxima task a será executada daqui a " +
                                Util.getIntervalNow(task.getNextExecution())
                );
        }
    }

    public synchronized void stop(){
        ClockDebug.log(DebugFlags.MANAGER_STOPPING, "Parando gerenciador de tasks: " + hashCode());
        executor.stop();
        enabled = false;
    }

    public synchronized void start(){
        if(!enabled){
            ClockDebug.log(DebugFlags.MANAGER_STARTING, "Iniciando gerenciador de tasks: " + hashCode());
            enabled = true;
            tasks.forEach(ClockTask::reset);
        }

        ClockTask task = tasks.size() > 0 ? tasks.get(0) : null;

        if(task != null && task.enabled()){
            if(getNextTask() == null || !getNextTask().enabled() || task.getNextExecution() < getNextExecution())
                setNextTask(task);
        }else{
            ClockDebug.log(DebugFlags.MANAGER_NONE_TASK, "Nenhuma task a ser executada");
        }
    }

}
