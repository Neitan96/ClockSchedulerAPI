# ClockSchedulerAPI

## O plugin

ClockSchedulerAPI é um plugin e API para outros desenvolvedores, ele cria schedulers com base na data e hora, podendo colocar schedules para repetir todo dia, toda semana, todo mes e etc, ele tambem pode ser usado inividualmente.

## Nomenclatura
 - Alarm ou alarme - Refere somente o que vai dizer quando algo vai ser executado.
 - Task ou tarefa - Abriga o alarme e o que vai ser executado.

 #### Dias da semana:
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

 #### Meses:
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

## Configurações

#### **config.yml**

* **Language**  
Língua do plugin, para ver as línguas disponíveis entre no diretório lang no diretório do plugin.  
Default: pt-br

* **FusoHorario**  
 * **TimeZone**  
Fuso horário em que o plugin vai fucionar.  
Default: America/Sao_Paulo

 * **Ajuste**  
Ajuste de data e hora manualmente.  
Para ajustar coloque o sinal de +(mais) para aumentar ou o sinal de -(menos) para diminuir em seguida o valor a ser alterado e por último o tipo do valor, sendo, D para dias, H para horas, M para minutos e S para segundos, você colocar quantos ajustes quiser separando-os por espaços, assim: [+|-][0-9][d|h|m|s]... .  
Exemplo:  
+3 -2h +30m -3s  
Nesse exemplo adicionamos 3 dias e 30 minutos, diminuirmos 2 horas e 3 segundos.

* **Debug**  
Aqui você as flags dos debugs que deseja exibir no console.
> - All - Exibi todos os debugs.
> - Others - Exibi todos debugs que não tem flag.
> - Task added - Quando uma task é adicionada.
> - Task removed - Quando uma task é removida.
> - Tasks removed - Quando todas tasks são removidas.
> - Task enabled - Quando um task é ativada.
> - Task disabled - Quando uma task é desativada.
> - Task Restarted - Quando um task é resetada.
> - Task running - Quando uma task vai ser executada.
> - Task error execute - Quando ocorre um erro na execução da task.
> - Manager starting - Quando o gerenciador é iniciado.
> - Manager stopping - Quando o gerenciador é desativado.
> - Manager next execution - Próxima task a ser executada.
> - Manager none task - Quando não tem tasks para ser executada.
> - Manager removing disabled tasks - Quando todas tasks desativadas são removidas.
say Ola pessoal

#### **commands.yml**

* **Comandos**  
Esses são os comandos programados para despertar de acordo com o alarme programado.  
Para adicionar um novo comando para ser executado primeiro coloque sua prioridade(opcional), sendo elas na ordem: @Highest, @High, @Normal, @Low e @Lowest, em seguida o [alarme](src/examples.yml) entre "(aspas duplas) e em seguida o comando para ser executado entre "(aspas duplas).  
Exemplo:  
@High "Intervalo|30" "say Ola pessoal"  
Nesse exemplo colocamos a prioridade High com o alarme Intervalo|30 e o comando que vai ser executado é: say Ola pessoal.

## Comandos

* **/clockstatus** - *clockschedulerapi.status*  
Como usar: */clockstatus*  
Ver o status do gerenciador de tasks.

* **/clocktime** - *clockschedulerapi.time*  
Como usar: */clocktime*  
Ver a hora atual do plugin.

* **/clocksettime** - *clockschedulerapi.settime*
Como usar: */clocksettime < true/false > < ajuste >*   
Ajusta a hora do plugin.  
**Cuidado: Ao ajustar a hora em in-game você pode fazer tasks não serem executadas ou serem executadas varias vezes.**  
No primeiro argumento se você colocar verdadeiro fará o plugin recalcular todas as tarefas, quando recalculado todas as tasks programada para serem executadas no horário que foi passado não serão executadas, porém se não forem recalculadas fará todas tasks programadas para serem executadas no horário que foi passado instantaneamente, como se tivesse acelerado o tempo, mas se você atrasou a hora nenhuma task será executada até atingir a hora anterior.   
O ajuste é feito igualmente à config.  
Exemplo:  
/clocksettime true +1d +5h -30m +50s  
O ajuste é somado ao ajuste atual, se o plugin tiver um ajuste de +2d e você executar o comando ajustando +1d ele irá somar e ficar +3d.

* **/clocksettimezone** - *clockschedulerapi.settimezone*  
Como usar: */clocksettimezone < Fuso horário >*   
Defini o fuso horário.

* **/clockdebug** - *clockschedulerapi.debug*  
Como usar: */clocksettimezone [Flags]...*   
Defini as flags do debug, se você colocar um flag que está ativa ela é desativada se ela está desativada ela é ativada.

* **/clocktest** - *clockschedulerapi.test*  
Como usar: */clocktest [Delay para executar os alarmes]*   
Faz um teste com todos os tipos de alarmes.  
Os resultados são gravados no arquivo *Tests.yml* no diretório do plugin.

