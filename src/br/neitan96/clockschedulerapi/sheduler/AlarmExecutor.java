package br.neitan96.clockschedulerapi.sheduler;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public interface AlarmExecutor{

    default void executeNext(AlarmTask task){
        long next = task.alarm.nextAfter(task.getLastExecution() + 1);
        executeNext(task, next);
    }

    void executeNext(AlarmTask task, long next);

    void stop();

}
