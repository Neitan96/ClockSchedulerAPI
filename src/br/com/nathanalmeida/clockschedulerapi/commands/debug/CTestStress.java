package br.com.nathanalmeida.clockschedulerapi.commands.debug;

import br.com.nathanalmeida.clockschedulerapi.ClockSchedulerAPI;
import br.com.nathanalmeida.clockschedulerapi.sheduler.ClockTask;
import br.com.nathanalmeida.clockschedulerapi.util.ClockCalendar;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
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

            ClockSchedulerAPI.getInstance().saveTests();
            ClockLang.COMMANDS_OFFTESTING.sendTo(commandSender);

        }else{
            int qntd = 100;
            int minTime = 15;
            int maxTime = 360;

            long initialized = ClockCalendar.getClockMilisecond();

            if(strings.length > 0){
                if(strings[0].matches("[^0-9]")){
                    ClockLang.COMMANDS_INVALIDCOMMAND.sendTo(commandSender);
                    return true;
                }
                qntd = Integer.parseInt(strings[0]);
            }

            if(strings.length > 1){
                if(strings[1].matches("[^0-9]")){
                    ClockLang.COMMANDS_INVALIDCOMMAND.sendTo(commandSender);
                    return true;
                }
                maxTime = Integer.parseInt(strings[1]);
                minTime = maxTime <= minTime ? maxTime - 1 : minTime;
            }

            tasks = new ClockTask[qntd];

            for(int i = 0; i < qntd; i++){
                ClockSchedulerAPI.addTask(
                        tasks[i] = new AlarmTest(
                                AlarmTest.getRandomAlarm(minTime, maxTime), initialized
                        ).getTask()
                );
            }

            ClockLang.COMMANDS_ADDEDTESTS.sendTo(commandSender);
        }

        return true;
    }
}
