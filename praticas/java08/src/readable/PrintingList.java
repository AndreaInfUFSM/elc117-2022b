// Example from: https://java-programming.mooc.fi/part-9/2-interface
import java.util.ArrayList;

public class PrintingList {

  public static void main(String[] args) {

    TextMessage message = new TextMessage("me", "Oh wow, this printer knows how to print these as well!");

    ArrayList<String> pages = new ArrayList<>();
    pages.add("Values common to both {1, 3, 5} and {2, 3, 4, 5} are {3, 5}.");
    Ebook book = new Ebook("Introduction to University Mathematics.", pages);
    
    Printer printer = new Printer();
    printer.print(message);
    printer.print(book);
    
  }
}
