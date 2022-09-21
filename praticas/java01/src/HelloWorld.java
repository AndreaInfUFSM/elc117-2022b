/** 
 * O que faz este programa ???
 */
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;

class HelloWorld {
  public static void main(String[] args) {
    String[] names = {"Ana Paula", "Anderson", "Gabriel Denardi", "Gabriel Franca",
                      "Gabriel Caetano", "Marcelo", "Miguel", "Alejandro", 
                      "Arthur", "Eduardo", "Felipe Colpo", "Felipe Sanfelice",
                      "Francisco", "Giovanni", "Gleison", "Heitor", "Jean",
                      "Jean", "Joao Marcos", "Leonardo", "Tiago", "Waliston"};
    Random generator = new Random();
    int randomIndex = generator.nextInt(names.length);                      
    System.out.println("Which classmate will get this greeting?");
    System.out.println("Hello " + names[randomIndex] + "!"); 
    
    // System.out.println("Time to greet the whole class!");
    // Collections.shuffle(Arrays.asList(names));
    // for (String name : names) {
    //   System.out.println("Hello " + name + "!");
    // }
  }
}
