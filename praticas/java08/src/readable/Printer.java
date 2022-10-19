// Example from: https://java-programming.mooc.fi/part-9/2-interface

public class Printer {
  public void print(Readable readable) {
      System.out.println(readable.read());
  }
}