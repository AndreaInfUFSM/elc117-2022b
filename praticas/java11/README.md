<!--
author:   Andrea Charão

email:    andrea@inf.ufsm.br

version:  0.0.1

language: PT-BR

narrator: Brazilian Portuguese Female

comment:  Material de apoio interativo elaborado para a disciplina
          ELC117 - Paradigmas de Programação,
          da Universidade Federal de Santa Maria

link:     https://cdn.jsdelivr.net/chartist.js/latest/chartist.min.css

script:   https://cdn.jsdelivr.net/chartist.js/latest/chartist.min.js

translation: English  translations/English.md


onload
window.CodeRunner = {
    ws: undefined,
    handler: {},

    init(url) {
        this.ws = new WebSocket(url);
        const self = this
        this.ws.onopen = function () {
            self.log("connections established");
            setInterval(function() {
                self.ws.send("ping")
            }, 15000);
        }
        this.ws.onmessage = function (e) {
            // e.data contains received string.

            let data
            try {
                data = JSON.parse(e.data)
            } catch (e) {
                self.warn("received message could not be handled =>", e.data)
            }
            if (data) {
                self.handler[data.uid](data)
            }
        }
        this.ws.onclose = function () {
            self.warn("connection closed")
        }
        this.ws.onerror = function (e) {
            self.warn("an error has occurred => ", e)
        }
    },
    log(...args) {
        console.log("CodeRunner:", ...args)
    },
    warn(...args) {
        console.warn("CodeRunner:", ...args)
    },
    handle(uid, callback) {
        this.handler[uid] = callback
    },
    send(uid, message) {
        message.uid = uid
        this.ws.send(JSON.stringify(message))
    }
}

window.CodeRunner.init("wss://coderunner.informatik.tu-freiberg.de/")
//window.CodeRunner.init("ws://127.0.0.1:8000/")

@end


@LIA.c:       @LIA.eval(`["main.c"]`, `gcc -Wall main.c -o a.out`, `./a.out`)
@LIA.clojure: @LIA.eval(`["main.clj"]`, `none`, `clojure -M main.clj`)
@LIA.cpp:     @LIA.eval(`["main.cpp"]`, `g++ main.cpp -o a.out`, `./a.out`)
@LIA.go:      @LIA.eval(`["main.go"]`, `go build main.go`, `./main`)
@LIA.haskell: @LIA.eval(`["main.hs"]`, `ghc main.hs -o main`, `./main`)
@LIA.java:    @LIA.eval(`["@0.java"]`, `javac @0.java`, `java @0`)
@LIA.julia:   @LIA.eval(`["main.jl"]`, `none`, `julia main.jl`)
@LIA.mono:    @LIA.eval(`["main.cs"]`, `mcs main.cs`, `mono main.exe`)
@LIA.nasm:    @LIA.eval(`["main.asm"]`, `nasm -felf64 main.asm && ld main.o`, `./a.out`)
@LIA.python:  @LIA.python3
@LIA.python2: @LIA.eval(`["main.py"]`, `python2.7 -m compileall .`, `python2.7 main.pyc`)
@LIA.python3: @LIA.eval(`["main.py"]`, `none`, `python3 main.py`)
@LIA.r:       @LIA.eval(`["main.R"]`, `none`, `Rscript main.R`)
@LIA.rust:    @LIA.eval(`["main.rs"]`, `rustc main.rs`, `./main`)
@LIA.zig:     @LIA.eval(`["main.zig"]`, `zig build-exe ./main.zig -O ReleaseSmall`, `./main`)

@LIA.dotnet:  @LIA.dotnet_(@uid)

@LIA.dotnet_
<script>
var uid = "@0"
var files = []

files.push(["project.csproj", `<Project Sdk="Microsoft.NET.Sdk">
  <PropertyGroup>
    <OutputType>Exe</OutputType>
    <TargetFramework>net6.0</TargetFramework>
    <ImplicitUsings>enable</ImplicitUsings>
    <Nullable>enable</Nullable>
  </PropertyGroup>
</Project>`])

files.push(["Program.cs", `@input(0)`])

send.handle("input", (e) => {
    CodeRunner.send(uid, {stdin: e})
})
send.handle("stop",  (e) => {
    CodeRunner.send(uid, {stop: true})
});


