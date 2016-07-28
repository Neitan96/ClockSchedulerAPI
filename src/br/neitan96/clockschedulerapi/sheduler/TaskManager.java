package br.neitan96.clockschedulerapi.sheduler;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Neitan96 on 25/07/2016.
 */
public class TaskManager{

    protected final TreeSet<AlarmTask> tasks = new TreeSet<>();
    protected final AlarmExecutor executor = new AlarmExecutorDefault(this::update);

    public void update(){
        AlarmTask next = tasks.stream().
                filter(AlarmTask::enabled).findFirst().orElse(null);

        if(next != null) executor.executeNext(next);
    }

    public Set<AlarmTask> getTasks(){
        return Collections.unmodifiableSet(tasks);
    }

    public void addTask(AlarmTask task){
        tasks.add(task);
        update();
    }

    public void removeTask(AlarmTask task){
        tasks.remove(task);
        update();
    }

    public synchronized void removeAll(JavaPlugin plugin){
        this.tasks.stream()
                .filter(s -> s.plugin == plugin)
                .forEach(this::removeTask);
    }

    public void stop(){
        executor.stop();
    }

    public void start(){
        update();
    }

}
