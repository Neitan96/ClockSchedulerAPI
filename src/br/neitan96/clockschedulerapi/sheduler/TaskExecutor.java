package br.neitan96.clockschedulerapi.sheduler;

/**
 * Created by Neitan96 on 16/07/2016.
 */
public interface TaskExecutor{

    void executeNext(ClockTask task);

    ClockTask getCurrentTask();

    boolean running();

    void stop();

}
