public class LanguageRecommendation {

  private String name;
  private int firstAppeared;
  private String recommendedBy;

  public LanguageRecommendation(String name, int firstAppeared, String recommendedBy) {
    this.name = name;
    this.firstAppeared = firstAppeared;
    this.recommendedBy = recommendedBy;
    // printing inside a constructor is not a good practice
    // but it can be helpful to understand object creation
    System.out.println("New LanguageRecommendation"); 
  }

  public String getName() {
    return this.name;
  }

  public int getFirstAppeared() {
    return this.firstAppeared;
  }
  
  public String getRecommendedBy() {
    return this.recommendedBy;
  }
 
  public int yearsOld(int year) {
    return year - firstAppeared;
  }

  public static void main(String[] args) {
    LanguageRecommendation lang = new LanguageRecommendation("Java", 1995, "andrea");
    System.out.println(lang.getName() + " is " + lang.yearsOld(2022) + " years old");
  }
}
