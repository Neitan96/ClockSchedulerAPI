package br.neitan96.clockschedulerapi.commands.debug;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
import br.neitan96.clockschedulerapi.util.ClockCalendar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Neitan96 on 12/08/2016.
 */
public class CTestStress implements CommandExecutor{

    protected ClockTask tasks[] = null;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(tasks != null){

            for(ClockTask task : tasks){
                if(task != null){
                    task.disable();
                    ClockSchedulerAPI.removeTask(task);
                }
            }

            tasks = null;

            ClockSchedulerAPI.log(commandSender, "Off testing.");

        }else{
            int qntd = 100;
            int minTime = 15;
            int maxTime = 360;

            ClockCalendar calendar = new ClockCalendar();
            String pathToSave = String.format("%d-%02d-%02d-%02d:%02d:%02d",
                    calendar.getYear(), calendar.getMonth(), calendar.getDay(),
                    calendar.getHour(), calendar.getMinute(), calendar.getSecond());

            if(strings.length > 0){
                if(strings[0].matches("[^0-9]")){
                    ClockSchedulerAPI.log(commandSender, "Command invalid.");
                    return true;
                }
                qntd = Integer.parseInt(strings[0]);
            }

            if(strings.length > 1){
                if(strings[1].matches("[^0-9]")){
                    ClockSchedulerAPI.log(commandSender, "Command invalid.");
                    return true;
                }
                maxTime = Integer.parseInt(strings[1]);
                minTime = maxTime <= minTime ? maxTime - 1 : minTime;
            }

            tasks = new ClockTask[qntd];

            for(int i = 0; i < qntd; i++){
                ClockSchedulerAPI.addTask(
                        tasks[i] = new AlarmTest(
                                AlarmTest.getRandomAlarm(minTime, maxTime), pathToSave
                        ).getTask()
                );
            }

            ClockSchedulerAPI.log(commandSender, "Added tests");
        }

        return true;
    }
}
