<!--
author:   Andrea Charão

email:    andrea@inf.ufsm.br

version:  0.0.1

language: PT-BR

narrator: Brazilian Portuguese Female

comment:  Material de apoio elaborado para a disciplina
          ELC117 - Paradigmas de Programação,
          da Universidade Federal de Santa Maria

link:     https://cdn.jsdelivr.net/chartist.js/latest/chartist.min.css

script:   https://cdn.jsdelivr.net/chartist.js/latest/chartist.min.js

translation: English  translations/English.md

@load.java: @load(java,@0)

@load
<script style="display: block" modify="false" run-once="true">
    fetch("@1")
    .then((response) => {
        if (response.ok) {
            response.text()
            .then((text) => {
                send.lia("LIASCRIPT:\n``` @0\n" + text + "\n```")
            })
        } else {
            send.lia("HTML: <span style='color: red'>Something went wrong, could not load <a href='@1'>@1</a></span>")
        }
    })
    "loading: @1"
</script>
@end

-->

# OOP em ação: Persistência + GUI

* Nas aulas anteriores, trabalhamos com 2 cenários que colocaram em ação a **programação orientada a objetos** usando bibliotecas externas ao Java SDK: (1) persistência de objetos em um banco de dados usando MongoDB e (2) interfaces gráficas com JavaFX.

* Nesta aula, vamos unir estes 2 cenários e também aproveitar para conhecer outras bibliotecas/serviços.


## Persistência de dados no Google Sheets


* Armazenar dados em nuvem é uma tendência e combina muito bem com a **programação orientada a objetos**! 

