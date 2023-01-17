

# Geração de imagens vetoriais


O objetivo deste trabalho é a criação e a comparação de 2 programas equivalentes, um em Haskell funcional e outro em uma linguagem imperativa. Os programas deverão implementar a geração de imagens vetoriais em formato SVG, com elementos aleatórios.




## Requisitos


1. Este trabalho deve ser desenvolvido **em duplas**.

2. A dupla deverá desenvolver e comparar 2 programas equivalentes, um em Haskell funcional e outro em uma linguagem imperativa (C, Java, C++, Python, ...).

3. A saída dos programas deve ser um arquivo nomeado `output.svg`, contendo strings conforme o formato SVG (*Scalable Vector Graphics*). 

4. Os programas devem implementar uma lógica de geração de um padrão visual composto, por exemplo, de círculos, retângulos, linhas, etc. Cada execução dos programas deve gerar uma imagem distinta, com elementos (cores, posições, quantidades, etc.) definidos com o auxílio de geradores de números (pseudo-)aleatórios no código.

5. No programa em Haskell, a função principal deve ficar dentro de um arquivo `Main.hs`. Este programa deve trabalhar com listas e usar recursos de programação funcional. Operações repetitivas devem ser preferencialmente expressas com funções pré-definidas em Haskell (*higher order* ou outras) e/ou com *list comprehension*.

6. Os códigos devem evitar o uso de valores *hard-coded* para gerar a imagem. Pode-se definir algumas constantes para configurar a imagem e usá-las nas funções de geração.

7. Depois de desenvolver os 2 programas, deve ser feita uma comparação que ressalte semelhanças e diferenças.


## Entrega

- Deadline: 29/01/23
- Repositório de entrega: https://classroom.github.com/a/agUNRyaA
- O repositório de entrega deverá conter:

  - os códigos desenvolvidos
  - um arquivo `README.md` dividido em seções, contendo

    - identificação da dupla
    - exemplos de imagens de saída
    - explicação sobre como executar o programa 
    - análise comparativa dos códigos 
    - créditos para sites/pessoas que ajudaram
  

## Critérios de Avaliação

A avaliação deste trabalho levará em conta os seguintes critérios:

- 70%: códigos desenvolvidos de acordo com os requisitos
- 20%: conteúdo da análise comparativa
- 10%: originalidade/dificuldade do padrão visual escolhido e aprofundamento na programação funcional em Haskell



## Material de apoio

Na sequência estão disponíveis alguns códigos e explicações que podem ser úteis neste trabalho.


### Geração de dados aleatórios


Dados aleatórios são úteis nos mais variados tipos de sofware, então é comum que as linguagens de programação ofereçam formas de gerá-los. 

Em geral, isso é oferecido em bibliotecas por meio de fórmulas que calculam uma série de números a partir de um valor inicial (*seed*) - ou seja, são geradores pseudo-aleatórios, em que é possível predizer a saída para um dado valor inicial.

#### Aleatoriedade e programação funcional

Uma característica da programação funcional é que as funções produzem sempre uma mesma saída para uma dada entrada, sem guardar estado nem alterar argumentos (dados imutáveis). 

Isso pode parecer incompatível com a ideia de geração de dados aleatórios, em que se espera obter valores diferentes a cada execução. No entanto, é possível usar estratégias para obter uma *seed* diferente a cada vez. 

A geração da *seed* geralmente fica escondida em funções que retornam um gerador ou uma sequência de dados aparentemente aleatórios.


#### Usando System.Random

A alternativa mais popular em Haskell para geração de números aleatórios é a biblioteca `System.Random`. 

Esta biblioteca define tipos e funções muito úteis para programas que precisam de dados aleatórios.

Para usá-la, é **IMPORTANTE** saber:

- Esta biblioteca não faz parte da "biblioteca padrão" do Haskell. Pode ser instalada localmente usando ferramentas de gerenciamento de pacotes do Haskell (`stack` ou `cabal`).
- Algumas funções desta biblioteca (por exemplo: `newStdGen`) expressam ações e só podem ser usadas dentro de funções que fazem I/O. Ou seja, não podem ser chamadas dentro de funções puras. Nessas funções ficam "escondidas" as operações para obter uma *seed*.

Documentação da biblioteca: https://hackage.haskell.org/package/random-1.2.1.1

No Repl.it, é possível configurar um projeto para incluir bibliotecas além daquelas instaladas por padrão. O exemplo abaixo é um projeto que usa `System.Random` para gerar uma imagem com um grid de retângulos posicionados e coloridos aleatoriamente. Você pode usar este projeto como ponto de partida para este trabalho.

??[](https://replit.com/@AndreaSchwertne/2022haskell-svg-systemrandom)

**Como o Repl.it permite configurar dependências de projetos?** Usando Nix, um *"purely functional package manager"* (sim, paradigma funcional em ação!). Veja mais nesta entrevista com o CEO do Repl.it: https://semaphoreci.com/blog/amjad-masad-replit

#### Um gerador simples: LCG

Um algoritmo simples para geração de números pseudo-aleatórios é o LCG (*linear congruential generator*). No site [RosettaCode](https://rosettacode.org/wiki/Linear_congruential_generator) há implementações de 2 geradores LCG populares escritos em várias linguagens, incluindo Haskell.


Em [lcgexample.hs](lcgexample.hs) há um exemplo de código com esses geradores, que você pode aproveitar caso esteja implementando este trabalho localmente e não tenham a biblioteca System.Random instalada. 


??[](https://replit.com/@AndreaSchwertne/2022haskell-svg-lcgrandom)










### Links

- [Tutorial SVG](https://www.w3schools.com/graphics/svg_intro.asp) 

  Tutorial SVG no W3Schools

- [Randomness](http://learnyouahaskell.com/input-and-output#randomness)

  Seção sobre geração de dados aleatórios no tutorial Learn You a Haskell for Great Good




