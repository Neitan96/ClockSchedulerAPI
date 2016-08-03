package br.neitan96.clockschedulerapi.util;

/**
 * Created by Neitan96 on 03/08/2016.
 */
public enum DebugFlags{

    ALL("All"),
    OTHERS("Others"),

    TASK_ADDED("Task added"),
    TASK_REMOVED("Task removed"),
    TASK_ENABLED("Task enabled"),
    TASK_DISABLED("Task disabled"),
    TASK_RUNNING("Task running"),
    TASK_RESTARTED("Task Restarted"),
    TASK_ERROR_EXECUTE("Task error execute"),

    MANAGER_STARTING("Manager starting"),
    MANAGER_STOPPING("Manager stopping"),
    MANAGER_NEXT_EXECUTION("Manager next execution"),
    MANAGER_NONE_TASK("Manager none task"),
    MANAGER_REMOVING_DISABLED("Manager removing disabled tasks");

    public final String flagName;

    DebugFlags(String flagName){
        this.flagName = flagName;
    }

    @Override
    public String toString(){
        return flagName;
    }

}
