// Example from: https://java-programming.mooc.fi/part-9/2-interface

import java.util.ArrayList;

public class Ebook implements Readable {
  private String name;
  private ArrayList<String> pages;
  private int pageNumber;

  public Ebook(String name, ArrayList<String> pages) {
    this.name = name;
    this.pages = pages;
    this.pageNumber = 0;
  }

  public String getName() {
    return this.name;
  }

  public int pages() {
    return this.pages.size();
  }

  public String read() {
    String page = this.pages.get(this.pageNumber);
    nextPage();
    return page;
  }

  private void nextPage() {
    this.pageNumber = this.pageNumber + 1;
    if (this.pageNumber % this.pages.size() == 0) {
      this.pageNumber = 0;
    }
  }
}