* **/clockteststress** - *clockschedulerapi.teststress*  
Como usar: */clockteststress [Quantidade de tarefas] [Delay máximo para executar as tarefas]*   
Ver o status do gerenciador de tasks.

* **/clocktmptasks** - *clockschedulerapi.tmptasks*  
Como usar: */clocktmptasks*   
Ver as tarefas temporárias.  
Tarefas temporárias não são armazenadas, elas somem quando o servidor é desligado.  

* **/clocktmptasksadd** - *clockschedulerapi.tmptasksadd*  
Como usar: */clocktmptasksadd "< Alarme >" "< Comando para ser executado >"*   
Adiciona uma tarefa temporária.

* **/clocktmptasksremove** - *clockschedulerapi.tmptasksremove*  
Como usar: */clocktmptasksremove < Index da tarefa >*   
Remove uma tarefa temporária, para ver a index da tarefa use /clocktmptasks.

* **/clocktasks** - *clockschedulerapi.tasks*  
Como usar: */clocktasks*   
Ver todas tarefas incluindo as temporárias.

* **/clocktasksadd** - *clockschedulerapi.tasksadd*  
Como usar: */clocktasksadd "< Alarme >" "< Comando para ser executado >"*   
Adiciona uma nova tarefa, esse comando salva a tarefa nas configurações.

* **/clocktasksremove** - *clockschedulerapi.tasksremove*  
Como usar: */clocktasksremove < Index da tarefa >*   
Remove uma tarefa, para ver a index da tarefa use /clocktasks, caso essa tarefa estiver nas configurações ela será apagada.

* **/clocktasksdisable** - *clockschedulerapi.tasksdisable*  
Como usar: */clocktasksdisable < Index da tarefa >*   
Desativa uma tarefa, para ver a index da tarefa use /clocktasks, caso essa tarefa estiver nas configurações ela não será apagada.

* **/clocktasksenable** - *clockschedulerapi.tasksenable*  
Como usar: */clocktasksenable < Index da tarefa >*   
Ativa uma tarefa, para ver a index da tarefa use /clocktasks..

* **/clocktasksdisableall** - *clockschedulerapi.tasksdisableall*  
Como usar: */clocktasksdisableall [Plugin]*   
Desativa todas tarefas ou somente as tarefas de um plugin.

* **/clocktasksenablell** - *clockschedulerapi.tasksenableall*  
Como usar: */clocktasksenablell [Plugin]*   
Ativa todas tarefas ou somente as tarefas de um plugin.

* **/clockreload** - *clockschedulerapi.reload*  
Como usar: */clockreload*   
Re-carrega o plugin.

## A api

#### Criando um alarme:
* AlarmHour:
Esse alarme desperta toda hora ao x minuto.  
```Java
ClockAlarm alarm = new AlarmHour(MINUTO, SEGUNDO);
```
Exemplo:  
```Java
ClockAlarm alarm = new AlarmHour(30, 15);
```
Esse alarme vai desperta toda hora nos 30 minutos e 15 segundos.

* AlarmDaily:
Esse alarme desperta todo dia as x hora e x minuto.
```Java
ClockAlarm alarm = new AlarmDaily(HORA, MINUTO, SEGUNDO);
```
Exemplo:
```Java
ClockAlarm alarm = new AlarmDaily(10,30, 00);
```
Esse alarme vai desperta todo dia as 10 horas, 30 minutos e 0 segundos.

* AlarmWeekly:
Esse alarme desperta toda semana as x hora e x minuto.
```Java
ClockAlarm alarm = new AlarmWeekly(DIA_DA_SEMANA, HORA, MINUTO, SEGUNDO);
```
Exemplo:
```Java
ClockAlarm alarm = new AlarmWeekly("Domingo", 11, 30, 30);
```
Esse alarme vai desperta toda semana no domingo as 10 horas, 30 minutos e 30 segundos.

* AlarmMothly:
Esse alarme desperta todo mês no dia x as x hora e x minuto.
```Java
ClockAlarm alarm = new AlarmMothly(DIA, HORA, MINUTO, SEGUNDO);
```
Exemplo:
```Java
ClockAlarm alarm = new AlarmMothly(20, 12, 30, 45);
```
Esse alarme vai desperta todo mês no dia 20 as 12 horas, 30 minutos e 45 segundos.

* AlarmYearly:
Esse alarme desperta todo ano no mês x no dia x as x hora e x minuto.
```Java
ClockAlarm alarm = new AlarmYearly(DIA, MES, HORA, MINUTO, SEGUNDO);
```
Exemplo:
```Java
ClockAlarm alarm = new AlarmYearly(25, "Dezembro", 0, 0, 50);
```
Esse alarme vai desperta todo ano no mês de dezembro no dia 25 as 0 horas, 0 minuto e 50 segundos.

