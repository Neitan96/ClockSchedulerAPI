package br.neitan96.clockschedulerapi.util;

/**
 * Created by Neitan96 on 07/09/15.
 */
public class Util{

    public static int getWeek(String week){
        week = week.toLowerCase();
        if(ClockLang.DAYSOFTHEWEEK_SUNDAY.contains(week)) return 1;
        if(ClockLang.DAYSOFTHEWEEK_MONDAY.contains(week)) return 2;
        if(ClockLang.DAYSOFTHEWEEK_TUESDAY.contains(week)) return 3;
        if(ClockLang.DAYSOFTHEWEEK_WEDNESDAY.contains(week)) return 4;
        if(ClockLang.DAYSOFTHEWEEK_THURSDAY.contains(week)) return 5;
        if(ClockLang.DAYSOFTHEWEEK_FRIDAY.contains(week)) return 6;
        if(ClockLang.DAYSOFTHEWEEK_SATURDAY.contains(week)) return 7;
        return -1;
    }

    public static String getWeek(int week){
        switch(week){
            case 1:
                return ClockLang.DAYSOFTHEWEEK_SUNDAY.getMessage()[0];
            case 2:
                return ClockLang.DAYSOFTHEWEEK_MONDAY.getMessage()[0];
            case 3:
                return ClockLang.DAYSOFTHEWEEK_TUESDAY.getMessage()[0];
            case 4:
                return ClockLang.DAYSOFTHEWEEK_WEDNESDAY.getMessage()[0];
            case 5:
                return ClockLang.DAYSOFTHEWEEK_THURSDAY.getMessage()[0];
            case 6:
                return ClockLang.DAYSOFTHEWEEK_FRIDAY.getMessage()[0];
            case 7:
                return ClockLang.DAYSOFTHEWEEK_SATURDAY.getMessage()[0];
            default:
                return null;
        }
    }


    public static int[] getTime(String time){

        String[] args = time.split(":");

        int hour;
        int minute;

        try{
            hour = Integer.parseInt(args[0]);
            minute = Integer.parseInt(args[1]);
        }catch(Exception e){
            return null;
        }

        if(hour > 23 || hour < 0 || minute > 59 || minute < 0)
            return null;

        return new int[]{hour, minute};
    }

    public static String getTime(int hour, int minute){
        return String.format("%02d:%02d", hour, minute);
    }


    public static int getMonth(String month){
        if(ClockLang.MONTHS_JANUARY.contains(month)) return 0;
        if(ClockLang.MONTHS_FEBRUARY.contains(month)) return 1;
        if(ClockLang.MONTHS_MARCH.contains(month)) return 2;
        if(ClockLang.MONTHS_APRIL.contains(month)) return 3;
        if(ClockLang.MONTHS_MAY.contains(month)) return 4;
        if(ClockLang.MONTHS_JUNE.contains(month)) return 5;
        if(ClockLang.MONTHS_JULY.contains(month)) return 6;
        if(ClockLang.MONTHS_AUGUST.contains(month)) return 7;
        if(ClockLang.MONTHS_SEPTEMBER.contains(month)) return 8;
        if(ClockLang.MONTHS_OCTOBER.contains(month)) return 9;
        if(ClockLang.MONTHS_NOVEMBER.contains(month)) return 10;
        if(ClockLang.MONTHS_DECEMBER.contains(month)) return 11;
        return -1;
    }

    public static String getMonth(int month){
        switch(month){
            case 0:
                return ClockLang.MONTHS_JANUARY.getMessage()[0];
            case 1:
                return ClockLang.MONTHS_FEBRUARY.getMessage()[0];
            case 2:
                return ClockLang.MONTHS_MARCH.getMessage()[0];
            case 3:
                return ClockLang.MONTHS_APRIL.getMessage()[0];
            case 4:
                return ClockLang.MONTHS_MAY.getMessage()[0];
            case 5:
                return ClockLang.MONTHS_JUNE.getMessage()[0];
            case 6:
                return ClockLang.MONTHS_JULY.getMessage()[0];
            case 7:
                return ClockLang.MONTHS_AUGUST.getMessage()[0];
            case 8:
                return ClockLang.MONTHS_SEPTEMBER.getMessage()[0];
            case 9:
                return ClockLang.MONTHS_OCTOBER.getMessage()[0];
            case 10:
                return ClockLang.MONTHS_NOVEMBER.getMessage()[0];
            case 11:
                return ClockLang.MONTHS_DECEMBER.getMessage()[0];
            default:
                return null;
        }
    }


    public static int[] getDate(String date){
        String[] args = date.split("/");
        if(args.length != 3)
            return null;

        int day;
        int month;
        int year;

        try{
            day = Integer.parseInt(args[0]);
            month = Integer.parseInt(args[1]);
            year = Integer.parseInt(args[2]);
        }catch(Exception e){
            return null;
        }

        if(day < 1 || day > 31 || month < 1 || month > 12 || year < 1000 || year > 9999)
            return null;

        return new int[]{day, month - 1, year};
    }

    public static String getDate(int day, int month, int year){
        return String.format("%02d/%02d/%d", day, month + 1, year);
    }


    public static String getInterval(long milisecond){
        String negativeFix = "";
        if(milisecond < 0){
            negativeFix = "-";
            milisecond = Math.abs(milisecond);
        }

        long second = milisecond / 1000;
        long minute = 0;
        long hour = 0;
        long day = 0;

        while(second > 59){
            second -= 60;
            minute++;
        }
        while(minute > 59){
            minute -= 60;
            hour++;
        }
        while(hour > 23){
            hour -= 24;
            day++;
        }

        String interval = negativeFix + second + "s";
        interval = (minute > 0 ? negativeFix + minute + "m " : "") + interval;
        interval = (hour > 0 ? negativeFix + hour + "h " : "") + interval;
        interval = (day > 0 ? negativeFix + day + "d " : "") + interval;

        return interval;
    }

    public static String getIntervalNow(long milisecond){
        return getInterval(milisecond - ClockCalendar.getClockMilisecond());
    }

    public static String getDifferenceNow(long milisecond){
        return getInterval(ClockCalendar.getClockMilisecond() - milisecond);
    }

}
