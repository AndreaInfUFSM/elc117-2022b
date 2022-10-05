<p align="left"><a href="../README.md"><< Reiniciar</a> | <a href="README01.md">Anterior</a></p>

## Refatore o código

[Refatorar um código](https://en.wikipedia.org/wiki/Code_refactoring) significa reestruturá-lo para melhorar algum aspecto, sem alterar seu comportamento observável.

Vamos melhorar a organização em classes do código da prática anterior, e com isso simplificar a implementação de alguns métodos.

1. Crie uma classe `Member` com atributos/métodos comuns a `Student` e `Professor` (atributos e métodos que eram redundantes).

2. Refatore as classes `Student` e `Professor` para que sejam derivadas de `Member`:
   - Elimine atributos e métodos redundantes, que devem ficar somente em `Member`.
   - Ajuste os construtores usando `super` para que invoquem os construtores de `Member`

3. Refatore a classe `Laboratory`:
   - Substitua `ArrayList<Student>` e `ArrayList<Professor>` por `ArrayList<Member>`. Como tanto `Student` como `Professor` são derivadas de `Member`, poderão ser armazenados nesta `ArrayList`.
   - Substitua os métodos `addMember(Student)` e `addMember(Professor)`  por `addMember(Member)`.
   - Reimplemente os métodos `getContactInfos`, `userExists` e `countMembers`, acessando somente a lista de `Member`, sem distinguir se o membro é `Student` ou `Professor`. 



4. Se o código for refatorado como solicitado, a saída do Main será a seguinte:
   ```
   NCC has 3 members
   [{class='Student', name='Nome1 Sobrenome1', userId='username1'}, {class='Student', name='Nome2 Sobrenome2', userId='username2'}, {class='Professor', name='Andrea Charao', userId='andrea', room='376', building='Anexo B'}]
   User andrea found
   ```

**Pronto! Prática concluída!** 


