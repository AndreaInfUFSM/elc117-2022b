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


-->

# OOP em ação: GUI com JavaFX

* Interfaces gráficas (*Graphical User Interfaces* - GUIs) são um ótimo cenário para colocar em ação a **programação orientada a objetos**.

* Por exemplo, podemos ter **classes** representando botões, caixas de texto, etc.  

* Podemos ter vários objetos dessas classes em uma GUI, cada um com seus **atributos** (por exemplo, posição, cor, etc.). Podemos manipular cada objeto usando os **métodos** definidos nas classes. 

* Há muitas opções de toolkits e bibliotecas de classes para criar GUIs:  
    https://en.wikipedia.org/wiki/List_of_widget_toolkits

* Para Java, as 2 opções mais populares são Swing e JavaFX.



Aqui vamos trabalhar com alguns exemplos e exercícios usando JavaFX. 


## JavaFX

JavaFX é um toolkit mais moderno que Swing. Já foi parte do Java SDK, mas seguiu um desenvolvimento paralelo e agora é distribuído separadamente.


Mesmo que hoje em dia haja menos demanda para GUI em desktop (em comparação com interfaces Web), vale a pena praticar com JavaFX para exercitar orientação a objetos.

Há muito material de referência sobre JavaFX. Alguns tutoriais bem completos:

* https://www3.ntu.edu.sg/home/ehchua/programming/java/Javafx1_intro.html

* https://jenkov.com/tutorials/javafx/index.html


### Classes fundamentais

* JavaFX traz uma grande hierarquia de classes que usa todos os recursos de OOP.

* Um programa em JavaFX deve implementar uma classe derivada de `javafx.application.Application`.

* Tipicamente, o programa irá criar uma janela, definida pela classe `javafx.stage.Stage` (JavaFX a metáfora de um "teatro", por isso a janela é um "palco").

* Os componentes da interface (botões, etc) são adicionados a uma "cena" (objeto da classe `javafx.stage.Scene`) associada ao `Stage`.

* O conteúdo de uma "cena" forma uma hierarquia de objetos derivados da classe `javafx.scene.Node`. Alguns "nodos" servem para organizar o **layout** dos componentes na janela e podem conter outros "nodos".


![Fonte: https://www3.ntu.edu.sg/home/ehchua/programming/java/images/JavaFX_StageScene.png](https://www3.ntu.edu.sg/home/ehchua/programming/java/images/JavaFX_StageScene.png)





### Compilação e execução 

* Atualmente, JavaFX é distribuído como uma biblioteca separada do Java SDK.

* Para compilar e executar um programa com JavaFX, é preciso baixar as bibliotecas e indicar ao compilador/JVM onde elas se encontram.

* Fazer isso manualmente é trabalhoso, por isso usamos ferramentas de automação de *build*. 

* No site do JavaFX há instruções detalhadas para diferentes  *build tools* e sistemas operacionais: https://openjfx.io/openjfx-docs/#install-javafx

* Nesta disciplina usaremos Gradle.




## Prática

Nesta prática, vamos executar, examinar e modificar alguns programas em JavaFX.


### Obtenha o código

O çódigo para esta prática está em [praticas/java12/src](https://github.com/AndreaInfUFSM/elc117-2022b/tree/main/praticas/java12/src/).


Obtenha o código como nas outras práticas, atualizando seu fork do repositório da disciplina (ou clonando todo o repositório).



### Execute o primeiro exemplo (hello)


Na pasta  [praticas/java12/src/01-hello/gradle](https://github.com/AndreaInfUFSM/elc117-2022b/tree/main/praticas/java12/src/01-hello/gradle) temos um projeto Gradle configurado para usar JavaFX. 

Para executá-lo, abra um terminal e digite:  


```
./gradlew run
```

O comando acima chama o *Gradle wrapper*, um script que copia as dependências e invoca o Gradle.  Este script tem que ser chamado **dentro** da pasta que contém o projeto. 



### Altere o primeiro exemplo

Para fazer esta parte, você terá que examinar o código fornecido e pesquisar sobre as classes e métodos usados.

No programa `App.java` :

1. Adicione outro botão abaixo do botão existente.
2. Associe um handler ao novo botão para limpar o conteúdo da caixa de texto (setar string vazia).

### Execute o segundo exemplo (fxml)

Na pasta  [praticas/java12/src/02-fxml](https://github.com/AndreaInfUFSM/elc117-2022b/tree/main/praticas/java12/src/02-fxml) temos um projeto com a mesma funcionalidade do anterior, porém organizado em mais arquivos.

Para executá-lo, abra um terminal e digite:  


```
./gradlew run
```

### Examine o segundo exemplo

Este exemplo usa um arquivo em formato FXML (`primary.fxml`) para descrever os componentes da interface.

Este arquivo pode ser gerado com a ferramenta [SceneBuilder](https://gluonhq.com/products/scene-builder/), que tem um editor visual de interfaces gráficas.

``` xml 
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox alignment="CENTER" prefHeight="140.0" prefWidth="479.0" spacing="20.0" xmlns="http://javafx.com/javafx/11.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="hellofx.PrimaryController">
   <padding>
      <Insets bottom="20.0" left="20.0" right="20.0" top="20.0" />
   </padding>
   <children>
      <HBox alignment="CENTER_LEFT" prefHeight="100.0" prefWidth="200.0">
         <children>
            <Label text="Enter your name:">
               <HBox.margin>
                  <Insets right="10.0" />
               </HBox.margin></Label>
            <TextField fx:id="nameText">
               <HBox.margin>
                  <Insets right="10.0" />
               </HBox.margin></TextField>
            <Button mnemonicParsing="false" onAction="#doGreeting" text="Greet Me!" />
         </children>
      </HBox>
      <HBox alignment="CENTER_LEFT" prefHeight="100.0" prefWidth="200.0">
         <children>
            <Label fx:id="greeting" />
         </children>
      </HBox>
   </children>
</VBox>


```


No método `main` em `App.java`, o arquivo FXML é carregado e associado à `Scene`.

``` java
package hellofx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480,160);
        scene.getRoot().setStyle("-fx-font-family: 'sans-serif'");
        stage.setScene(scene);
        stage.setTitle("First FXML Example");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

```


O código que trata eventos na interface fica em uma classe *controller*, localizada em outro arquivo.

``` java 
package hellofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrimaryController {
    @FXML TextField nameText;
    @FXML Label greeting;
    
    @FXML
    void doGreeting(ActionEvent event) {
        String name = nameText.getText();
        String message = "Hello, "+name+". Welcome to JavaFX!";
        greeting.setText(message);
    }
}

```

Usando FXML, o código que descreve os componentes visuais da interface fica separado da lógica da aplicação. Isso é muito conveniente e uma boa prática a seguir em programas maiores.

### Crie um terceiro exemplo

Com base nos exemplos fornecidos, crie um programa com JavaFX para preencher um objeto da classe `Song` da aula anterior.

* O programa deverá ter uma GUI com múltiplos campos de texto (TextField), um Button e um Label.

* Você deverá copiar o arquivo `Song.java` da aula anterior para a mesma pasta que contém a classe principal do seu programa (ajuste o nome do pacote).

* Quando o botão for clicado, o handler deverá ler todos os valores nos campos de texto e usá-los para construir um objeto da classe `Song` (para simplificar, insira somente uma tag na lista de tags do objeto).

* O objeto `Song` criado deverá ser mostrado no Label (representação obtida com `toString()`).

