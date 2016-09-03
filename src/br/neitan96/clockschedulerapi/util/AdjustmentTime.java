package br.neitan96.clockschedulerapi.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neitan96 on 31/08/2016.
 */
public class AdjustmentTime{

    public static final int DAY_IN_MILLISECOND = 86400000;
    public static final int HOUR_IN_MILLISECOND = 3600000;
    public static final int MINUTE_IN_MILLISECOND = 60000;
    public static final int SECOND_IN_MILLISECOND = 1000;


    public static long getMilliseconds(String[] fromString){
        Pattern pattern = Pattern.compile("([\\-+]?[0-9]+)([dhms])");

        long miliseconds = 0;

        for(String aFromString : fromString){
            if(aFromString.isEmpty()) continue;
            Matcher matcher = pattern.matcher(aFromString.trim());
            if(!matcher.matches())
                throw new IllegalArgumentException("Invalid argument: " + aFromString);

            int number = Integer.parseInt(matcher.group(1));

            switch(matcher.group(2)){
                case "D":
                case "d":
                    miliseconds += number * DAY_IN_MILLISECOND;
                    break;
                case "H":
                case "h":
                    miliseconds += number * HOUR_IN_MILLISECOND;
                    break;
                case "M":
                case "m":
                    miliseconds += number * MINUTE_IN_MILLISECOND;
                    break;
                case "S":
                case "s":
                    miliseconds += number * SECOND_IN_MILLISECOND;
                    break;
            }
        }

        return miliseconds;
    }

    public static long getMilliseconds(String fromString){
        return getMilliseconds(fromString.split(" "));
    }

    protected long adjustmentMilisecond = 0;


    public AdjustmentTime(long adjustmentMilisecond){
        this.adjustmentMilisecond = adjustmentMilisecond;
    }

    public AdjustmentTime(String... fromString){
        adjustmentMilisecond = getMilliseconds(fromString);
    }

    public AdjustmentTime(String fromString){
        adjustmentMilisecond = getMilliseconds(fromString);
    }


    public long getInMiliseconds(){
        return adjustmentMilisecond;
    }

    public long getInSeconds(){
        return adjustmentMilisecond / SECOND_IN_MILLISECOND;
    }

    public long getInMinutes(){
        return adjustmentMilisecond / MINUTE_IN_MILLISECOND;
    }

    public long getInHours(){
        return adjustmentMilisecond / HOUR_IN_MILLISECOND;
    }

    public long getInDays(){
        return adjustmentMilisecond / DAY_IN_MILLISECOND;
    }


    private long[] count(){
        long milisecond = this.adjustmentMilisecond;
        long day = 0;
        long hour = 0;
        long minute = 0;
        long second = 0;

        while(milisecond >= DAY_IN_MILLISECOND){
            day++;
            milisecond -= DAY_IN_MILLISECOND;
        }

        while(milisecond >= HOUR_IN_MILLISECOND){
            hour++;
            milisecond -= HOUR_IN_MILLISECOND;
        }

        while(milisecond >= MINUTE_IN_MILLISECOND){
            minute++;
            milisecond -= MINUTE_IN_MILLISECOND;
        }

        while(milisecond >= SECOND_IN_MILLISECOND){
            second++;
            milisecond -= SECOND_IN_MILLISECOND;
        }

        return new long[]{milisecond, second, minute, hour, day};
    }


    public long getMilliseconds(){
        return count()[0];
    }

    public long getSeconds(){
        return count()[1];
    }

    public long getMinutes(){
        return count()[2];
    }

    public long getHours(){
        return count()[3];
    }

    public long getDays(){
        return count()[4];
    }


    public void addMiliseconds(long milliseconds){
        this.adjustmentMilisecond += milliseconds;
    }

    public void addSeconds(long seconds){
        this.adjustmentMilisecond += SECOND_IN_MILLISECOND * seconds;
    }

    public void addMinutes(long minutes){
        this.adjustmentMilisecond += MINUTE_IN_MILLISECOND * minutes;
    }

    public void addHours(long hours){
        this.adjustmentMilisecond += HOUR_IN_MILLISECOND * hours;
    }

    public void addDays(long days){
        this.adjustmentMilisecond += DAY_IN_MILLISECOND * days;
    }


    public void adjust(Calendar calendar){
        if(adjustmentMilisecond != 0){
            long adjustmentMiliseconds = this.adjustmentMilisecond;
            while(adjustmentMiliseconds > Integer.MAX_VALUE){
                calendar.add(Calendar.MILLISECOND, Integer.MAX_VALUE);
                adjustmentMiliseconds -= Integer.MAX_VALUE;
            }
            calendar.add(Calendar.MILLISECOND, (int) adjustmentMiliseconds);
        }
    }


    @Override
    public String toString(){
        long[] count = count();
        ArrayList<String> toString = new ArrayList<>(count.length);

        if(count[4] != 0)
            toString.add(count[4] + "d");
        if(count[3] != 0)
            toString.add(count[3] + "h");
        if(count[2] != 0)
            toString.add(count[2] + "m");
        if(count[1] != 0)
            toString.add(count[1] + "s");

        return toString.size() > 0 ? String.join(" ", toString) : "No adjustment";
    }

}