CodeRunner.handle(uid, function (msg) {
    switch (msg.service) {
        case 'data': {
            if (msg.ok) {
                CodeRunner.send(uid, {compile: "dotnet build -nologo"})
            }
            else {
                send.lia("LIA: stop")
            }
            break;
        }
        case 'compile': {
            if (msg.ok) {
                if (msg.message) {
                    if (msg.problems.length)
                        console.warn(msg.message);
                    else
                        console.log(msg.message);
                }

                send.lia("LIA: terminal")
                console.clear()
                CodeRunner.send(uid, {exec: "dotnet run"})
            } else {
                send.lia(msg.message, msg.problems, false)
                send.lia("LIA: stop")
            }
            break;
        }
        case 'stdout': {
            if (msg.ok)
                console.stream(msg.data)
            else
                console.error(msg.data);
            break;
        }

        case 'stop': {
            if (msg.error) {
                console.error(msg.error);
            }

            if (msg.images) {
                for(let i = 0; i < msg.images.length; i++) {
                    console.html("<hr/>", msg.images[i].file)
                    console.html("<img title='" + msg.images[i].file + "' src='" + msg.images[i].data + "' onclick='window.LIA.img.click(\"" + msg.images[i].data + "\")'>")
                }

            }

            send.lia("LIA: stop")
            break;
        }

        default:
            console.log(msg)
            break;
    }
})


CodeRunner.send(
    uid, { "data": files }
);

"LIA: wait"
</script>
@end

@LIA.eval:  @LIA.eval_(false,@uid,`@0`,@1,@2)

@LIA.evalWithDebug: @LIA.eval_(true,@uid,`@0`,@1,@2)

@LIA.eval_
<script>
const uid = "@1"
var order = @2
var files = []

if (order[0])
  files.push([order[0], `@input(0)`])
if (order[1])
  files.push([order[1], `@input(1)`])
if (order[2])
  files.push([order[2], `@input(2)`])
if (order[3])
  files.push([order[3], `@input(3)`])
if (order[4])
  files.push([order[4], `@input(4)`])
if (order[5])
  files.push([order[5], `@input(5)`])
if (order[6])
  files.push([order[6], `@input(6)`])
if (order[7])
  files.push([order[7], `@input(7)`])
if (order[8])
  files.push([order[8], `@input(8)`])
if (order[9])
  files.push([order[9], `@input(9)`])


send.handle("input", (e) => {
    CodeRunner.send(uid, {stdin: e})
})
send.handle("stop",  (e) => {
    CodeRunner.send(uid, {stop: true})
});


CodeRunner.handle(uid, function (msg) {
    switch (msg.service) {
        case 'data': {
            if (msg.ok) {
                CodeRunner.send(uid, {compile: @3})
            }
            else {
                send.lia("LIA: stop")
            }
            break;
        }
        case 'compile': {
            if (msg.ok) {
                if (msg.message) {
                    if (msg.problems.length)
                        console.warn(msg.message);
                    else
                        console.log(msg.message);
                }

                send.lia("LIA: terminal")
                CodeRunner.send(uid, {exec: @4})

                if(!@0) {
                  console.clear()
                }
            } else {
                send.lia(msg.message, msg.problems, false)
                send.lia("LIA: stop")
            }
            break;
        }
        case 'stdout': {
            if (msg.ok)
                console.stream(msg.data)
            else
                console.error(msg.data);
            break;
        }

        case 'stop': {
            if (msg.error) {
                console.error(msg.error);
            }

            if (msg.images) {
                for(let i = 0; i < msg.images.length; i++) {
                    console.html("<hr/>", msg.images[i].file)
                    console.html("<img title='" + msg.images[i].file + "' src='" + msg.images[i].data + "' onclick='window.LIA.img.click(\"" + msg.images[i].data + "\")'>")
                }

            }

            send.lia("LIA: stop")
            break;
        }

        default:
            console.log(msg)
            break;
    }
})


CodeRunner.send(
    uid, { "data": files }
);

"LIA: wait"
</script>
@end
-->

# OOP + DB: Um match perfeito!

Programação orientada a objetos (OOP) e bancos de dados (DB) andam juntos em muitos casos. 

Aqui você vai conhecer exemplos usando Java com MongoDB - uma dupla que dá um bom match! 😃


## Persistência de objetos


Um programa em execução pode criar muitos objetos. 

Por exemplo, imagine instâncias de uma classe `Song`, representando músicas. Esta classe pode definir alguns atributos, por exemplo:

* `artist` (nome do cantor/cantora/grupo) e 
* `name` (nome da música).  

Cada objeto `Song` terá valores para `artist` e `name` - por exemplo `Coldplay` e `Clocks` (pense aqui em outros valores baseados nas suas músicas favoritas). 

Quando você cria esses objetos da classe `Song`, eles ficam armazenados em memória e você pode manipulá-los, por exemplo com um método `getArtist()`.

Mas... **O que acontece quando o programa termina?** Todos os seus objetos são destruídos! Todas suas músicas favoritas se perderam??? :-) Não se você implementar alguma forma de **persistência**  - isto é, salvar os dados em um arquivo ou banco de dados.

Há muitos tipos de bancos de dados e seus gerenciadores. Por exemplo, MySQL ou PostgreSQL são dois gerenciadores populares para bancos de dados relacionais (você verá isso em outra disciplina). Também há os bancos não-relacionais (NoSQL), entre os quais temos o MongoDB.


## MongoDB

Este banco de dados NoSQL trabalha com coleções de "documentos".

Um **documento** é semelhante a um objeto com seus atributos. 

