package br.neitan96.clockschedulerapi.util;

/**
 * Created by Neitan96 on 07/09/15.
 */
public class Util {

    public static int getWeek(String week){
        switch (week.toLowerCase()){

            case "domingo":
                return 1;

            case "segunda":
                return 2;

            case "segunda-feira":
                return 2;

            case "terca":
                return 3;

            case "terca-feira":
                return 3;

            case "quarta":
                return 4;

            case "quarta-feira":
                return 4;

            case "quinta":
                return 5;

            case "quinta-feira":
                return 5;

            case "sexta":
                return 6;

            case "sexta-feira":
                return 6;

            case "sabado":
                return 7;

            default:
                return -1;

        }
    }

    public static String getWeek(int week){
        switch (week) {

            case 1:
                return "Domingo";

            case 2:
                return "Segunda-feira";

            case 3:
                return "Terca-feira";

            case 4:
                return "Quarta-Feira";

            case 5:
                return "Quinta-feira";

            case 6:
                return "Sexta-feira";

            case 7:
                return "Sabado";

            default:
                return "Domingo";
        }
    }


    public static int[] getTime(String time){

        String[] args = time.split(":");

        int hour;
        int minute;

        try{
            hour = Integer.parseInt(args[0]);
            minute = Integer.parseInt(args[1]);
        }catch (Exception e){
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
        switch (month.toLowerCase()){
            case "janeiro":
                return 0;
            case "fervereiro":
                return 1;
            case "marco":
                return 2;
            case "abril":
                return 3;
            case "maio":
                return 4;
            case "junho":
                return 5;
            case "julho":
                return 6;
            case "agosto":
                return 7;
            case "setembro":
                return 8;
            case "outubro":
                return 9;
            case "novembro":
                return 10;
            case "dezembro":
                return 11;
            default:
                return -1;
        }
    }

    public static String getMonth(int month){
        switch (month){
            case 0:
                return "Janeiro";
            case 1:
                return "Fervereiro";
            case 2:
                return "Marco";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7:
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
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
        }catch (Exception e){
            return null;
        }

        if(day < 1 || day > 31 || month < 1 || month > 12 || year < 1000 || year > 9999)
            return null;

        return new int[]{day, month-1, year};
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
