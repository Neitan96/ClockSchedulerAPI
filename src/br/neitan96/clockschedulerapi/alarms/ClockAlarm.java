package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

/**
 * Created by Neitan96 on 14/07/2016.
 */
public interface ClockAlarm{

    /**
     * Calcula o próximo alarme a partir do milissegundos especificado.
     * Se o milissegundos do alarme for igual o milissegundos especificado
     * esse será o próximo alarme.
     *
     * @param miliseconds Milissegundos para calcular o próximo alarme.
     * @return Milissegundos do próximo alarme.
     */
    long nextAfter(long miliseconds);

    /**
     * Verifica se o milissegundos especificado tem algum alarme para despertar.
     *
     * @param miliseconds Milissegundos para verificar.
     * @return Se está no tempo de despertar.
     */
    default boolean checkTime(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setSecond(59);
        miliseconds = calendar.getTimeInMillis();

        calendar.setSecond(0);
        return miliseconds >= nextAfter(calendar.getTimeInMillis());
    }

    /**
     * @return O próximo despertar.
     */
    default long next(){
        return nextAfter(new ClockCalendar().getTimeInMillis());
    }

}
