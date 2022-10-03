<p align="left"><a href="../README.md"><< Reiniciar</a> | <a href="README01.md">Anterior</a></p>

## Código incompleto

1. Obtenha os códigos abaixo:

   - [Student.java](../src/incomplete/Student.java): classe que representa um estudante de uma instituição
   - [Professor.java](../src/incomplete/Professorjava): classe que representa um professor de uma instituição
   - [Laboratory.java](../src/incomplete/Laboratory.java): classe que representa um laboratório da instituição. Um laboratório pode ter estudantes e professores como membros. **Esta classe está incompleta!**
   - [Main.java](../src/incomplete//Main.java): classe contendo um método `main` que cria e manipula objetos das classes acima

   <br>
   <details>
     <summary>Pro Tip: Dica para Quem fez fork do repositório da disciplina</summary>
     <ul>
        <li> Você vai trabalhar com 2 repositórios:</li>
        <ul>
           <li> o fork do repositório da disciplina e </li>
           <li> o repositório criado automaticamente para esta prática quando você clicou no link de entrega. </li>
        </ul>
        <li>Para sincronizar o fork do repositório da disciplina, siga estas <a href="https://docs.github.com/en/github/collaborating-with-pull-requests/working-with-forks/syncing-a-fork">instruções</a> (também possível via <a href="https://www.freecodecamp.org/news/how-to-sync-your-fork-with-the-original-git-repository/">linha de comando </a>). Sincronizar o fork é melhor do que clonar o repositório da disciplina a cada aula.
        <li>Se precisar usar/modificar um código fornecido no repositório da discipline, copie-o para dentro do seu repositório de entrega.</ li>
     </ul>   
  </details>   
   
2. Compile e execute o código na pasta `incomplete`:
   ```
   javac *.java
   java Main
   ```
   **Você verá que a compilação dá erro, pois o código está incompleto!**


3. Você deverá fazer o código funcionar alterando somente o arquivo [Laboratory.java](../src/incomplete/Laboratory.java), de forma a completar as partes marcadas com "COMPLETE-ME". Para isso, é importante analisar todos os arquivos fornecidos.

4. Se o código for completado como solicitado, a saída do Main será a seguinte:
   ```
   NCC has 3 members
   [{class='Student', name='Nome1 Sobrenome1', userId='username1'}, {class='Student', name='Nome2 Sobrenome2', userId='username2'}, {class='Professor', name='Andrea Charao', userId='andrea', room='376', building='Anexo B'}]
   User andrea found
   ```
4. Quando o código funcionar, faça commit e push para o GitHub.



<p align="right"><a href="README03.md">Vamos adiante... >></a> </p>

