package br.neitan96.clockschedulerapi.commands.debug;

import br.neitan96.clockschedulerapi.ClockSchedulerAPI;
import br.neitan96.clockschedulerapi.sheduler.ClockTask;
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
                task.disable();
                ClockSchedulerAPI.removeTask(task);
            }

            tasks = null;

            ClockSchedulerAPI.log(commandSender, "Off testing.");

        }else{
            int qntd = 100;

            if(strings.length > 0){
                if(strings[0].matches("[^0-9]")){
                    ClockSchedulerAPI.log(commandSender, "Command invalid.");
                    return true;
                }
                qntd = Integer.parseInt(strings[0]);
            }

            tasks = new ClockTask[qntd];

            for(int i = 0; i < qntd; i++){
                ClockTask randomTask = CTestUtil.getRandomTask();
                tasks[i] = randomTask;
                ClockSchedulerAPI.addTask(randomTask);
            }

            ClockSchedulerAPI.log(commandSender, "Added tests");
        }

        return true;
    }
}