* AlarmDate:
Esse alarme desperta numa data fixa, e não repete.
```Java
ClockAlarm alarm = new AlarmDate(ANO, MES, DIA, HORA, MINUTO, SEGUNDO);
```
Exemplo:
```Java
ClockAlarm alarm = new AlarmDate(2015, "Setembro", 22, 15, 0, 25);
```
Esse alarme vai desperta no ano de 2015 no mês setembro no dia 22 as 15 horas, 0 minuto e 25 segundos.

* AlarmInterval:
Esse alarme desperta de tempos em tempos.
```Java
ClockAlarm alarm = new AlarmInterval(INTERVALO);
```
Exemplo:
```Java
ClockAlarm alarm = new AlarmInterval(60);
```
Esse alarme vai desperta de 60 em 60 segundos.

* AlarmBetween:
Esse alarme é a junção de 3 alarmes, o START, END e ALARM, o alarme que vai definir quando será executado é o ALARM, mas para ele executar precisa estar entre o START e o END, exemplo, se o START for todo dia 10 do mês, o end for todo dia 20 do mês e o START for um intervalo de 30 segundos, sempre entre o dia 10 e 20 do mês o intervalo de 30 segundos executará.
```Java
ClockAlarm alarm = new AlarmBetween(START, END, ALARM);
```
Exemplo:
```Java
ClockAlarm alarm = new AlarmBetween(new AlarmMothly(10, 00, 00), new AlarmMothly(20, 00, 00), new AlarmInterval(30));
```
Esse alarme vai desperta entre do dia 10 e 20 do mês a cada 30 segundos.

* AlarmMonthlyWeek:
Esse alarme desperta em uma determinada semana do mês.
```Java
ClockAlarm alarm = new AlarmMonthlyWeek(SEMANA_DO_MÊS, DIA_DA_SEMANA, HORA, MINUTO, SEGUNDO);
```
Exemplo:
```Java
ClockAlarm alarm = new AlarmMonthlyWeek(3, Util.getWeek("Domingo"), 10, 30, 45);
```
Esse alarme vai desperta na terceira semana do mês, no domingo as 10 horas, 30 minutos e 45 segundos.

* AlarmMulti:
Esse alarme é apenas um grupos de alarmes, com ele você pode colocar varios alarmes em um.
```Java
ClockAlarm alarm = new AlarmMulti(ALARME, ALARME, ALARME...);
```
Exemplo:
```Java
ClockAlarm alarm = new AlarmMulti(new AlarmInterval(60), new AlarmWeekly("Domingo", 11, 30, 30));
```
Esse alarme vai desperta de 60 em 60 segundos e todo domingo as 11 horas, 30 minutos e 30 segundos.

* Por string:
Se você quiser criar um alarme a parti de uma string como nos exemplos a cima.
```Java
ClockAlarm alarm = ClockSchedulerAPI.getAlarm(STRING);
```
Exemplo:
```Java
ClockAlarm alarm = ClockSchedulerAPI.getAlarm("Anual\|22 Setembro 15:00");
```
Exemplo2:
```Java
ClockAlarm alarm = ClockSchedulerAPI.getAlarm(getConfig().getString("MeuPlugin.Alarm"));
```
Recomendado para quando você precisar tirar algum alarme da config de seu plugin ou qualquer outra coisa.

#### Registrando um alarme:

Para adicionar uma tarefa digite o seguinte codigo:
```Java
ClockTask task = ClockSchedulerAPI.addTask(ALARME, RUNNABLE, PLUGIN);
```

 * ALARME
   Esse é o alarme criado no topico anterior.

 * RUNNABLE
   Esse é o Runnable que vai ser executado quando o alarme despertar.

 * PLUGIN
   Esse é o seu plugin.

Ou você pode adicionar direto de um string:
```Java
ClockTask task = ClockSchedulerAPI.addTask(STRING, RUNNABLE, PLUGIN);
```

 * STRING
   Essa é a string do alarme, como nos exemplos a cima.

#### Cancelando uma tarefa
Para cancelar você pode desativar ela ou remover da lista, desativando ela vai continuar no gerenciador do ClockSchedulerAPI mas desativada, recomendado quando você for querer ativar ela novamente, quando remove a tarefa ela será apagada da lista.  
Removendo:
```Java
ClockTask task = ClockSchedulerAPI.addTask(STRING, RUNNABLE);
ClockSchedulerAPI.removeTask(alarm);
```
Desativando:
```Java
ClockTask task = ClockSchedulerAPI.addTask(STRING, RUNNABLE);
task.disable();
```

Você támbem pode remover todas as tarefas do seu plugin, exemplo:
```Java
ClockSchedulerAPI.removeTasksPlugin(PLUGIN);
```
