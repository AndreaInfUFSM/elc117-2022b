<!--
author:   Andrea Charão

email:    andrea@inf.ufsm.br

version:  0.0.1

language: PT-BR

narrator: Brazilian Portuguese Female

comment:  Material elaborado para a disciplina
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

# Programação Lógica




> Este material é uma introdução ao paradigma de **programação lógica** em linguagem Prolog.
>
> O conteúdo tem partes interativas e pode ser visualizado de vários modos usando as opções no topo da página.


## Um problema de lógica

Você sabe responder isso?


> Para comemorar o aniversário de Cíntia, ela e mais quatro amigas - Alice, Bia, Dirce e Eunice - foram almoçar juntas no RU. As mesas são redondas e acomodam exatamente 5 pessoas. Cíntia e Dirce sentam-se uma ao lado da outra. Alice e Bia não sentam-se uma ao lado da outra. (Fonte: Olimpíada Brasileira de Informática)

As duas amigas sentadas ao lado de Eunice são:

   - [( )] Cíntia e Alice
   - [( )] Cíntia e Dirce
   - [(x)] Alice e Bia
   - [( )] Dirce e Bia
   - [( )] Alice e Dirce



> Você saberia fazer um programa para encontrar a resposta?




### Solução em Prolog

> Para comemorar o aniversário de Cíntia, ela e mais quatro amigas - Alice, Bia, Dirce e Eunice - foram almoçar juntas no RU. As mesas são redondas e acomodam exatamente 5 pessoas. Cíntia e Dirce sentam-se uma ao lado da outra. Alice e Bia não sentam-se uma ao lado da outra. (Fonte: Olimpíada Brasileira de Informática)

Código:

```prolog
solucao(X,Y) :-
    A = [alice,bia,cintia,dirce,eunice],
    permutation(A,L),
    aolado(cintia,dirce,L),
    not(aolado(alice,bia,L)),
    aolado(X,eunice,L),
    aolado(Y,eunice,L),
    not(X=Y), !.

aolado(X,Y,L) :- nextto(X,Y,L); nextto(Y,X,L).
aolado(X,Y,L) :- naspontas(X,Y,L).

naspontas(X,Y,L) :- L = [X|_], last(L,Y).
naspontas(X,Y,L) :- L = [Y|_], last(L,X).
```

Execução:

```prolog
user@localhost:~$ swipl mesas.pl 

?- solucao(cintia,alice).
false.

?- solucao(cintia,dirce).
false.

?- solucao(alice,bia).
true.

?- solucao(dirce, bia).
false.

?- solucao(alice,dirce).
false.
```

## Programação lógica


- [ ] É declarativa, expressa o que se quer obter, não como obter (assim como a funcional)
- [ ] Baseada em lógica matemática
- [ ] Programas são compostos por cláusulas que permitem deduções
- [ ] Prolog é a principal linguagem (propósito geral)

<section>**Deduções:**</section> 
Obtenção de informações que não estão explícitas... 

> Joãozinho é aluno de Paradigmas. 
> Todo aluno de Paradigmas é inteligente. 
> Logo, Joãozinho é inteligente.

### Aplicações

- [ ] Primeiros passos em Inteligência Artificial
- [ ] Sistemas especialistas, bancos de dados inteligentes, processamento de linguagem natural, chatbots...

Um produto...