Por exemplo, podemos ter uma coleção  contendo um documento com os seguintes campos e valores referentes a uma música:

* `artist: Coldplay` 
* `name: Clocks`, 

Um banco de dados MongoDB  pode ter várias **coleções** - por exemplo, uma coleção com dados de usuários e outra com dados de músicas.

MongoDB não exige uma pré-definição da estrutura do banco de dados, por isso podemos simplesmente ir acrescentando dados a uma coleção (isso não acontece com bancos de dados relacionais, que precisam da definição de um esquema antes de podermos manipular os dados).


## Usando MongoDB com Java

Para usar MongoDB com Java, precisamos do seguinte:

1. Criar um banco de dados em MongoDB: isso pode ser feito localmente, instalando MongoDB no seu computador, ou usando um serviço em nuvem (Atlas é o mais recomendado atualmente).

2. Configurar um projeto Java para incluir um *driver* Java para o MongoDB (biblioteca de classes que se comunicam com o gerenciador do banco de dados). Isso precisa ser feito usando uma ferramenta de automação de *build*, como Gradle ou Maven.

3. No código Java, usar as classes oferecidas no *driver* para instanciar um cliente do banco e fazer operações usando os (muuuuitos) métodos oferecidos na biblioteca. Esta etapa é como usar qualquer outra API em Java.

Alguns links importantes:

* Java and MongoDB: informações gerais
  https://www.mongodb.com/languages/java

* Documentação: MongoDB Java Driver  
  https://www.mongodb.com/docs/drivers/java/sync/current/



## Prática

Nesta prática, vamos trabalhar com uma classe `Song`, que representa músicas adicionadas no banco por colegas da turma.

Todo objeto da classe `Song` terá os seguintes atributos:
* `name`: nome da música
* `artist`: nome do artista (cantor, cantora, grupo, etc.)
* `url`: URL da música (trilha/track no Spotify, por exemplo)
* `user`: nome do usuário que adicionou a música
* `tags`: array de tags sobre a música (gênero musical, ou outras observações que o usuário quiser adicionar)


### Obtenha o projeto

O çódigo para esta prática está em [praticas/java11/src/songs](https://github.com/AndreaInfUFSM/elc117-2022b/tree/main/praticas/java11/src/songs).


Obtenha o código como nas outras práticas, atualizando seu fork do repositório da disciplina (ou clonando todo o repositório).

### Execute o primeiro exemplo do projeto

Esta parte é provavelmente a mais propensa a problemas, então vamos com calma! :-)

Para executar o projeto, não vamos usar os comandos javac/java diretamente. Vamos usar o Gradle, que é a ferramenta de automação de *build* usada neste exemplo.

Vamos usar linha de comando e usar o *Gradle wrapper*, um script que copia as dependências e invoca o Gradle. 

Este script tem que ser chamado **dentro** da pasta `songs` que contém o projeto. Abra um terminal nesta pasta e execute o script conforme os exemplos abaixo.

No Linux:

```
./gradlew example1:run
```

No Windows:

```
.\gradlew.bat example1:run
```



### Altere o código do primeiro exemplo

Examine o código em [Example1.java](https://github.com/AndreaInfUFSM/elc117-2022b/blob/main/praticas/java11/src/songs/example1/src/main/java/songs/Example1.java).

Note que não temos uma classe `Song` neste exemplo.

O código contém uma parte comentada que cria um documento (representando uma música) e o insere no banco. 

Descomente esta parte, altere os dados conforme seu gosto musical :-) e execute o projeto novamente.

```
./gradlew example1:run
```


### Examine o segundo exemplo do projeto

O segundo exemplo tem um código um pouco mais longo e organizado em mais classes:


* [`class Example2`](https://github.com/AndreaInfUFSM/elc117-2022b/blob/main/praticas/java11/src/songs/example2/src/main/java/songs/Example2.java): contém o método `main`
* [`class Song`](https://github.com/AndreaInfUFSM/elc117-2022b/blob/main/praticas/java11/src/songs/example2/src/main/java/songs/Song.java): representa uma música
* [`class SongCrudRepository`](https://github.com/AndreaInfUFSM/elc117-2022b/blob/main/praticas/java11/src/songs/example2/src/main/java/songs/SongCrudRepository.java): intermedia operações com o banco de dados para objetos da classe Song (CRUD = create-read-update-delete)

Neste exemplo, usamos mais recursos do *driver* MongoDB para mapear objetos no banco de dados, conforme esta documentação: https://www.mongodb.com/developer/languages/java/java-mapping-pojos/

### Execute o segundo exemplo do projeto

Para executar o segundo exemplo, alteramos o comando para:

```
./gradlew example2:run
```

### Altere o código do segundo exemplo


Veja que o código tem várias partes comentadas.

Altere o código para:

1. Inserir uma música que você goste 
2. Mostrar os nomes de todos os usuários que inseriram músicas no banco 
3. Alterar tags de uma música 









