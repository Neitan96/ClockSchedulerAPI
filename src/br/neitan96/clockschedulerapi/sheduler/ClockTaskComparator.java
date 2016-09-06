package br.neitan96.clockschedulerapi.sheduler;

import java.util.Comparator;

/**
 * Created by Neitan96 on 06/09/2016.
 */
public class ClockTaskComparator implements Comparator<ClockTask>{
    @Override
    public int compare(ClockTask task1, ClockTask task2){
        if(!task1.enabled() && !task2.enabled()) return 0;
        if(!task1.enabled()) return 1;
        if(!task2.enabled()) return -1;

        if(task1.getNextExecution() == task2.getNextExecution())
            return Integer.compare(task2.priority.getOrder(), task1.priority.getOrder());
        else if(task1.getNextExecution() > task2.getNextExecution())
            return 1;
        else
            return -1;

    }
}
