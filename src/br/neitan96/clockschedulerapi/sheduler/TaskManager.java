package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.util.ClockDebug;
import br.neitan96.clockschedulerapi.util.DebugFlags;
import br.neitan96.clockschedulerapi.util.Util;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by Neitan96 on 25/07/2016.
 */
public class TaskManager{

    protected static final Comparator<ClockTask> COMPARATOR = (task1, task2) -> {
        if(task1 == task2) return 0;
        if(task1.getNextExecution() > task2.getNextExecution())
            return -1;
        return 1;
    };

    protected final TreeSet<ClockTask> tasks = new TreeSet<>(COMPARATOR);
    protected final TaskExecutorDefault executor = new TaskExecutorDefault(this::start);

    protected long nextExecution = -1;
    protected boolean enabled = false;

    public Set<ClockTask> getTasks(){
        return Collections.unmodifiableSet(tasks);
    }

    public ClockTask getNextTask(){
        return executor.getCurrentTask();
    }

    public long getNextExecution(){
        return nextExecution;
    }

    public boolean running(){
        return enabled && nextExecution >= 0;
    }


    public boolean addTask(ClockTask task){
        if(tasks.add(task)){
            ClockDebug.log(DebugFlags.TASK_ADDED, "Task Adicionada: " + task.toString());
            if(enabled && (nextExecution < 0 || task.getNextExecution() < getNextExecution()))
                setNextTask(task);
            return true;
        }
        return false;
    }

    public boolean removeTask(ClockTask task){
        if(tasks.remove(task)){
            ClockDebug.log(DebugFlags.TASK_REMOVED, "Task removida: " + task.toString());
            if(running() && task == getNextTask())
                start();
            return true;
        }
        return false;
    }

    public synchronized void removeAll(){
        stop();
        tasks.clear();
        ClockDebug.log(DebugFlags.TASK_REMOVED, "Todas tasks removidas");
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


    private synchronized ClockTask findfirst(){
        ClockTask task = null;
        for(ClockTask clockTask : tasks){
            if(clockTask.enabled() &&
                    (task == null || clockTask.getNextExecution() < task.getNextExecution()))
                task = clockTask;
        }
        return task;
    }

    private void setNextTask(ClockTask task){
        if(task != executor.getCurrentTask()){
            executor.executeNext(task);
            nextExecution = task.getNextExecution();
            ClockDebug.log(DebugFlags.MANAGER_NEXT_EXECUTION,
                    "Proxima task a será executada daqui a " +
                            Util.getIntervalNow(task.getNextExecution())
            );
        }
    }

    public synchronized void stop(){
        ClockDebug.log(DebugFlags.MANAGER_STOPPING, "Parando gerenciador de tasks: " + hashCode());
        executor.stop();
        nextExecution = -1;
        enabled = false;
    }

    public synchronized void start(){
        if(nextExecution < 1){
            ClockDebug.log(DebugFlags.MANAGER_STARTING, "Iniciando gerenciador de tasks");
            tasks.forEach(ClockTask::reset);
        }

        ClockTask task = findfirst();

        if(task != null){
            if(task.getNextExecution() < getNextExecution())
                setNextTask(task);
        }else{
            nextExecution = -1;
            ClockDebug.log(DebugFlags.MANAGER_NONE_TASK, "Nenhuma task a ser executada");
        }
    }

}
