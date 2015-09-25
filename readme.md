#ClockSchedulerAPI

##O plugin

ClockSchedulerAPI é um plugin e API para outros desenvolvedores, ele cria schedulers com base na data e hora, podendo colocar schedules para repetir todo dia, toda semana, todo mes e etc, ele tambem pode ser usado inividualmente.

##As config

####**config.yml**

#####FusoHorario
* TimeZone
> Time é o fuso horario em que o plugin vai fucionar.

* Ajuste
> Se o TimeZone não funcionar você pode ajusta a hora, adicionando horas, minutos e etc.

#####Performance
* IntervaloDespertador
> Esse é o intervalo de segundos que o plugin vai verificar os alarmes.
Nessa configuração a cada 50 segundos o plugin vai verificar se algum alarmes está pronto para despertar.

#####Debug
> Se deseja ativar o debug.
> 
* 0 para desativar
* 1 Para mostra somente quando um alarme é adicionado
* 2 Para mostra quando um alarme é adicionado e quando a verificação é feita

####**comandos.yml**

######Comandos
* Esses são os comandos programados para despertar de acordo com o alarme programado.

##Legendas

####Dias da semana:
* Domingo
* Segunda
* Segunda-feira
* Terca
* Terca-feira
* Quarta
* Quarta-Feira
* Quinta
* Quinta-feira
* Sexta
* Sexta-feira
* Sabado

####Meses:
* Janeiro
* Fervereiro
* Marco
* Abril
* Maio
* Junho
* Julho
* Agosto
* Setembro
* Outubro
* Novembro
* Dezembro

##Os exemplos
* Horario|30 - Toda hora no minuto 30
* Diario|12:00 - Todo dia as 12 horas e 0 minutos
* Semanal|Domingo 13:05 - Todo domingo as 13 horas e 5 minutos
* Mensal|2 Domingo 14:10 - Todo segundo domingo do mes as 14 horas e 10 minutos
* Mensal|20 15:15 - Todo mes no dia 20 as 15 horas e 15 minutos
* Anual|22 Setembro 16:20 - Todo ano dia 22 de Setembro as 16 horas e 20 minutos
* Data|22/09/2015 17:25 - (Sem repetição) dia 22 de setembro de 2015 as 17 horas e 25 minutos

##Os comandos

* **/clockhoracerta:**
> Esse comando mostra a data e hora de acordo com o plugin.

* **/clockteste:**
> Esse comando cria alguns alarmes que desperta depois de 1 minuto para testar o plugin.

* **/clocktempcomando:**
> Esse comando adicionar um comando para ser executado de acordo com o alarme.
O alarme dura somente enquanto o servidor estiver ligado, quando o servidor desligar o alarme é desativado.
Exemplo: 
/clocktempcomando "Diario\|15:30" "say Ola pessoal do servidor, agora são 15 horas e 30 minutos"

* **/clockpermcomando:**
> Esse comando é igual o comando a cima (clocktempcomando), a diferença que ele salva o alarme na config e reativa ele quando o servidor é religado.

* **/clockdesativar:**
> Esse comando desativa todos os alarmes ativos atualmente no plugin.

* **/clockremover:**
> Esse comando remove um comando da config de alarmes de acordo com a index dele.
OBS: esse comando não desativa o alarme do comando.
Modo de usar: /clockremover INDEX_DO_ALARME

* **/clockcomandos:**
> Mostra a lista de todos os comandos da config junto ao index do comando.

* **/clockdesativarperms:**
> Esse comando desativa somentes os comandos da config.

* **/clockreativarperms:**
> Esse comando reativa todos os comandos da config.
OBS: se você usar esse comando e os comandos da config estiverem ativos, vai duplicar os comandos.

* **/clockdesativartemps:**
> Esse comando Desativa todos os alarmes de comandos temporarios.

* **/clockdebuglista:**
> Esse comando mostra todos os alarmes ativados atualmente.

* **/clocklimparcache:**
> Esse comando limpa o cache do plugin.
Para entender melhor:
No plugin existe uma lista de alarmes, quando um alarme da lista é desativado ele continua na lista ate da a hora dele despertar(mas não desperta), então ele é removido da lista, com esse comando ele pecorre todos os alarmes da lista e remove os alarmes desativados.

