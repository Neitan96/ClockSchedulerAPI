package br.com.nathanalmeida.clockschedulerapi.commands.debug;

import br.com.nathanalmeida.clockschedulerapi.alarms.ClockAlarm;
import br.com.nathanalmeida.clockschedulerapi.util.ClockCalendar;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Neitan96 on 06/09/2016.
 */
public class TestResult{

    public final ClockAlarm alarm;
    public final long initialized;
    public final long executed;
    public final long expected;

    public TestResult(ClockAlarm alarm, long initialized, long executed, long expected){
        this.alarm = alarm;
        this.initialized = initialized;
        this.executed = executed;
        this.expected = expected;
    }

    public void saveIn(ConfigurationSection section){
        ClockCalendar initialized = new ClockCalendar(this.initialized);
        ClockCalendar executed = new ClockCalendar(this.executed);
        ClockCalendar expected = new ClockCalendar(this.expected);

        String path = String.format("%d-%02d-%02d-%02d:%02d:%02d",
                initialized.getYear(), initialized.getMonth(), initialized.getDay(),
                initialized.getHour(), initialized.getMinute(), initialized.getSecond());

        String nowStr = executed.toString(true);
        String expectedStr = expected.toString(true);
        long diff = executed.getTimeInMillis() - expected.getTimeInMillis();
        path += expectedStr.equals(nowStr) ? ".Sucess" : ".Fail";

        int count = 1;
        if(section.contains(path))
            count = section.getConfigurationSection(path).getKeys(false).size() + 1;

        path += ".Num-" + count;

        section.set(path + ".Alarm", alarm.toString());
        section.set(path + ".Now", nowStr);
        section.set(path + ".Expected", expectedStr);
        section.set(path + ".Diff", diff);
    }

}
