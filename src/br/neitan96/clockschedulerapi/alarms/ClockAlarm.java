package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

/**
 * Created by Neitan96 on 14/07/2016.
 */
public interface ClockAlarm extends Comparable<ClockAlarm>{

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
     * @return O próximo despertar.
     */
    default long next(){
        return nextAfter(new ClockCalendar().getTimeInMillis());
    }

    @Override
    default int compareTo(ClockAlarm alarm){
        return Float.compare(next(), alarm.next());
    }

}