##A api

####Criando um alarme:
* AlarmHour:
> Esse alarme desperta toda hora ao x minuto.
**ClockAlarm alarm = new AlarmHour(MINUTO);**
Exemplo:
*ClockAlarm alarm = new AlarmHour(30);*
Esse alarme vai desperta toda hora nos 30 minutos.

* AlarmDaily:
> Esse alarme desperta todo dia as x hora e x minuto.
**ClockAlarm alarm = new AlarmDaily(HORA, MINUTO);**
Exemplo:
*ClockAlarm alarm = new AlarmDaily(10,30);*
Esse alarme vai desperta todo dia as 10 horas e 30 minutos.

* AlarmWeekly:
> Esse alarme desperta toda semana as x hora e x minuto.
**ClockAlarm alarm = new AlarmWeekly(DIA_DA_SEMANA, HORA, MINUTO);**
Exemplo:
*ClockAlarm alarm = new AlarmWeekly("Domingo", 11, 30);*
Esse alarme vai desperta toda semana no domingo as 10 horas e 30 minutos.

* AlarmMothly:
> Esse alarme desperta todo mês no dia x as x hora e x minuto.
**ClockAlarm alarm = new AlarmMothly(DIA, HORA, MINUTO);**
Exemplo:
*ClockAlarm alarm = new AlarmMothly(20, 12, 30);*
Esse alarme vai desperta todo mês no dia 20 as 12 horas e 30 minutos.

* AlarmYearly:
> Esse alarme desperta todo ano no mês x no dia x as x hora e x minuto.
**ClockAlarm alarm = new AlarmYearly(DIA, MES, HORA, MINUTO);**
Exemplo:
*ClockAlarm alarm = new AlarmYearly(25, "Dezembro", 0, 0);*
Esse alarme vai desperta todo ano no mês de dezembro no dia 25 as 0 horas e 0 minuto.

* AlarmDate:
> Esse alarme desperta numa data fixa, e não repete.
**ClockAlarm alarm = new AlarmDate(ANO, MES, DIA, HORA, MINUTO);**
Exemplo:
*ClockAlarm alarm = new AlarmDate(2015, "Setembro", 22, 15, 0);*
Esse alarme vai desperta no ano de 2015 no mês setembro no dia 22 as 15 horas e 0 minuto.

* Por string:
> Se você quiser criar um alarme a parti de uma string como nos exemplos a cima.
**ClockAlarm alarm = ClockSchedulerAPI.getAlarm(STRING);**
Exemplo:
*ClockAlarm alarm = ClockSchedulerAPI.getAlarm("Anual\|22 Setembro 15:00");*
Recomendado para quando você precisar tirar algum alarme da config de seu plugin ou qualquer outra coisa.


####Registrando um alarme:

Para adicionar um alarme para digitar o seguinte codigo:
>**ClockSchedulerAPI.addAlarm(ALARME, RUNNABLE, PLUGIN);**

 * ALARME
   Esse é o alarme criado no topico anterior.

 * RUNNABLE
   Esse é o Runnable que vai ser executado quando o alarme despertar.

 * PLUGIN
   Esse é o seu plugin.
 
#####*Ou você pode adicionar direto de um string*:
>**ClockAlarm alarm = ClockSchedulerAPI.addAlarm(STRING, RUNNABLE, PLUGIN);**

 * STRING
   Essa é a string do alarme, como nos exemplos a cima.

 * RUNNABLE
   Esse é o Runnable que vai ser executado quando o alarme despertar.

 * PLUGIN
   Esse é o seu plugin.

####Cancelando um alarme
* Basta você usa a função *cancelAlarm* do seu alarme, exemplo:
>**ClockAlarm alarm = ClockSchedulerAPI.addAlarm(STRING, RUNNABLE);**
>**alarm.cancelAlarm();**

* Ou você pode remover todos os alarmes do seu plugin, exemplo:
>**ClockSchedulerAPI.removeAlarmsFromPlugin(PLUGIN);**