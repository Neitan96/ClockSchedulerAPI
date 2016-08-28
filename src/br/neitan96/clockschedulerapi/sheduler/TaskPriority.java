package br.neitan96.clockschedulerapi.sheduler;

/**
 * Created by Neitan96 on 28/08/2016.
 */
public enum TaskPriority{

    LOWEST(0),
    LOW(1),
    NORMAL(2),
    HIGH(3),
    HIGHEST(4);

    public static TaskPriority fromString(String priority){
        try{
            return TaskPriority.valueOf(priority.toUpperCase());
        }catch(IllegalArgumentException e){
            return null;
        }
    }

    private final int order;

    TaskPriority(int slot){
        this.order = slot;
    }

    public int getOrder(){
        return this.order;
    }

    @Override
    public String toString(){
        String priority = super.toString();
        return priority.substring(0, 1).toUpperCase() + priority.substring(1).toLowerCase();
    }

}
