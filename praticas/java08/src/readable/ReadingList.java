// Example from: https://java-programming.mooc.fi/part-9/2-interface
import java.util.ArrayList;

public class ReadingList {

  public static void main(String[] args) {
    ArrayList<Readable> readingList = new ArrayList<Readable>();

    readingList.add(new TextMessage("me", "never been programming before..."));
    readingList.add(new TextMessage("me", "gonna love it i think!"));
    readingList.add(new TextMessage("me", "give me something more challenging! :)"));
    readingList.add(new TextMessage("me", "you think i can do it?"));
    readingList.add(new TextMessage("me", "up here we send several messages each day"));
    
    
    ArrayList<String> pages = new ArrayList<>();
    pages.add("A method can call itself.");
    readingList.add(new Ebook("Introduction to Recursion.", pages));
    
    for (Readable readable: readingList) {
      System.out.println(readable.read());
    }
    
  }
}
