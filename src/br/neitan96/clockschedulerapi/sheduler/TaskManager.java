package br.neitan96.clockschedulerapi.sheduler;

import br.neitan96.clockschedulerapi.util.ClockDebug;
import br.neitan96.clockschedulerapi.util.Util;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created by Neitan96 on 25/07/2016.
 */
public class TaskManager{

    protected static final Comparator<ClockTask> COMPARATOR = (task1, task2) -> {
        if(task1.getNextExecution() > task2.getNextExecution())
            return 1;
        return -1;
    };

    protected final TreeSet<ClockTask> tasks = new TreeSet<>(COMPARATOR);
    protected final TaskExecutorDefault executor = new TaskExecutorDefault(this::start);

    protected long nextExecution = -1;

    public Set<ClockTask> getTasks(){
        return Collections.unmodifiableSet(tasks);
    }

    public long getNextExecution(){
        return nextExecution;
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
        stop();
        tasks.clear();
        ClockDebug.log(ClockDebug.TASK_REMOVED, "Todas tasks removidas");
    }

    public synchronized void removeAll(JavaPlugin plugin){
        this.tasks.stream()
                .filter(s -> s.plugin == plugin)
                .forEach(this::removeTask);
        start();
    }

    public synchronized void removeDisableds(){
        ClockDebug.log(ClockDebug.MANAGER_REMOVING_DISABLED, "Removendo tasks desativadas: " + toString());
        Set<ClockTask> tasksDisabled = new HashSet<>();
        tasks.stream().filter(ClockTask::enabled).forEach(tasksDisabled::add);
        tasksDisabled.forEach(this::removeTask);
    }

    public void stop(){
        ClockDebug.log(ClockDebug.MANAGER_STOPPING, "Parando gerenciador de tasks: " + toString());
        nextExecution = -1;
        executor.stop();
    }

    public void start(){
        if(nextExecution < 1)
            ClockDebug.log(ClockDebug.MANAGER_STARTING, "Iniciando gerenciador de tasks");

        ClockTask task = tasks.stream()
                .filter(ClockTask::enabled).sorted(COMPARATOR).findFirst().orElse(null);

        if(task != null){
            if(task != executor.getCurrentTask() || task.getNextExecution() != getNextExecution()){

                executor.executeNext(task);
                nextExecution = task.getNextExecution();
                ClockDebug.log(ClockDebug.MANAGER_NEXT_EXECUTION,
                        "Proxima task a será executada daqui a " +
                                Util.getIntervalNow(task.getNextExecution())
                );

            }
        }else{
            ClockDebug.log(ClockDebug.MANAGER_NONE_TASK, "Nenhuma task a ser executada");
            stop();
        }
    }

}
