<p align="left"><a href="../README.md"><< Reiniciar</a> | <a href="README02.md">Anterior</a></p>

## Crie seu desenho



1. Crie uma classe `MyTurtle`, derivada de `BaseTurtle`.

2. Implemente um desenho à sua escolha, usando métodos implementados em `BaseTurtle` que representam comandos para a tartaruga (veja a lista abaixo).


### Métodos disponíveis

Os seguintes métodos definem o comportamento da tartaruga:
   - `forward(d)` -- move `d` unidades para frente na direção corrente
   - `back(d)` -- move `d` unidades para trás
   - `moveTo(x,y)` -- move a tartaruga diretamente para o ponto `(x,y)`, sem mudar a direção
   - ``moveBy(x,y)` -- move `x` unidades horizontalmente e `y` unidades verticalmente a partir da posição corrente, sem mudar a direção da tartaruga
   - `turn(ang)` -- rotaciona a tartaruga em `ang` graus (no sentido horário se `ang` for positivo, ou anti-horário se `ang` for negativo
   - `face(ang)` -- aponta a tartaruga na direção dada por `ang` graus, sendo que o valor `0` (zero) equivale a apontar para a direita (na direção positiva do eixo x)
   - `reset()` -- reseta os atributos da tartaruga aos seus valores originais
   - `setDelay(milliseconds)` -- seta um delay após cada movimento da tartaruga 
   - `pause(seconds)` -- pausa a tartaruga por um tempo adicional que se soma ao delay
    
Além disso, a tartaruga tem uma caneta que pode ser manipulada com os seguintes métodos:

   - `down()` - caneta virada para baixo, de forma que a tartaruga deixe um rastro por onde passa
   - `up()` - caneta virada para cima, para que a tartaruga não deixe rastro enquanto se move
   - `setColor(r, g, b)` - seta a cor da caneta a partir dos componentes R, G e B (vermelho, verde e azul). Os parâmetros são números na faixa de 0.0 a 1.0
   - `randomColor()` -- seta a cor da caneta aleatoriamente
   - `setStrokeWidth(w)` -- seta o valor `w` para a espessura do traço da caneta (em pixels)


### Ideias de desenhos

Veja alguns desenhos em: https://math.hws.edu/eck/cs124/f21/lab7/index.html

<p align="right"><a href="README04.md">Terminou? >></a> </p>
