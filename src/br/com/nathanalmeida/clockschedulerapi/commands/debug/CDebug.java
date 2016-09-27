package br.com.nathanalmeida.clockschedulerapi.commands.debug;

import br.com.nathanalmeida.clockschedulerapi.ClockSchedulerAPI;
import br.com.nathanalmeida.clockschedulerapi.util.ClockDebug;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Neitan96 on 03/08/2016.
 */
public class CDebug implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){

        if(strings.length < 1){
            ClockLang.COMMANDS_INVALIDCOMMAND.sendTo(commandSender);
            return true;
        }

        List<String> debugTags = Arrays.stream(ClockDebug.getDebugTags()).collect(Collectors.toList());

        args:
        for(String flagArg : strings){
            for(int i = 0; i < debugTags.size(); i++){
                String flagBug = debugTags.get(i);
                if(flagArg.equalsIgnoreCase(flagBug)){
                    debugTags.remove(i);
                    continue args;
                }
            }
            debugTags.add(flagArg);
        }

        ClockDebug.setTags(debugTags.toArray(new String[debugTags.size()]));
        ClockSchedulerAPI.getInstance().saveDebugFlagsToConfig();

        ClockLang.COMMANDS_DEBUGCHANGED.sendTo(commandSender);

        return true;
    }
}
