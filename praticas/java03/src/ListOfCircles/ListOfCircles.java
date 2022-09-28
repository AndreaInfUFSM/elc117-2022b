import java.util.ArrayList;

public class ListOfCircles {
  // Constants in Java: https://www.baeldung.com/java-constants-good-practices
  private static final int MAX_CIRCLES = 6;

  public static void main(String[] args) {
    ArrayList<Circle> list = new ArrayList<Circle>();
    
    // Long version: create a Point, then a Circle, and then add the Circle to the list
    Point p = new Point(1.5, 1.5);
    Circle c = new Circle(p, 3.5);
    list.add(c);

    for (int i = 0; i < MAX_CIRCLES; i++) {
      // Short version: create and insert objects in a single line
      list.add(new Circle(new Point(i, i+2), i + 3.5));
    }
    // Iterate over the list (long, C-like version)
    System.out.println("---");
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i).area());
    }
    
    System.out.println("---");
    // Iterate over the list (short version)
    for (Circle item : list) {
      System.out.println(item.area());
    }
    
  }
}