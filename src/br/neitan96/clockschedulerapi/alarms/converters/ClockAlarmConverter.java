package br.neitan96.clockschedulerapi.alarms.converters;

import br.neitan96.clockschedulerapi.alarms.ClockAlarm;

/**
 * Created by Neitan96 on 15/07/2016.
 */
public interface ClockAlarmConverter{

    /**
     * Converte uma string em alarm.
     *
     * @param alarm String para ser convertida.
     * @return Alarm instanciado.
     */
    ClockAlarm getAlarm(String alarm);

    /**
     * @return Retorna os apelidos do alarm.
     */
    String[] getLabels();

}
