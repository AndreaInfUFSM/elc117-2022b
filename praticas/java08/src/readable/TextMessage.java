// Example from: https://java-programming.mooc.fi/part-9/2-interface

public class TextMessage implements Readable {
  private String sender;
  private String content;

  public TextMessage(String sender, String content) {
    this.sender = sender;
    this.content = content;
  }

  public String getSender() {
    return this.sender;
  }

  public String read() {
    return this.content;
  }
}
