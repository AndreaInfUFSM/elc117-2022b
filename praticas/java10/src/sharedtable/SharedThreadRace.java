public class SharedThreadRace {

  private static final int TURNS = 20;
  private static final char[] MARKERS = {'A', 'B', 'C'};

  public static void main(String[] args) throws InterruptedException {
       
    SharedTable table = new SharedTable(TURNS * MARKERS.length);
    Runner[] threads = new Runner[MARKERS.length];

    for (int i = 0; i < MARKERS.length; i++)
      threads[i] = new Runner(table, TURNS, MARKERS[i]);
    
    for (Thread t : threads) 
      t.start();
    
    for (Thread t : threads)  
      t.join();
    

    String strOccur = "";
    for (int i = 0; i < MARKERS.length; i++) {
      strOccur += String.format("%c=%d ",
                  MARKERS[i], table.countOccurrences(MARKERS[i]));
    }
    System.out.println(table);
    System.out.println("There should be " + TURNS + " marks for each Runner!");
    System.out.println("Marks: " + strOccur);

  }
}
