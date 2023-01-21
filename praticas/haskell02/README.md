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

//window.CodeRunner.init("wss://coderunner.informatik.tu-freiberg.de/")
window.CodeRunner.init("wss://ancient-hollows-41316.herokuapp.com/")

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

# Programação Funcional em Haskell (2)




> Este material é uma introdução à **programação funcional** em Haskell.
>
> O conteúdo tem partes interativas e pode ser visualizado de vários modos usando as opções no topo da página.


## Continuando com funções de alta ordem

- [ ] Funções que recebem outras funções como argumento e/ou produzem funções como resultado
- [ ] Exemplos: map, filter, etc.
- [ ] Recurso/conceito característico da programação funcional



![Funções de alta ordem são poderosas!](https://assets-us-01.kc-usercontent.com/500e0a65-283d-00ef-33b2-7f1f20488fe2/a548734c-16ed-4e00-a1c0-ccba351890e5/PR22_3.png "Imagine aqui seu time favorito de super-herois!")

### Função `map`

> Aplica uma dada função a cada elemento de uma lista, construindo outra lista 

```haskell
ghci> f x = x + 1
ghci> map f [0, 5, 8, 3, 2, 1]
[1,6,9,4,3,2]
```
![Função de alta ordem map](https://upload.wikimedia.org/wikipedia/commons/0/06/Mapping-steps-loillibe-new.gif)


Veja mais sobre `map` em outras linguagens: <a href="https://www.wikiwand.com/en/Map_(higher-order_function)">https://www.wikiwand.com/en/Map_(higher-order_function)</a>


### Aplicação parcial / currying

> Podemos usar aplicação parcial junto com funções de alta ordem

```haskell
ghci> add x y = x + y
ghci> map (add 2) [0, 5, 8, 3, 2, 1]
[1,6,9,4,3,2]
```

Isso é possível porque Haskell implementa currying: uma função com múltiplos argumentos equivale a múltiplas funções com um argumento.


Veja mais sobre isso:

??[](https://youtu.be/m12c99qgHBU)


### Função `filter`

> Aplica uma dada função booleana (condição) a cada elemento de uma lista, construindo outra lista contendo somente os elementos que satisfazem a condição

```haskell
ghci> f x = mod x 2 == 0
ghci> filter f [0, 5, 8, 3, 2,1]
[0,8,2]
```
![Função de alta ordem filter](https://upload.wikimedia.org/wikipedia/commons/5/52/Filter-steps-loillierbe.gif)



Veja mais sobre `filter` em outras linguagens: <a href="https://www.wikiwand.com/en/Filter(higher-order_function)">https://www.wikiwand.com/en/Filter_(higher-order_function)</a>



### Exemplo com map e filter

> O que faz o programa abaixo?
> Clique no botão para executá-lo!

``` haskell
import Data.Char
import Text.Printf

isVowel :: Char -> Bool
isVowel c = elem c "aeiouAEIOU"

-- Função principal
main = do
  print "Please type a word:"
  -- putStrLn "Please type a word:"
  word <- getLine
  let nv = length (filter isVowel word)
  print (word ++ " in uppercase is " ++ map toUpper word)
  print (word ++ " has " ++ show nv ++ " vowels")
  printf "%s has %d vowels" word nv 
```
@LIA.haskell()

 --{{0}}--
Observações sobre este código:

 --{{1}}--
Usa `import` dos módulos`Data.Char` e `Text.Printf` para usar as funções `toUpper` e `printf`, respectivamente

 --{{2}}--
Tem uma função `main` que possui um bloco de ações (`do`)

 --{{3}}--
Usa `getLine` para ler uma linha (`String`) da entrada padrão

 --{{4}}--
Usa `let` para definir uma expressão que será usada mais adiante

 --{{5}}--
Usa `++` para concatenar Strings (que são listas de Char)

 --{{6}}--
Usa `show` para obter a representação em `String` de um dado numérico

 --{{7}}--
Ilustra o uso da função `printf`


### Funções `foldr1`/`foldl1`

- [ ] Aplicam função de 2 argumentos sucessivamente aos itens da lista, resultando em um só valor 
- [ ] `foldr1` inicia pela direita da lista, `foldl1` inicia pela esquerda
- [ ] Também chamadas de reduções (`reduce`) em outras linguagens

```haskell
ghci> f x y = x + y
ghci> foldl1 f [2, 3, 4]
9
ghci> foldl1 f [47, 11, 42, 13]
113
```

Veja mais sobre folds/reduce em outras linguagens: <a href="https://www.wikiwand.com/en/Reduce(higher-order_function)">https://www.wikiwand.com/en/Reduce_(higher-order_function)</a>



### Função `zipWith`

- [ ] Aplica uma dada função a cada par de elementos correspondentes, provenientes das 2 listas recebidas, produzindo outra lista
- [ ] Tamanho da lista resultante limitado pela menor lista recebida (restante dos elementos ignorados)

```haskell
ghci> add x y = x + y
ghci> zipWith add [10,11,12] [3,3,3]
[13,14,15]
ghci> zipWith add [10,11,12] [3,3]
[13,14]
ghci> zipWith (-) [10,11,12] [3,3]
[7,8]
ghci> zipWith (+) [10,11,12] [3,3]
[13,14]
```


### Quiz 

1. Se você aplicar `map` a uma lista de 5 elementos, o resultado pode ser uma lista vazia?

   - [( )] Sim
   - [(x)] Não
   - [[?]] Não pode, porque map produz um novo elemento para cada elemento da lista de entrada.


2. A função `map` pode ser aplicada como no exemplo abaixo?

   ```
   add x y = x + y
   map add [1,2,3]
   ```

   - [( )] Sim
   - [(x)] Não
   - [[?]] Não pode, porque map espera uma função que possa ser aplicada a cada elemento da lista, um de cada vez. A função deve precisar de somente um argumento, que virá da lista.

3. A função `map` pode ser aplicada como no exemplo abaixo?

   ```
   add x y = x + y
   map (add 2) [1,2,3]
   ```

   - [(x)] Sim
   - [( )] Não
   - [[?]] Sim, porque neste caso passamos uma aplicação parcial da função add, que já tem o primeiro argumento constante.


4. A função `filter` pode produzir uma lista de tipo diferente da lista passada como argumento?

   - [( )] Sim
   - [(x)] Não
   - [[?]] Não, a função filter apenas seleciona elementos de uma dada lista, não produz novos valores.

5. A função `foldr1` pode receber como argumento a função `sqrt`?

   - [( )] Sim
   - [(x)] Não
   - [[?]] Não, porque a função sqrt recebe apenas um argumento, mas a função foldr1 aplica uma função que recebe 2 argumentos.

6. A função `zipWith` pode receber como argumento a função `subtract`?

   - [(x)] Sim
   - [( )] Não
   - [[?]] Sim, porque a função subtract recebe 2 argumentos.


## Funções anônimas (lambda)


- Sintaxe para definir funções sem nome, anônimas

  ``` haskell
  ghci> (\x -> x^2) 2
  4
  ```

- Também conhecidas como *lambda*
- Servem como:

   - argumento para funções de alta ordem
   - resultado de função de alta ordem






### Sintaxe geral

Sintaxe geral para definir uma função lambda:

`(\<arg1> ... <argn> -> <expressão usando arg1 ... argn>)`

> O símbolo `\` lembra um lambda no alfabeto grego, né? :-)

Exemplos no ambiente interativo GHCi:

Sem lambda

``` haskell
ghci> square x = x^2
ghci> map square [1,2,3]
[1,4,9]
```

Com lambda

``` haskell
ghci> map (\x -> x^2) [1,2,3]
[1,4,9]
```

### Reescrevendo funções com lambda



| Sem lambda | Com lambda |
| ---- | --------- | 
| `add2 x = x + 2` | `(\x -> x + 2)` | 
| `map add2 [1,2]` | `map (\x -> x + 2) [1,2]`  | 
| `gt60 a = a > 60` | `(\a -> a > 60)` | 
| `filter gt60 [10,65]` | `filter (\a -> a > 60) [10,65]` |
| `add x y = x + y` | `(\x y -> x + y)` |
| `foldr1 add [1,2]` | `foldr1 (\x y -> x + y) [1,2]` |
| `zipWith add [1,2] [3,4]` | `zipWith (\x y -> x + y) [1,2] [3,4]` | 


### Em outras linguagens

- Funções lambda fazem parte de muitas linguagens de programação
- Veja mais em: https://en.wikipedia.org/wiki/Anonymous_function

??[](https://en.wikipedia.org/wiki/Anonymous_function)

## Prática


### Aquecimento

- [Usando o Repl.it](#usando-o-repl.it)
- [Aplicando funções](#aplicando-funções)
- [Novidade: Tuplas](#novidade:-tuplas)

#### Usando o Repl.it

> Vamos usar o Repl.it de uma forma diferente hoje!

- Faça fork deste repositório de exemplos no Repl.it:  https://replit.com/@AndreaSchwertne/2022haskell02

- Você não vai usar o botão "Run" nem a aba "Console" do Repl.it. Vamos usar a aba "Shell", que é um terminal de comandos Linux.

- Para **executar** funções que estão em um arquivo, ele precisa antes ser **carregado** no GHCi. 

- Para carregar um arquivo no GHCi, digite por exemplo: 

  `ghci haskell02warmup.hs`. 

- Sempre que você alterar o arquivo `.hs`, você precisará carregá-lo novamente no GHCi. 

- Alguns comandos úteis:

  - Para carregar um programa depois de abrir o GHCi:

    `ghci> :load Main.hs` ou `ghci> :l Main.hs`.

  - Para sair do interpretador: 

    `ghci> :q` ou `Ctrl-D`.

  - Setas Up e Down ajudam a acessar o histórico de comandos para não ter que redigitá-los.



#### Aplicando funções

1. No seu fork do repositório do Repl.it, abra o programa `haskell02warmup.hs` , que contém este código:

   ```haskell  
   square :: Int -> Int
   square x = x^2
  
   squareAll :: [Int] -> [Int]
   squareAll lis = map square lis 
   
   useHaskell :: String -> String
   useHaskell you = "Hey " ++ you ++ ", use Haskell!"
   
   beHappy :: [String] -> [String]
   beHappy people = map useHaskell people
   
   doctor :: String -> Bool
   doctor nome = (take 2 nome) == "Dr"
   
   adult :: Int -> Bool
   adult age = age >= 18
   ```

2. Qual será o resultado das aplicações de funções abaixo? Confira no interpretador.

   - `squareAll [1,2,3]`
   - `filter (>10) (squareAll [2, 3, 4])`
   - `filter (\x -> square x > 10 && square x < 30) [1, 2, 3, 4, 5, 6]`
   - `beHappy ["Harry", "Hermione"]`
   - `filter adult [4, 10, 18, 24, 9]`
   - `filter doctor ["Mr. Hyde", "Dr. Jekyll", "Dr. Octopus"]`
   - `map (\s -> s ++ ", use Haskell!") (filter doctor ["Mr. Hyde", "Dr. Jekyll"])`
 


#### Novidade: Tuplas

Abaixo temos operações **tuplas** em Haskell. 

```haskell
> fst ("Fulano",32)
"Fulano"
> snd ("Fulano",32)
32
```

Tuplas agrupam dados que podem ser de diferentes tipos, em um único "registro". É algo um pouco semelhante a `struct` em C. 

Nos exemplos abaixo, temos 2 tuplas de tipo `(String, Int)`. Uma lista com estas tuplas tem tipo `[(String,Int)]`. Você consegue deduzir o resultado de cada linha abaixo? 

```haskell
> filter (\(_,age) -> age > 60) [("Fulano", 32),("Beltrano", 64)] -- aqui temos 2 tuplas (String,Int)
> beHappy (map (\(name,_) -> name) [("Fulano", 32),("Beltrano", 64)]) 
```

### Repositório de entrega


Até então, você está trabalhando com um fork do repositório de exemplos no Repl.it.

Para entregar seus exercícios, você vai trabalhar com um repositório de entrega no GitHub - que pode ou não ficar vinculado ao Repl.it. Se você vincular sua conta no Repl.it à sua conta no GitHub, os arquivos da prática poderão ser enviados ao GitHub diretamente pelo Repl.it.


Instruções:

1. Clique aqui para criar seu repositório GitHub desta prática de Haskell: https://classroom.github.com/a/PnB1O2zW

2. Para vincular sua conta no Repl.it à sua conta no GitHub, veja estas instruções: https://docs.replit.com/category/using-git-on-replit

3. Se você não quiser vincular as contas, faça seus exercícios em um repositório do Repl.it, depois faça download dos arquivos e envie-os ao GitHub usando comandos git ou a interface web.


### Criando funções 

Entregue estes exercícios no arquivo `haskell02functions.hs`.


1. Médicos consideram que um indivíduo tem febre quando sua [temperatura corporal está acima de 37,8oC](https://drauziovarella.uol.com.br/doencas-e-sintomas/febre/). Escreva uma função `hasFever :: [Float] -> [Float]` que, dada uma lista de temperaturas de indivíduos, selecione aquelas que representam febre. Resolva esta questão definindo uma função auxiliar nomeada, que verifica se uma dada temperatura é febre ou não.

2. Escreva uma função `hasFever' :: [Float] -> [Float]` que resolva a questão anterior usando lambda.

3. Crie uma função `itemize :: [String] -> [String]` que receba uma lista de strings e adicione tags HTML `<li>` e `</li>` antes e depois de cada string. Resolva esta questão usando lambda.

4. Escreva uma função `bigCircles :: Float -> [Float] -> [Float]` que receba um número e uma lista de raios de círculos. Essa função deverá retornar somente aqueles raios de círculos cuja área seja maior que o número passado como argumento.

5. Escreva uma função `quarentine :: [(String,Float)] -> [(String,Float)]` que receba uma lista de tuplas com nomes de pessoas e suas temperaturas corpóreas, e selecione aquelas que têm febre.

6. Escreva uma função `agesIn :: [Int] -> Int -> [Int]` que receba uma lista de anos de nascimento de algumas pessoas e um ano de referência. A lista resultante terá idades calculadas considerando o ano de referência (idades aproximadas, já que só consideram o ano, não a data completa de nascimento). Resolva esta questão usando **lambda**.

7. Escreva uma função `superNames :: [String] -> [String]` que receba uma lista de nomes e adicione o prefixo "Super " às strings que começarem com a letra `A` (maiúscula), deixando as demais inalteradas. A lista resultante, portanto, terá a mesma quantidade de strings da lista original.

8. Escreva uma função `onlyShorts :: [String] -> [String]` que receba uma lista de strings e retorne outra lista contendo somente as strings cujo tamanho seja menor que 5.

### Alterando um Programa


Agora vamos trabalhar com um programa em Haskell que gera uma imagem em formato SVG, com 2 círculos. 

Você vai modificar um código fornecido para gerar uma imagem diferente.

1. Abra o programa `haskell02svg.hs` que está no repositório no Repl.it.

2. Execute o programa abrindo-o no GHCi e chamando a função `main`

3. Veja que o programa cria um arquivo `circles.svg`. Clique neste arquivo para ver a figura gerada.

4. Veja a sintaxe para definir um círculo em SVG: https://www.w3schools.com/graphics/svg_circle.asp 


5. Analise o código do programa e identifique os recursos de Haskell que você já conhece e as novidades que apareceram. 

6. Identifique as linhas de código que criam círculos com diferentes características.

7. Modifique o código para gerar uma imagem diferente, com vários círculos com outras características. Confira a sintaxe para definir um [círculo em SVG](https://www.w3schools.com/graphics/svg_circle.asp).

8. Entregue o arquivo `haskell02svg.hs` modificado e a figura `circles.svg` gerada pelo seu programa. 




## Bibliografia


Consulte as seções abaixo no livro [Learn you a Haskell for Great Good!](http://learnyouahaskell.com), de Miran Lipovača:

- [Funções de alta ordem (map, filter, etc.)](http://learnyouahaskell.com/higher-order-functions) 
- [Funções anônimas](http://learnyouahaskell.com/higher-order-functions#lambdas)
- [Tuplas](http://learnyouahaskell.com/starting-out) (procure por Tuples)