??[](https://www.ibm.com/watson)

Bastidores...

??[](https://www.cs.nmsu.edu/ALP/2011/03/natural-language-processing-with-prolog-in-the-ibm-watson-system/)

### Origens

- [ ] Início da década de 70 (assim como a linguagem C)
- [ ] Pesquisas em processamento de linguagem natural: https://dl.acm.org/doi/10.1145/234286.1057820
- [ ] Motivações persistem até hoje (por exemplo: ["Alexa, How Can I Reason with Prolog?"](https://drops.dagstuhl.de/opus/volltexte/2019/10884/pdf/OASIcs-SLATE-2019-17.pdf))

Ver árvore genealógica das linguagens: https://www.digibarn.com/collections/posters/tongues/tongues.jpg

![](https://www.digibarn.com/collections/posters/tongues/tongues.jpg)

### Prolog

- [ ] Várias implementações e "dialetos" 
- [ ] Padronização [ISO/IEC](https://www.iso.org/standard/21413.html)
- [ ] Principais compiladores/interpretadores: SWI Prolog, GNU Prolog, Tau Prolog

![](https://www.swi-prolog.org/icons/swipl.png) SWI-Prolog tem ambiente online (SWISH):  https://www.swi-prolog.org/, https://swish.swi-prolog.org/




## Prolog

Vamos ver alguns recursos da linguagem na sequência.

Usaremos o SWI-Prolog online no Repl.it.

### Prolog no Repl.it

> Podemos executar Prolog no Repl.it!


Instruções:

- Clique no botão Run e aguarde aparecer o prompt do shell no Linux
- Digite `swipl main.pl` para abrir o programa `main.pl` no interpretador de comandos do SWI-Prolog
- No prompt do SWI-Prolog, digite consultas conforme os exemplos das próximas seções. Por exemplo:

  ```
  ?- inteligente(joaozinho).
  ```

??[](https://replit.com/@AndreaSchwertne/2022prolog)




### Basics

- [ ] Um programa em Prolog é composto por definições de **predicados** (verificáveis true ou false)
- [ ] Predicados expressam propriedades ou relações entre objetos
- [ ] Definições de predicados por meio de 2 tipos de cláusulas: **fatos** ou **regras**
- [ ] Execução do programa é uma **consulta**

| Conceito | Exemplo | Prolog |
| -------- | ------- | ------ |
| Fato | "Joãozinho é aluno de Paradigmas" | `aluno(joaozinho).` |
| Regra | "Todo aluno de Paradigmas é inteligente" | `inteligente(X) :- aluno(X).` |
| Consulta | "Joãozinho é inteligente?" | `?- inteligente(joaozinho).` |


### Predicados

- [ ] Predicados são a base das instruções em Prolog
- [ ] Forma geral: `nomedopredicado(arg1, arg2, ...)`
- [ ] Predicados se diferenciam pelo nome (case-sensitive) e quantidade de argumentos
- [ ] Nome de predicado usado em **fatos**, **regras**, **consultas**
- [ ] Argumentos não têm tipo explícito e podem ser **constantes** ou **variáveis**
- [ ] Constantes são valores simbólicos: inicial minúscula (ex.: `joaozinho`), números (ex.: `9`, `-8`, `22.3`), strings (ex.: `"ABC"`), listas (`[a,b]`)
- [ ] Variáveis iniciam por maiúscula e servem para estabelecer relações 

Aqui temos 2 predicados:

```Prolog
aluno(joaozinho).
inteligente(X) :- aluno(X).
```
```Prolog
?- inteligente(joaozinho).
```

### Fatos

- [ ] Fatos expressam verdades **incondicionais**.
- [ ] São cláusulas com apenas uma parte (ao contrário das regras que têm 2 partes)
- [ ] Sintaxe: cláusulas sempre terminam com ponto final (`.`)

```Prolog
idade(joaozinho, 27).
idade(maria, 60).
aluno(joaozinho).
nacionalidade(joaozinho, brasileiro).
nacionalidade(maria, brasileiro).
mae(joaozinho, maria).
```
### Consultas

- [ ] Usam **predicados** definidos por meio de fatos e regras
- [ ] Buscam resposta, que pode ser `true`/`false` ou valor para variável

```Prolog
?- aluno(joaozinho).
true
?- aluno(outronome).
false
```
#### Com variáveis

- [ ] Consultas podem ter variáveis.
- [ ] Se houver valor que satisfaça a consulta, ele será vinculado à variável.

```Prolog
?- aluno(X).
X = joaozinho
?- idade(joaozinho,I).
I = 27
```


#### Buscando mais respostas

- [ ] Ponto-e-vírgula (`;`) serve para buscar outra resposta no interpretador
- [ ] Significa "ou" e pode ser usado também em regras


```Prolog
?- nacionalidade(X, brasileiro).
X = joaozinho ;
X = maria
```




### Regras

- [ ] São cláusulas com **condicionais**.
- [ ] Forma geral: `<consequente> :- <antecedente>` (ou seja, do lado direito uma condição, do lado esquerdo o que pode ser deduzido caso a condição se verifique)


```Prolog
ave(X) :- papagaio(X).
```

Exemplos com condições compostas

```Prolog
idoso(X) :- idade(X, I), I >= 65.
alto(X) :- altura(X,A), A > 170.
```

#### Regras com "E" lógico

Usamos vírgula (`,`) para expressar "E" lógico.

Exemplo (em português):

> Se X é pai de Y e Y é pai de Z, então X é avô de Z.

Em Prolog:

```Prolog
avo(X,Z) :- pai(X,Y), pai(Y,Z). 
```



#### Regras com "OU" lógico

Usamos ponto-e-vírgula (`;`) para expressar "OU" lógico.

Exemplo (em português):

> Se X é um papagaio ou uma coruja, então X é uma ave.

Em Prolog:

```Prolog
ave(X) :- papagaio(X) ; coruja(X). 
```

Outra opção (muito usada) é expressar o "OU" na forma de definições alternativas para o predicado:

```Prolog
ave(X) :- papagaio(X).
ave(X) :- coruja(X).
```


### Operadores relacionais (com números)

Em regras ou consultas, podemos usar operadores relacionais, que comparam valores e resultam verdadeiro ou falso.


Exemplos de consultas com o predicado `idade` definido pelos fatos mais abaixo:


```Prolog
?- idade(N,X), X =< 30.
?- idade(N,X), X > 25, X < 30.
```

```Prolog
idade(pedro,35).
idade(ana,30).
idade(paulo,27).
```

Se estivermos trabalhando com **números**, podemos usar estes operadores:

| Operador | Operação |
| -------- | -------- |
| `>` | maior |
| `<` | menor |
| `>=` | maior ou igual |
| `=<` | menor ou igual | 
| `=:=` | igual (numérico) |
| `=\=` | diferente (numérico) |



### Aritmética

- [ ] Aritmética em Prolog usa o operador `is`
- [ ] Esse operador faz com que as expressões aritméticas sejam avaliadas pelo interpretador
- [ ] Sem esse operador, as expressões aritméticas são tratadas como símbolos agrupados, sem execução de cálculos

```Prolog
soma(A,B,C) :- C is A + B.
```

```Prolog
?- soma(1, 4, C).
C = 5
```

> Observe que regras em Prolog se assemelham a procedimentos, não a funções. O resultado do predicado `soma` estará na variável C.


### Regras com aritmética

```Prolog
largura(sala, 4).
comprimento(sala, 5).
largura(quarto, 3).
comprimento(quarto, 2).

area(X,A) :- largura(X,L),
             comprimento(X, C),
             A is L*C.
```

```Prolog
?- area(sala, A).
A = 20

?- area(banheiro, A).
false
```

## Curiosidades

> Aceitando pull requests...

Vamos reunir aqui algumas questões/informações curiosas envolvendo esta linguagem?

### Prolog e Árvores Genealógicas

Um exercício clássico em Prolog, atualizado para novos contextos!

- Árvore genealógica de Game of Thrones: https://www.freecodecamp.org/news/how-to-learn-prolog-by-watching-game-of-thrones-4852ea960017/

- Árvore genealógica de deuses gregos: https://github.com/elc117/t2-2022a-eduardo_gilson

### Code Explanation Tools

- [ ] Ferramentas baseadas em AI têm se dedicado a criar e/ou explicar código 
- [ ] Exemplos: https://denigma.app https://www.explaincode.app
- [ ] Como será que se comportam com alguns exemplos abaixo?

Linguagem C

```C
#include <stdio.h>
int main() {    

    int number1, number2, sum;
    
    number1 = 10;
    number2 = 20;

    sum = number1 + number2;      
    
    printf("%d + %d = %d", number1, number2, sum);
    return 0;
}
```

Linguagem Java

```Java
class Main {

  public static void main(String[] args) {
    
    int first = 10;
    int second = 20;

    int sum = first + second;
    System.out.println(first + " + " + second + " = "  + sum);
  }
}
```

Linguagem Haskell

```Haskell
add :: Int -> Int -> Int
add x y = x + y
```

```Haskell
lcg2 :: Integer -> [Integer]
lcg2 s = map (`div` 2^16) $ tail $ iterate (\n -> (214013 * n + 2531011) `mod` 2^31) s
```

Linguagem Prolog

```Prolog
add(X,Y,S) :- S is X+Y.
```

```Prolog
aluno(joaozinho).
inteligente(X) :- aluno(X).
```


## Bibliografia


- Patrick Blackburn, Johan Bos, and Kristina Striegnitz. [Learn Prolog Now](http://www.learnprolognow.org).
- Markus Triska. [The Power of Prolog](https://www.metalevel.at/prolog).

