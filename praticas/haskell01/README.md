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

# Programação Funcional em Haskell


![Haskell Logo](https://www.haskell.org/img/haskell-logo.svg)




> Este material é uma introdução à **programação funcional** em Haskell.
>
> O conteúdo tem partes interativas e pode ser visualizado de vários modos usando as opções no topo da página.



## Highlights da linguagem

Alguns destaques da linguagem (contraste com C):

- [ ] Criada em ~1987, vários autores
- [ ] Linguagem puramente funcional (funções sem efeitos colaterais)
- [ ] Fortemente tipada, mas com inferência de tipos
- [ ] Suporta listas nativamente (estrutura/tipo de dados nativa)
- [ ] Ambiente de execução: compilador e ambiente interativo (console)

![Evolução de algumas linguagens de programação](https://www.digibarn.com/collections/posters/tongues/ComputerLanguagesChart.png)

## Ambiente de execução


Para instalação local

- [ ] Download oficial em https://www.haskell.org/downloads/
- [ ] Compilador GHC, ambiente interativo GHCi, pacotes de bibliotecas, gerenciadores de dependência cabal / stack, etc.  
- [ ] Pode ser assustador para iniciantes :-)


Em nuvem

- [ ] [Repl.it](http://replit.com) é o melhor atualmente 
- [ ] Oferece experiência próxima ao ambiente local
- [ ] Outros ambientes Haskell em nuvem geralmente escondem a console e/ou comandos






## Aplicando funções pré-definidas

- [ ] Programação funcional aplica funções a argumentos (como em matemática)
- [ ] Módulo (biblioteca) default chamado Prelude tem muitas funções pré-definidas


### Sintaxe geral

Sintaxe para aplicar função a argumentos: 

`<func> <arg1> ... <argn>`

Atenção!

- Sem parênteses, sem vírgula
- Parênteses usados para expressar precedência


Exemplos no ambiente interativo GHCi:

``` text
GHCi, version 9.0.2: http://www.haskell.org/ghc/  :? for help
Prelude> sqrt 4
2.0
Prelude> sqrt 4-1
1.0
Prelude> sqrt (4-1)
1.7320508075688772
```
> Veja acima como os parênteses alteram a ordem de precedência das operações!

Mais exemplos:

``` text
Prelude> min 16 8
8
Prelude> 4^2
16
```

### Teste outros exemplos

Clique em "Run" abaixo, depois digite os exemplos 1 a 5, um de cada vez. 

1. `min 9 8`
2. `sqrt (max 9 8)`
3. `min "abc" "def"`
4. `min "abcdef" "def"`
5. `length (min "abcd" "ab")`

> Você consegue descobrir o que fazem estas funções?


<iframe frameborder="0" width="100%" height="500px" src="https://replit.com/@AndreaSchwertne/2022haskell-shell?embed=true"></iframe>


## Definindo funções simples


- [ ] Sintaxe para definir funções é simples, difere bastante da linguagem C
- [ ] Como em C, usamos símbolos para expressar o nome dos argumentos

### Sintaxe geral

Sintaxe geral para definir uma função sem tipo explícito:

`<nomefunc> <arg1> ... <argn> = <expressão>`

Exemplos no ambiente interativo GHCi:

``` text
GHCi, version 9.0.2: http://www.haskell.org/ghc/  :? for help
Prelude> f x = x+4
Prelude> f 3
7
Prelude> f 8.5
12.5
Prelude> f 3.0
7.0
```

> Tipo do argumento `x`não é explícito, está sendo inferido!



### Teste outros exemplos


Clique em "Run" abaixo, depois digite as seguintes definições:

1. `soma x y = x + y`
3. `hipotenusa c1 c2 = sqrt (c1^2+c2^2)`

Em seguida digite as seguintes aplicações das funções:

1. `soma 8 9`
2. `soma 3 1.5`
4. `hipotenusa 2 4`

<iframe frameborder="0" width="100%" height="500px" src="https://replit.com/@AndreaSchwertne/2022haskell-shell?embed=true"></iframe>


### Erros acontecem...


Clique em "Run" abaixo, depois digite as seguintes definições:

1. `inc x = x + 1`
3. `plural word = word ++ "s"`

Em seguida digite as seguintes aplicações que vão gerar erro!

1. `inc "abcd"`
2. `plural 2`

> Você consegue identificar o motivo de cada erro?

<iframe frameborder="0" width="100%" height="500px" src="https://replit.com/@AndreaSchwertne/2022haskell-shell?embed=true"></iframe>



## Funções de alta ordem (higher order)

- [ ] São funções que recebem outras funções como argumento e/ou produzem funções como resultado
- [ ] Muito poderosas pois implementam definições genéricas que podem ser facilmente especializadas
- [ ] Exemplos clássicos: `map` e `filter` (mas existem muitos outros!)


![Funções de alta ordem são poderosas!](https://assets-us-01.kc-usercontent.com/500e0a65-283d-00ef-33b2-7f1f20488fe2/a548734c-16ed-4e00-a1c0-ccba351890e5/PR22_3.png "Imagine aqui seu time favorito de super-herois!")

### Função `map`

- Recebe 2 argumentos: uma função e uma lista
- Aplica a função a cada elemento da lista, inserindo cada resultado na lista resultante
- Função passada como argumento deve ser compatível com elementos da lista 
- Lista resultante terá sempre mesmo tamanho da lista de entrada

Exemplo 1: Função que adiciona uma constante

``` text
GHCi, version 9.0.2: http://www.haskell.org/ghc/  :? for help
Prelude> func x = x + 4
Prelude> func 3
7
Prelude> map func [1,2,3]
[5,6,7]]
```

Exemplo 2: Funções booleanas que comparam caracter com espaço

``` text
GHCi, version 9.0.2: http://www.haskell.org/ghc/  :? for help
Prelude> nospace c = c /= ' '
Prelude> space c = c == ' '
Prelude> nospace 'a'
True
Prelude> nospace ' '
False
Prelude> map nospace "abc"
[True,True,True]
Prelude> map nospace "ha ha"
[True,True,False,True,True]
```
> Tipo `String` em Haskell é equivalente a `[Char]` (lista de caracteres)


### Função `filter`

- Recebe 2 argumentos: uma função booleana (tipo `Bool`, valores `True`/`False`) e uma lista
- Aplica a função a cada elemento da lista, inserindo na lista de saída somente os elementos que resultarem `True`
- Ou seja; é uma função que seleciona elementos que satisfazem uma condição
- Lista resultante terá tamanho igual ou menor ao da lista de entrada

Exemplo: Usando map e filter

``` text
GHCi, version 9.0.2: http://www.haskell.org/ghc/  :? for help
Prelude> let nospace c = c /= ' '
Prelude> filter nospace "ha ha"
"haha"
Prelude> map nospace "ha ha"
[True,True,False,True,True]
```

Exemplo: Usando `:t` para verificar o tipo de uma função

``` text
Prelude> let nospace c = c /= ' '
Prelude> :t nospace
nospace :: Char -> Bool
Prelude> :t map
map :: (a -> b) -> [a] -> [b]
Prelude> :t filter
filter :: (a -> Bool) -> [a] -> [a]
```


## Funções tipadas

- [ ] Programas em Haskell geralmente definem funções tipadas
- [ ] Definições são agrupadas em arquivos com extensão `.hs` (exemplo: `Main.hs`)
- [ ] Arquivo pode ser carregado no ambiente interativo com o comando `ghci Main.hs`


### Sintaxe geral

- [ ] Duas linhas: uma Linha de definição de tipo (signature) e outra para a definição do que a função faz
- [ ] Argumentos devem corresponder à signature (quantidade e tipo dos argumentos)

Exemplos:

``` haskell
-- Eleva um numero ao cubo
-- Aqui temos um comentario!
cube :: Int -> Int
cube x = x^3

-- Eleva um numero ao quadrado
square :: Int -> Int
square x = x^2

-- Verifica se um numero eh par 
-- Ilustra uso de if/then/else para expressar condicional 
-- A funcao 'mod' retorna o resto da divisao inteira
-- A função seguinte apresenta uma versão melhorada
isEven :: Int -> Bool
isEven n = if mod n 2 == 0 then True else False

-- Versão melhorada da função anterior
-- A comparação == resulta True/False, por isso
-- o if-then-else é desnecessário neste caso
isEvenBetter :: Int -> Bool
isEvenBetter n = mod n 2 == 0
```


### Tipos básicos

| Tipo | Descrição | Exemplos de valores |
| ---- | --------- | ------------------- |
| `Bool` | Valores lógicos (verdadeiro/falso) | `True`, `False` |
| `Int` | Inteiros com precisão fixa (com limite superior/inferior) | `0`, `1`, `3`, `-9`, etc. |
| `Integer` | Inteiros com precisão arbitrária | maiores/menores que `Int` |
| `Float` | Reais, precisão simples | `5.5`, `3e-9` (notação científica), etc. |
| `Double` | Reais, precisão dupla | maiores/menores que `Float` |
| `Char` | Caracteres (Unicode) | delimitar com apóstrofe (aspa simples): `'a'`, `'B'`, `'\97'`, etc. |
| `String` | Lista de caracteres, equivalente a `[Char]` | delimitar com aspas duplas: `"abc"`, `""`, etc. |


### Listas

- Em Haskell, uma lista é um conjunto de dados de um mesmo tipo
- Ou seja: em Haskell, listas são **homogêneas**
- Sintaxe: delimitação por colchetes, valores separados por vírgula



Exemplos:

| Valor | Tipo |
| ----- | ------ |
| `[1,2,3,4]` | `[Int]` ou `[Integer]` |
| `['a','b']` | `[Char]` ou `String` |
| `"ab"` (sintaxe abreviada) | `[Char]` ou `String` |
| `[[1,2],[3,4]]` | `[[Int]]` (lista aninhada) |
| `[1.5, 7]` | `[Float]` ou `[Double]` |



### Exemplo com listas e saída

Um programa mais completo, que também faz saída na console:

``` haskell
-- Usa função head (pré-definida)
initial :: String -> Char
initial name = head name

-- Usa função map
allInitials :: [String] -> [Char]
allInitials names = map initial names

-- Função principal
main = do
  print (initial "Andrea")
  print (allInitials ["Fulano", "Beltrano"])
```
@LIA.haskell()

## Prática




### Quickstart


Estes exercícios são apenas para ambientação com a linguagem. Não precisam ser entregues.


Execute este Repl.it para carregar as definições de funções para esta prática:

<iframe frameborder="0" width="100%" height="500px" src="https://replit.com/@AndreaSchwertne/2022haskell-quickstart"></iframe>



Este programa tem vários exemplos da sintaxe de Haskell que possuem equivalentes em C. Será que você consegue entendê-los? (se ligue nos comentários!)

``` haskell
-- Eleva um numero ao quadrado
-- Aqui temos um comentario!
square :: Int -> Int
square x = x^2

-- Verifica se um numero eh par 
-- Ilustra uso de if/then/else para expressar condicional 
-- A funcao 'mod' retorna o resto da divisao inteira
-- A função seguinte apresenta uma versão melhorada
isEven :: Int -> Bool
isEven n = if mod n 2 == 0 then True else False

-- Versão melhorada da função anterior
-- A comparação == resulta True/False, por isso
-- o if-then-else é desnecessário neste caso
isEvenBetter :: Int -> Bool
isEvenBetter n = mod n 2 == 0

-- Gera um numero a partir de um caracter 
-- Note esta estrutura condicional em Haskell, usando 'guardas' (|)
encodeMe :: Char -> Int
encodeMe c 
   | c == 'S'  = 0
   | c == 'N'  = 1
   | otherwise = undefined

-- Calcula o quadrado do primeiro elemento da lista
-- Note que '[Int]' designa uma lista de elementos do tipo Int 
squareFirst :: [Int] -> Int
squareFirst lis = (head lis)^2

-- Verifica se uma palavra tem mais de 10 caracteres
isLongWord :: String -> Bool -- isso é o mesmo que: isLongWord :: [Char] -> Bool
isLongWord s = length s > 10
```   

Na console do Repl.it, teste interativamente as funções em cada um dos casos abaixo. 

- `square 2 + 1`
- `square (2+1)`
- `isEven 8`
- `isEven 9`
- `encodeMe 'S'`
- `squareFirst [-3,4,5]`
- `isLongWord "test"`


Agora teste as aplicações de funções abaixo. Elas vão gerar **erros**. Deduza o motivo em cada caso.

- `sQUARE 2`
- `square 'A'`
- `isEven 8.1`
- `encodeMe "A"`
- `squareFirst []`
- `isLongWord 'test'`


### Repositório de entrega

Os exercícios seguintes deverão ser entregues em 2 arquivos em um repositório no GitHub. 

Clique aqui para criar o repositório de entrega: https://classroom.github.com/a/7IFmVyyW


### Parte 1 - Definindo novas funções

Nos exercícios abaixo, você vai definir **funções tipadas**. 

Esta parte deverá ser entregue em um arquivo nomeado `haskell01parte1.hs`. ATENÇÃO: Nomeie o arquivo exatamente como solicitado, senão a prática não será contabilizada na avaliação!


1. Crie uma função `sumSquares :: Int -> Int -> Int` que receba dois números x e y e calcule a soma dos seus quadrados.

2. Defina a função `circleArea :: Float -> Float` que receba um raio r e calcule a área de um círculo com esse raio, dada por pi vezes o raio ao quadrado. Dica: Haskell tem a função `pi` pré-definida.

3. Defina uma função `age :: Int -> Int -> Int` que receba o ano de nascimento de uma pessoa e o ano atual, produzindo como resultado a idade (aproximada) da pessoa.

4. Defina uma função `isElderly :: Int -> Bool` que receba uma idade e resulte verdadeiro caso a idade seja maior que 65 anos.


5. Defina uma função `htmlItem :: String -> String` que receba uma `String` e adicione tags `<li>` e `</li>` como prefixo e sufixo, respectivamente. Por exemplo, se a entrada for `"abc"`, a saída será `"<li>abc</li>"`. Use o operador `++` para concatenar strings (este operador serve para concatenar quaisquer listas do mesmo tipo).

7. Crie uma função `startsWithA :: String -> Bool` que receba uma string e verifique se ela inicia com o caracter `'A'`.

8. Defina uma função `isVerb :: String -> Bool` que receba uma string e verifique se ela termina com o caracter `'r'`. Antes desse exercício, teste no interpretador a função pré-definida `last`, que retorna o último elemento de uma lista. Dica: conheça também o [list monster](http://s3.amazonaws.com/lyah/listmonster.png), do autor Miran Lipovača :-)

9. Crie uma função `isVowel :: Char -> Bool` que receba um caracter e verifique se ele é uma vogal minúscula.

10. Crie uma função `hasEqHeads :: [Int] -> [Int] -> Bool` que verifique se 2 listas possuem o mesmo primeiro elemento. Use a função `head` e o operador lógico `==` para verificar igualdade.

11. A função pré-definida `elem` recebe um elemento e uma lista, e verifica se o elemento está presente ou não na lista. Teste essa função no interpretador: 
    ```haskell
    elem 3 [1,2,3]
    elem 4 [1,2,3]
    elem 'c' "abcd"
    elem 'A' "abcd"
    ```

    Agora use a função `elem` para criar uma função `isVowel2 :: Char -> Bool` que verifique se um caracter é uma vogal, tanto maiúscula como minúscula.


### Parte 2 - Funções de alta ordem


Para fazer os exercícios abaixo, você vai usar as **funções de alta ordem** `map` e `filter`.

Esta parte deverá ser entregue em um arquivo nomeado `haskell01parte2.hs`.  ATENÇÃO: Nomeie o arquivo exatamente como solicitado, senão a prática não será contabilizada na avaliação!

Muitos desses exercícios também vão usar funções que você definiu na parte anterior desta prática. Essas funções deverão ser copiadas para o novo arquivo.

Sempre que possível, reuse funções já definidas. Quando não houver uma função já definida, você deverá criá-la.


1. Crie uma função `itemize :: [String] -> [String]` que receba uma lista de nomes e aplique a função `htmlItem` em cada nome.

2. Crie uma função `onlyVowels :: String -> String` que receba uma string e retorne outra contendo somente suas vogais. Por exemplo: `onlyVowels "abracadabra"` vai retornar `"aaaaa"`.

3. Escreva uma função `onlyElderly :: [Int] -> [Int]` que, dada uma lista de idades, selecione somente as que forem maiores que 65 anos.

5. Crie uma função `onlyLongWords :: [String] -> [String]` que receba uma lista de strings e retorne somente as strings longas (use a função `isLongWord` definida no código de exemplo no início da prática).

6. Escreva uma função `onlyEven` que receba uma lista de números inteiros e retorne somente aqueles que forem pares. Agora é com você a definição da tipagem da função!

7. Escreva uma função `onlyBetween60and80` que receba uma lista de números e retorne somente os que estiverem entre 60 e 80, inclusive. Você deverá criar uma função auxiliar `between60and80` e usar `&&` para expressar o operador "E" lógico em Haskell.

8. Crie uma função `countSpaces` que receba uma string e retorne o número de espaços nela contidos. Dica 1: você vai precisar de uma função que identifica espaços. Dica 2: aplique funções consecutivamente, isto é, use o resultado de uma função como argumento para outra. 

9. Escreva uma função `calcAreas` que, dada uma lista de valores de raios de círculos, retorne uma lista com a área correspondente a cada raio.




## Bibliografia

- [Learn You a Haskell for Great Good!](http://learnyouahaskell.com/) by Miran Lipovača - Ótimo tutorial sobre Haskell e programação funcional

- [Teach a Kid Functional Programming and You Feed Her for a Lifetime](http://www.huffingtonpost.com/john-pavley/teach-a-kid-functional-pr_b_3666853.html) by John Pavley - Um artigo opinativo para público não especializado



