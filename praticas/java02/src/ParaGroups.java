import java.util.Collections;
import java.util.Arrays;

class Main {
  public static void main(String[] args) {
    String[] names = {"Alejandro", "Ana Paula", "Anael", "Anderson", 
                      "EduardoLima", "EduardoGehrke", "EduardoSouza", 
                      "FelipeColpo", "FelipeSanfelice", "Francisco", 
                      "GabrielDenardi", "GabrielFrança", "GabrielCaetano", 
                      "Giovanni", "Gleison", "Heitor", "JoaoMarcos", "JulioCesar", 
                      "Leonardo", "Maithe", "Marcelo", "Michael", "Miguel", 
                      "Rodrigo", "Tiago", "Waliston"};
    
    Collections.shuffle(Arrays.asList(names));
    int groups = 0; 
    for (int i = 0; i < names.length; i++) {
      if (i % 2 == 0) System.out.print("\nGrupo " + groups++ + ":");
      System.out.print(" " + names[i]);
    }
    System.out.println();
  }
}
