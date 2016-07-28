package br.neitan96.clockschedulerapi.sheduler;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public interface SchedulerExecutor{

    default void executeNext(SchedulerTask task){
        long next = task.alarm.nextAfter(task.getLastExecution() + 1);
        executeNext(task, next);
    }

    void executeNext(SchedulerTask task, long next);

    void stop();

}
