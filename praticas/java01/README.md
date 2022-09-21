# Prática: Primeiro Contato com Java


Nesta parte da disciplina, vamos inverter a ordem clássica de teoria seguida de prática. Vamos primeiro fazer uma prática com Java, a linguagem orientada a objetos que vamos utilizar, para logo depois entender mais sobre este paradigma de programação.

## Ambiente de desenvolvimento Java


Para fazer estes exercícios, você vai precisar de um "kit" de desenvolvimento Java (Java Development Kit - JDK). Existem diferentes fornecedores e versões. A versão mais recente é a JDK 18, da Oracle, mas recomenda-se usar uma versão *Long-Term-Support* como JDK 11 ou JDK 17. Você encontra essas versões para download aqui: https://www.oracle.com/java/technologies/javase-downloads.html

Você também pode começar usando o ambiente de desenvolvimento Java disponível no Repl.it, mas é recomendável manter uma instalação local do Java para as práticas seguintes. Se você usar o Repl.it, compile e execute os programas usando a aba **Shell**, não a aba Console.

Além disso, você vai precisar de um editor de programas. Para começar, use algum que você já conheça (evite um IDE sofisticado nesta primeira prática).

## Códigos de exemplo

Os códigos das práticas estarão sempre no GitHub. Há várias formas de obtê-los, por exemplo:

Opção 1 (preferível): Em uma console, use cliente Git com interface por linha de comando e faça um clone do repositório da disciplina:
```
git clone https://github.com/AndreaInfUFSM/elc117-2022b/
```

Opção 2 (no desespero!): Pela interface Web do GitHub, navegue até o arquivo desejado e clique em "Raw". Salve o código direto pelo navegador ou use a URL como entrada para um programa de download (por exemplo wget). 

Outras opções incluem: usar a interface de um IDE com suporte a Git (melhor opção a longo prazo), usar um serviço chamado [DownGit](https://downgit.github.io) (no desespero!), etc.

## Exercícios (Parte 1)


1. Abra o programa [HelloWorld.java](src/HelloWorld.java). Dê uma olhada no código. O que será que o programa faz? Que partes do programa você reconhece por semelhança com outras linguagens? O que parece desconhecido?

2. Num **terminal de comandos** (console), compile o programa:

   ```
   javac HelloWorld.java
   ```
   O comando acima produz um arquivo .class para a classe contida em HelloWorld.java. Um arquivo .class contém um código (bytecode) interpretável por uma máquina virtual Java.

3. Execute o programa, informando o **nome da classe que contém o main**:

   ```
   java -cp . HelloWorld
   ```
   Obs.: Se algo der errado e você estiver usando Windows, veja esta [página da Oracle](https://docs.oracle.com/javase/tutorial/getStarted/problems/index.html) sobre problemas comuns e suas soluções. A página é muito antiga, mas muitos problemas se repetem há décadas :smiley:.


## Exercícios (Parte 2)

1. Baixe o programa [LanguageRecommendation.java](src/LanguageRecommendation.java) e analise seu código. Você consegue prever a saída deste programa antes de executá-lo?

2. Compile e execute o programa:
   ```
   javac LanguageRecommendation.java
   java -cp . LanguageRecommendation
   ```

3. Abra o programa [TestLanguageRecommendation.java](src/TestLanguageRecommendation.java). Caso você tenha usado o navegador para baixar o programa, confira se ele está na mesma pasta do programa LanguageRecommendation.java.

4. No programa [TestLanguageRecommendation.java](src/TestLanguageRecommendation.java), inclua a seguinte linha de código ao final do método `main`:
    ```java
    lang2.name = "cplusplus";
    ```
    Compile o programa. Algo vai dar errado... Se o código fosse em C e você quisesse acessar um elemento de um struct, isso estaria certo. Por que será que em Java é diferente?
    

## Exercícios (Parte 3)


Agora aperte o cinto, pois vamos nos aventurar por códigos maiores. Vamos usar Java para comunicação com um **serviço Web sobre recomendações de linguagens**. É um serviço simples que acessa dados públicos nesta [planilha no Google Sheets](https://docs.google.com/spreadsheets/d/1JgvffrFGJc_6XuUmQoPeo75FZXvw1k1nrTxZZFf-dJU/edit?usp=sharing).

1. Baixe o programa [HttpLanguageServiceExample.java](src/HttpLanguageServiceExample.java). Este programa se comunica com o serviço Web fazendo primeiro uma requisição de escrita e depois uma de leitura da planilha.

2. Compile e execute o programa para ver o resultado. 

3. Modifique o programa para enviar sua recomendação de linguagem, com seu nome de usuário no GitHub.


## Após a aula

Agora escolha um IDE de sua preferência e repita a execução dos programas desta prática. Alguns IDEs recomendados são: [Intellij IDEA](https://www.jetbrains.com/idea/download]), VS Code com [Coding Pack for Java](https://code.visualstudio.com/docs/java/java-tutorial#_coding-pack-for-java) ou NetBeans. Usando um IDE, os comandos de compilação e execução ficarão escondidos, mas continuam sendo chamados.