* Existe uma grande variedade de opções em nuvem para isso: 

  - bancos de dados relacionais (por exemplo, PostgreSQL em www.elephantsql ou supabase.com), 
  - bancos de dados NoSQL (por exemplo, MongoDB em https://www.mongodb.com/atlas) 
  - e até mesmo serviços de planilhas em nuvem, como Google Sheets.

* Armazenar dados em planilhas em nuvem tem limitações em comparação com bancos de dados, mas é uma solução vantajosa e simples!

* Qualquer que seja a opção, essa funcionalidade requer **bibliotecas de classes** externas ao Java SDK. Em outras linguagens, a sintaxe muda, mas a lógica permanece muito semelhante.





### Exemplo: Código e dados 

* O código em [java13/src/01-songrepository](https://github.com/AndreaInfUFSM/elc117-2022b/tree/main/praticas/java13/src/01-songrepository) contém um exemplo de programa que implementa persistência de objetos da classe `Song` no Google Sheets. 

* Os dados são persistidos em uma planilha compartilhada no Google Sheets: ['songs'](https://docs.google.com/spreadsheets/d/1iW7j0NtDbzFuoCSEi9Y4DWDgzRkeA4V_cYvlKBQbmIg/edit?usp=sharing)


* O projeto contém uma versão simplificada da classe `Song` usada nas aulas anteriores: tags são armazenadas em uma única String.

* O projeto usa Gradle para gerenciar dependências e automatizar o *build*. 

### Pontos importantes


* Para a comunicação do programa com o serviço em nuvem, foi criada uma API web com o serviço https://www.apispreadsheets.com/. A API permite fazer as operações Create-Read-Update-Delete (CRUD) por meio de requisições pela rede.

* Os dados são enviados/recebidos pela API em formato JSON, conforme este exemplo (resultado da requisição Read):

   ```
   This code is verbatim
   ```
   
* Outro item

  ``` json 
  { "data":[
         {
               "url": "https://open.spotify.com/track/3CIyK1V4JEJkg02E4EJnDl",
               "name": "Enemy",
               "tags": "pop rock",
               "user": "andreainfufsm",
               "artist": "Imagine Dragons"
          },
          {
               "url": "https://open.spotify.com/track/2JVbZEpewmqNlSzmXJTx2E",
               "name": "Favela",
               "tags": "EDM",
               "user": "andreainfufsm",
               "artist": "Alok"
          }
     ]
  }
  ```

*  Para trabalhar com este formato em Java, é conveniente usar uma biblioteca externa que converte dados de objetos em memória para o formato JSON e vice-versa (serialização/deserialização). Neste projeto, é usada a biblioteca [GSON](https://github.com/google/gson/blob/master/UserGuide.md).


## GUI para entrada de dados

* Na última aula você deve ter criado um formulário usando JavaFX para entrada de dados na classe `Song`. 

* Conforme os exemplos da última aula, isso poderia ser feito de 2 formas: (a) criando toda a interface gráfica no código Java (usando classes Label, TextField, Button, etc.) ou (b) codificando a interface gráfica em formato FXML (manualmente ou usando SceneBuilder) e carregando-a no código Java.

* Usar FXML é um pouco mais complicado no início, mas é a opção mais recomendada.


### Exemplo: código 

* O código em [java13/src/02-songform](https://github.com/AndreaInfUFSM/elc117-2022b/tree/main/praticas/java13/src/02-songform) contém um exemplo de programa usando JavaFX/FXML que implementa a entrada de dados para a classe `Song`. 

* Quando o usuário clica no botão, é criado um novo objeto da classe `Song` em memória. 

* Este código não implementa persistência de dados, portanto todos os objetos criados são perdidos quando o programa termina.


* O projeto usa Gradle para gerenciar dependências e automatizar o *build*. 







## Prática

Nesta prática, vamos executar os exemplos e combiná-los para produzir um novo programa com GUI para entrada de dados e persistência desses dados em uma planilha no Google Sheets.


### Obtenha o código


Obtenha o código como nas outras práticas, atualizando seu fork do repositório da disciplina (ou clonando todo o repositório).

O código tem 3 projetos dentro da pasta `src`:

* 01-songrepository: projeto sem interface gráfica que trata de persistência de dados no Google Sheets.
* 02-songform: projeto com interface gráfica mas sem persistência de dados
* 03-songpractice: projeto que você vai preencher para combinar as funcionalidades dos 2 anteriores


### Execute os projetos


Para executar qualquer um dos projetos, abra um terminal, vá até a pasta que contém o projeto e digite:  


```
./gradlew run
```






### Preencha o novo projeto

Você vai trabalhar no projeto `03-songpractice`, que já contém uma parte do código e já teve seu `build.gradle` configurado para incluir todas as dependências.

1. Adicione o arquivo `SongSheetRepository.java` ao projeto.

2. Altere a classe `SongController` de forma que, quando o botão for clicado, o objeto criado seja enviado para a planilha no Google Sheets, usando métodos definidos em `SongSheetRepository`. Exemplos de como fazer isso estão nos comentários do projeto `01-songrepository`, no arquivo `App.java`.

3. Note que a GUI para entrada de dados tem um Label com o texto `Song name`. Altere o texto para que apareça simplesmente `Name`. Altere também a ordem dos 2 primeiros campos (original tem URL depois do nome da música, modificado terá URL antes do nome da música). 

4. Note que, quando um objeto é cadastrado, os valores digitados para seus atributos permanecem nos `TextField`s da GUI. Altere o código para limpar os campos depois que o objeto for persistido.

Para você fazer os 2 últimos exercícios, terá que identificar onde as alterações devem ser feitas. Estes exercícios são um incentivo para você a perceber o valor de um código organizado em várias classes/arquivos, cada um com suas responsabilidades bem definidas.

### Opcional: vá além!

Estes exemplos servem como ponto de partida para programas com mais funcionalidades, que você pode implementar se quiser praticar mais (ou compensar excesso de faltas, atrasos em entregas, etc.).

Algumas ideias de opcionais são:

- Implemente um CRUD completo com GUI e controllers para cadastrar, listar, alterar e remover músicas da planilha. Neste caso, use o SceneBuilder para ajudar a compor a GUI.

- Use o serviço https://apispreadheets.com para criar uma API web para outra planilha hospedada no seu Google Drive, associadoa a outra classe à sua escolha (por exemplo, alguma classe usada no seu trabalho de OOP). Nesse caso, você deverá criar outra classe semelhante a `SongSheetRepository` e identificar o que deve ser alterado. Importante: use nomes de colunas na planilha idênticos a nomes de atributos da classe correspondente.

- Implemente uma GUI para visualizar a lista de músicas em formato de tabela usando JavaFX.



### Entrega

Clique neste link para criar seu repositório de entrega: https://classroom.github.com/a/8ZgnYTET

Entregue a pasta 03-songpractice e todo seu conteúdo, incluindo os arquivos de configuração do projeto. 

Caso você implemente extras (indo além), indique isso em um README.md e inclua o código opcional em uma pasta separada.

Não entregue o código em formato compactado. Evite usar a interaface Web do GitHub para enviar arquivos ao repositório. Use um cliente Git em linha de comando ou via IDE para enviar os arquivos separadamente.
