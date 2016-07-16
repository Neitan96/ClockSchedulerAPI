package br.neitan96.clockschedulerapi.alarms.convertors;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public interface ClockAlarmConvertor{

    /**
     * Converte uma string em alarm.
     *
     * @param alarm String para ser convertida.
     * @return Alarm instanciado.
     */
    ClockAlarm getAlarm(String alarm);

}
