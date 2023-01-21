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

# Programação Funcional em Haskell (3)




> Este material é uma introdução à **programação funcional** em Haskell.
>
> O conteúdo tem partes interativas e pode ser visualizado de vários modos usando as opções no topo da página.



 
                                                          
                                                          
## Mais sobre listas

- [ ] Relembrando: listas são suportadas nativamente e muito usadas em Haskell
- [ ] Muitas funções manipulam listas existentes: [head](https://downloads.haskell.org/ghc/latest/docs/libraries/base-4.17.0.0/Prelude.html#v:head), [tail](https://downloads.haskell.org/ghc/latest/docs/libraries/base-4.17.0.0/Prelude.html#v:tail), [init](https://downloads.haskell.org/ghc/latest/docs/libraries/base-4.17.0.0/Prelude.html#v:init), [last](https://downloads.haskell.org/ghc/latest/docs/libraries/base-4.17.0.0/Prelude.html#v:last), etc.
- [ ] Haskell também tem recursos sintáticos muito úteis para criar e gerar listas - é o que veremos no restante desta aula


![](http://s3.amazonaws.com/lyah/listmonster.png "List monster by Miran Lipovača")





## Notação `..`

- [ ] Usada entre colchetes
- [ ] Sintaxe para geração de listas em conjuntos ordenados
- [ ] Semelhante a "range" (intervalo) em outras linguagens
- [ ] Usada com valores literais ou expressões

Exemplo no GHCi:

```haskell
> [1..5]
[1,2,3,4,5]
```

Notação conhecida como "Ellipsis notation". Veja mais em:

??[](https://www.wikiwand.com/en/Ellipsis_%28computer_programming%29) 

### Forma `[n..m]`

> Lista de `n` a `m` com passo 1


Exemplos:

```haskell
> [1..5]
[1,2,3,4,5]
> ['a'..'e']
"abcde"
> [5..1]
[]
```

Mais exemplos, agora com números fracionários:

```haskell
> [1.1..5]
[1.1,2.1,3.1,4.1,5.1]
> [1.4..5]
[1.4,2.4,3.4,4.4,5.4]
> [1.5..5]
[1.5,2.5,3.5,4.5,5.5]
> [1.6..5]
[1.6,2.6,3.6,4.6]
> [pi..4]
[3.141592653589793,4.141592653589793]
> [pi+1..5]
[4.141592653589793,5.141592653589793]

```
Note que: 

- o valor final pode não constar na lista gerada - é um limite, considerando arredondamento
- podemos ter funções/expressões de cada lado do `..` (por exemplo, `pi`, `pi+1`)


### Forma `[n,m..p]`

> Lista de `n` a `p`, com passo (`m`-`n`), positivo ou negativo




Exemplos:

```haskell
 [2,4..10]
[2,4,6,8,10]
> [3,6..16]
[3,6,9,12,15]
> [5,4..1]
[5,4,3,2,1]
> [3,2.5..1]
[3.0,2.5,2.0,1.5,1.0]
```

### Exemplos com funções

A notação `..` é muito poderosa e geralmente vai estar dentro de funções.

**Exemplo 1**: lista de `Int`

Arquivo .hs

```haskell
func :: Int -> Int -> [Int]
func x c = [0, x .. x*(c-1)]
```

Execução no GHCi

```haskell
> func 2 5
[0,2,4,6,8]
```
Lista começa em `0`, tem passo `2`  e vai até `2*(5-1)=8`.

---

**Exemplo 2**: lista de `Float`, expressão com tipos diferentes

Arquivo .hs

```haskell
func :: Int -> Float -> [Float]
func c x = [0.0, x .. x*fromIntegral (c-1)]
```

Execução no GHCi

```haskell
> func 5 0.5
[0.0,0.5,1.0,1.5,2.0]
```

> Não podemos misturar `Int` e `Float` numa lista! 

> Função `fromIntegral` é usada para converter um tipo inteiro em um tipo fracionário.


### Conversões de tipos

Em Haskell, as conversões de tipos precisam ser explícitas.

| Funções | Descrição |
|---------|-----------|
| `fromIntegral`, `fromInteger` | Conversão de inteiros para fracionários | 
| `ceiling`, `floor`, `truncate`, `round` | Conversão de números reais fracionários para um tipo inteiro |

Execute-me!


```haskell
mysequence :: Int -> [Float]
mysequence c = [pi .. fromIntegral c * pi]

-- Função principal
main = do
  print (mysequence 2)
  print "Hello"
```
@LIA.haskell()


Outros exemplos no GHCi:

```haskell
> ceiling 7.8
8
> floor 7.8
7
> truncate 7.8
7
> round 7.8
8
> ceiling (-7.8)
-7
> floor (-7.8)
-8
> truncate (-7.8)
-7
> round (-7.8)
-8
```


### Listas infinitas

Haskell permite usar a notação `..` para expressar listas infinitas!

Forma geral: `[n..]` ou `[n,m..]`

**Exemplo 1**: lista infinita com passo `1` (default)

```haskell
> [10..]
[10,11,12,13 ^C Interrupted.
> [0.5..]
[0.5,1.5,2.5 ^C Interrupted.
```
Obs.: No GHCi, é preciso usar Ctrl-C para interromper a geração infinita.

---

**Exemplo 2**: lista infinita com passo `-1` (9-10)


```haskell
> [10,9..]
[10,9,8 ^C Interrupted.
```

---


**Exemplo 3**: usando função [take](https://downloads.haskell.org/ghc/latest/docs/libraries/base-4.17.0.0/Prelude.html#v:take) para obter somente os 5 primeiros elementos da lista infinita


```haskell
Prelude> take 5 [11..]
[11,12,13,14,15]
```

---

Para que serve uma lista infinita?

- Gerar uma lista indefinidamente não parece muito útil...
- Mas geralmente queremos apenas uma parte dela
- Recurso útil para não nos preocuparmos com limites na hora de programar
- Possível porque Haskell tem *lazy evaluation*



## Lazy Evaluation

- Haskell é uma linguagem implementada com *lazy evaluation* (avaliação preguiçosa)
- Técnica que retarda a avaliação de uma expressão até que seu valor seja realmente necessário
- Termos afins: *call-by-need*, *non-strict evaluation*
- Veja mais sobre isso em: https://www.wikiwand.com/en/Lazy_evaluation

Vantagens:

- Desempenho: evita cálculos desnecessários
- Uso de memória: valores criados somente quando necessário
- Poder de expressão da linguagem


![](https://www.thedailystar.net/sites/default/files/styles/very_big_1/public/feature/images/its_hard_work_being_lazy.jpg?)




## List Comprehension

> Inspirado na representação de conjuntos em matemática!

Representação de conjuntos:

-  "por extensão": elementos são enumerados
-  "por compreensão": expressão que descreve os elementos do conjunto

Exemplos em matemática:

- Expressão: `{x | x ∈ {2,3,5,7,11} ∧ x ≥ 4}`

  Conjunto: `{5,7,11}`

- Expressão: `{x + x | x ∈ {2,3,5,7,11} ∧ x ≥ 3  ∧  x﹤7}`

  Conjunto: `{6,10}`


### Sintaxe básica


List comprehensions em Haskell têm esta forma básica:

`[ <expressao> | <lista geradora> ]` 


Exemplo:

```haskell
> [x*2 | x <- [0..9]]
[0,2,4,6,8,10,12,14,16,18]
```

Conjunto compreendido por:

- Valores de `x*2` tal que
- `x` pertence à lista geradora `[0..9]`


> Atenção aos nomes de variáveis! Note que a variável `x` aparece tanto do lado direito como do lado esquerdo do `|`. 


### Equivalente a `map`

Note que:

```haskell
> [x*2 | x <- [0..9]]
[0,2,4,6,8,10,12,14,16,18]
```

Equivale a:

```haskell
> map (*2) [0..9]
[0,2,4,6,8,10,12,14,16,18]
```

Em outras palavras, isso é semelhante a um `map`:

- Para cada `x` pertencente à lista geradora `[0..9]`
- Coloque `x*2` na lista resultante


### Equivalente a `filter`

> Podemos aplicar condições na lista geradora à direita

Note que:

```haskell
> [x | x <- [2,3,5,7,11], x >= 4]
[5,7,11]
```

Equivale a:


```haskell
> filter (>=4) [2,3,5,7,11]
[5,7,11]
```

Em outras palavras, isso é semelhante a um `filter`:

- Para cada `x` pertencente à lista geradora `[2,3,5,7,11]`
- Se `x >= 4`
- Coloque `x` na lista resultante

### Equivalente a laços aninhados

> Podemos ter mais variáveis e mais geradores à direita


```haskell
> [ x+y | x <- [0..2], y <- [10,11]]
[10,11,11,12,12,13]
> [(x,y) | x <- [0..2], y <- [0..2]]
[(0,0),(0,1),(0,2),(1,0),(1,1),(1,2),(2,0),(2,1),(2,2)]
> [(x,y) | x <- [0,1], y <- [0..2]]
[(0,0),(0,1),(0,2),(1,0),(1,1),(1,2)]
```

Isso é equivalente a um produto cartesiano em matemática!

`{(x,y) | x ∈ X ∧ y ∈ Y}`

> Em outras palavras, é equivalente a **laços aninhados**!



### Exemplos com funções

**Exemplo 1**: Equivalente a `filter`, agora dentro de função:

Arquivo .hs:

```haskell
removeChar :: Char -> [Char] -> [Char]
removeChar c str = [x | x <- str, x /= c]
```

No GHCi:

```haskell
> removeChar 'a' "abababa"
"bbb"
```


**Exemplo 2**: Equivalente a `map`, com expressão condicional à esquerda:

Arquivo .hs:

```haskell
filterImg :: [Int] -> [Int]
filterImg bitmap = 
[if pixel < 10 then 0 else pixel | pixel <- bitmap]

```

No GHCi:

```haskell
> filterImg [1,3,4,20,40,40,40,3,2]
[0,0,0,20,40,40,40,0,0]

```


### Resumindo

- Recurso muito poderoso, capaz de expressar sucintamente operações que precisariam de uma ou mais funções
- Uma única notação com várias possibilidades - análogo a um canivete suíço!
- Existe em várias outras linguagens, como Python, por exemplo
- Veja mais aqui: https://en.wikipedia.org/wiki/List_comprehension


![](https://imageengine.victorinox.com/mediahub/33849/560Wx490H/SAK_1_6795__S2.jpg)


## Quiz 

1. Qual o resultado da expressão abaixo executada no GHCi?

   ```haskell
   [2*y | y <- [2..4]]
   ```

   - [( )] [2,3,4]
   - [(x)] [4,6,8]
   - [[?]] A expressão declara uma lista formada por valores de 2*y tal que y pertence a lista [2,3,4].


2. A expressão abaixo é equivalente a aplicar filter em [2..4] e passar o resultado para map (2*y)?

   ```
   [2*y | y <- [2..4], even y]
   ```

   - [(x)] Sim
   - [( )] Não
   - [[?]] Sim, esta list comprehension substitui tanto filter quanto map.

3. Qual será o resultado da expressão abaixo executada no GHCi?

   ```
   zipWith (\x y -> x + y) [1,2,3] (take 3 [11..])
   ```

   - [(x)] [12,14,15]
   - [( )] [11,12,13]
   

4. Que recursos de programação funcional em Haskell estão presentes na expressão abaixo?

   ```
   zipWith (\x y -> x + y) [1,2,3] (take 3 [11..])
   ```

   - [( )] Função de alta ordem, função lambda, list comprehension
   - [(x)] Lista infinita, função lambda, função de alta ordem
   - [[?]] Lista infinita ([11..]), função lambda ((\x y -> x + y)), função de alta ordem (zipWith)



5. A expressão abaixo vai gerar erro se removermos a função `fromIntegral`?

   ```
   [0.1..fromIntegral (length [1,2,3])]
   zipWith (\x y -> x + y) [1,2,3] (take 3 [11..])
   ```

   - [(X)] Sim
   - [( )] Não
   - [[?]] Sim, pois length retorna Int e, sem conversão explícita, a lista não será homogênea.






## Prática

- [Repositório de Entrega](#repositório-de-entrega)
- [Aquecimento](#aquecimento)
- [Funções](#funções)



### Repositório de Entrega


Clique aqui para criar seu repositório desta prática de Haskell: https://classroom.github.com/a/iGjby-S1






### Aquecimento



Estes exercícios não precisam ser entregues. São apenas um aquecimento.


1. Qual será o resultado das expressões abaixo? Tente deduzir e depois confira no interpretador.

   - a) `[x^2+1 | x <- [0..4]]`
   - b) `[x+1 | x <- [5..1]]`
   - c) `[(x,x^2) | x <- take 5 [2,4..]]`
   - d) `[y | (x,y) <- [(0,6.7), (1,5.6)]]`
    

2. As expressões abaixo são um pouco mais complicadas. Tente deduzir o que elas expressam e depois confira o resultado no interpretador. Consulte o Dr. Google sobre as funções Haskell que você não conhece ainda.

   ```haskell
   length [color | (_,_,color) <- [(0,1,"red"),(1,2,"green"), (8,4,"red")], color == "red"]
   ```


   ```haskell
   [ a ++ b | a <- ["lazy","big"], b <- ["frog", "dog"]]
   ```



   ```haskell
   [ (x,y) | x <- [1..5], even x, y <- [(x + 1)..6], odd y ]
   ```


   ```haskell
   concat [ if elem x "aeiou" then [x,'-'] else [x] | x <- "paralelepipedo"]
   ```

   

### Funções



Entregue todos os exercícios abaixo num arquivo nomeado `haskell03.hs`.

1. Usando *list comprehension*, defina uma função `add10toall :: [Int] -> [Int]`, que receba uma lista e adicione o valor 10 a cada elemento dessa lista, produzindo outra lista. Exemplo:

   ```
   > add10toall [0,1,2]
   [10,11,12]
   ```

2. Usando *list comprehension*, defina uma função `multN :: Int -> [Int] -> [Int]`, que receba um número `N` e uma lista, e multiplique cada elemento da lista por `N`, produzindo outra lista.

3. Usando a função de alta ordem `map`, defina a função `multN' :: Int -> [Int] -> [Int]`, equivalente à função do exercício anterior.

4. Usando *list comprehension*, defina uma função `applyExpr :: [Int] -> [Int]`, que receba uma lista e calcule `3*x+2` para cada elemento `x` da lista, produzindo outra lista.

5. Reescreva a função do exercício anterior usando lambda e uma função de alta ordem. A assinatura da função será `applyExpr' :: [Int] -> [Int]`.


6. Usando *list comprehension*, escreva uma função `addSuffix :: String -> [String] -> [String]` , para adicionar um dado sufixo às strings contidas numa lista. Exemplo: 

   ```haskell
   > addSuffix "@inf.ufsm.br" ["fulano","beltrano"]
   ["fulano@inf.ufsm.br","beltrano@inf.ufsm.br]
   ```

7. Usando *list comprehension*, defina uma função `selectgt5 :: [Int] -> [Int]`, que receba uma lista e selecione somente os valores maiores que 5, produzindo outra lista.

8. Usando *list comprehension*, defina uma função `sumOdds :: [Int] -> Int`, que receba uma lista e obtenha o somatório dos valores ímpares, produzindo outra lista. Para auxiliar nesta função, você deverá pesquisar funções pré-definidas em Haskell.

9. Resolva a questão anterior sem usar *list comprehension*, criando a função `sumOdds' :: [Int] -> Int`.

10. Usando *list comprehension*, defina uma função `selectExpr :: [Int] -> [Int]`, que receba uma lista e selecione somente os **valores pares entre 20 e 50, inclusive**, produzindo outra lista.

11. Escreva uma função `countShorts :: [String] -> Int`, que receba uma lista de palavras e retorne a quantidade de palavras dessa lista que possuem menos de 5 caracteres. Use *list comprehension*.


12. Escreva uma função `calcExpr :: [Float] -> [Float]`, que calcule `x^2/2` para cada elemento `x` da lista de entrada e selecione apenas os resultados que forem maiores que 10. Use *list comprehension*.

13. Escreva uma função `trSpaces :: String -> String`, que receba uma string e converta espaços (' ') em traços ('-'). Use *list comprehension*.


14. Usando *list comprehension*, Defina uma função `selectSnd :: [(Int,Int)] -> [Int]`, que receba uma lista de [tuplas](http://learnyouahaskell.com/starting-out#tuples) e selecione somente os segundos elementos dessas tuplas, produzindo outra lista. Exemplo: 

   ```haskell
   > selectSnd [(0,1),(3,4)]
   [1,4]
   ```

15. Em Haskell, a função `zip` combina elementos de duas listas, produzindo uma lista de tuplas. Por exemplo:

   ```haskell
   > zip [1,2] "ab"
   [(1,'a'),(2,'b')]
   > zip [1..] "abcde"
   [(1,'a'),(2,'b'),(3,'c'),(4,'d'),(5,'e')]
   ```

   Usando a função `zip` com *list comprehension* e outras funções auxiliares, escreva uma função `dotProd :: [Int] -> [Int] -> Int` que calcule o somatório dos produtos dos pares de elementos de duas listas, conforme o exemplo:

   ```
   > dotProd [1,1,1,1] [2,2,2,2] -- 1*2 + 1*2 + 1*2 + 1*2
   8
   ```




## Bibliografia


Consulte as seções abaixo no livro [Learn you a Haskell for Great Good!](http://learnyouahaskell.com), de Miran Lipovača:

- [List comprehension](http://learnyouahaskell.com/starting-out#im-a-list-comprehension)
- [Funções de alta ordem (map, filter, etc.)](http://learnyouahaskell.com/higher-order-functions)
- [Funções anônimas](http://learnyouahaskell.com/higher-order-functions#lambdas)
- [Tuplas](http://learnyouahaskell.com/starting-out#tuples) 
