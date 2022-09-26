import java.util.Collections;
import java.util.Arrays;

class Main {
  public static void main(String[] args) {
    String[] names = {"anaPMD", "anaelDSW", "andersonDC", "gabrielAD", "gabrielDSF", "gabrielVSC", "maitheSaldanhaFerrao", "marceloDSDA", "miguelJSDV", "rodrigoAS", "alejandroVTB", "eduardoMachadoDeLima", "eduardoRafaelGehrke", "eduardoRequiaSouza", "felipeColpoBagesteiro", "felipePeripolliSanfelice", "franciscoAlbrechtRibas", "giovanniRomanCacioli", "gleisonAntonioPiresDaSilva", "heitorArgentaPreigschadt", "joaoMarcosWilhelmsFrigo", "julioCesarAlvesSilvaDaSilva", "leonardoPiekalaSevero", "michaelCezarStrahl", "tiagoTrindadeMacedo", "walistonEuripedesDosSantos"};
    
    Collections.shuffle(Arrays.asList(names));
    int groups = 0; 
    for (int i = 0; i < names.length; i++) {
      if (i % 2 == 0) System.out.print("\nGrupo " + groups++ + ":");
      System.out.print(" " + names[i]);
    }
  }
}
