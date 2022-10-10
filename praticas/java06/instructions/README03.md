<p align="left"><a href="../README.md"><< Reiniciar</a> | <a href="README02.md">Anterior</a></p>

## Complete o código



1. Implemente o método `public String toString()` na classe `Assignment`, de forma que o primeiro laço em `TrackAssignments` produza o seguinte:
   ```
   ==> Printing all assignment **OBJECTS**:
   { dueDate='2022-09-15', description='jamboard', pending='true', submitDate='null'}
   { dueDate='2022-09-22', description='java01', pending='true', submitDate='null'}
   { dueDate='2022-09-27', description='java02', pending='true', submitDate='null'}
   { dueDate='2022-10-13', description='java06', pending='true', submitDate='null'}
   ```
   Veja mais sobre isso [aqui](http://www.mauda.com.br/?p=1472) (em português) ou [aqui](https://runestone.academy/ns/books/published/csawesome/Unit9-Inheritance/topic-9-7-Object.html) (em inglês).

2. Complete o método `status` na classe `Assignment` para retornar uma string que represente a situação da tarefa:
   - "done" se a tarefa estiver completa (não pendente)
   - "late" se a tarefa estiver pendente e atrasada
   - "due in x days" se a tarefa estiver pendente, faltando x=daysLeft() dias para a entrega



3. Complete o método `message()` na classe `GroupAssignment` para mostrar uma mensagem modificada quando a tarefa for em grupo, conforme o exemplo abaixo:
   ```
   ==> Printing all assignment **MESSAGES**:
   Group Assignment jamboard is late - call teamMate1, teamMate2
   Assignment java01 is late
   Group Assignment java02 is late - call teamMate1
   Group Assignment java06 is due in 2 days - call teamMate1
   ```
   Dicas:
   - Identifique o padrão: o que é variável e o que é fixo na mensagem modificada?
   - Para evitar redundância, aproveite/reuse a mensagem implementada na superclasse



4. No final do método `main` na classe `TrackAssignments`, adicione um código para contar e mostrar a quantidade de tarefas concluídas (não pendentes).






<p align="right"><a href="README04.md">Terminou? Confira o resultado! >></a> </p>
