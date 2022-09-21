import java.time.LocalDate;

public class TestLanguageRecommendation {
  public static void main(String[] args) {

    LanguageRecommendation lang1 = new LanguageRecommendation("Java", 1995, "andrea");
    System.out.println(lang1.getName() + " is " +
      lang1.yearsOld(LocalDate.now().getYear()) + " years old");

    LanguageRecommendation lang2 = new LanguageRecommendation("Cplusplus", 1985, "andrea");
    System.out.printf("%s is %d years old\n", 
      lang2.getName(),
      lang2.yearsOld(LocalDate.now().getYear()));
      
  }
}
