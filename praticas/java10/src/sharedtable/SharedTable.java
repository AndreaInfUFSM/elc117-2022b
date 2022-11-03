import java.util.Arrays;
import java.util.stream.Collectors;

public class SharedTable {

  private Character[] table;
  private int index = 0;

  public SharedTable(int n) {
    table = new Character[n];
    Arrays.fill(table, '-');
  }

  public void addMark(char c) {
    table[index] = c;
    spendSomeTime();
    index++;
  }

  // Length of filtered array (functional approach, using stream and lambda)
  public int countOccurrences(char c) {
    return Arrays.asList(table).stream().filter(i -> (i == c)).collect(Collectors.toList()).size();
  }

  // Same as above (imperative approach)
  public int oldCountOccurrences(char c) {
    int count = 0;
    for (int i = 0; i < table.length; i++) {
      if (table[i] == c) {
        count++;
      }
    }
    return count;
  }

  private void spendSomeTime() {
    for (int i = 0; i < 10000; i++) {
      for (int j = 0; j < 100; j++) {
      }
    }
  }

  @Override
  public String toString() {
    return Arrays.stream(table).map(Object::toString).collect(Collectors.joining());
  }
}
