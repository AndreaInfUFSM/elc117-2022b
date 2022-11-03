public class Runner extends Thread {

  private SharedTable table;
  private int count;
  private char c;

  Runner(SharedTable table, int count, char c) {
    this.table = table;
    this.count = count;
    this.c = c;
  }

  @Override
  public void run() {
    for (int i = 0; i < count; i++) {
      table.addMark(c);
    }
  }

}
