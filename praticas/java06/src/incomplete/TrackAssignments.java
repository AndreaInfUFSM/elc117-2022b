import java.time.LocalDate;
import java.util.ArrayList;

class Assignment {

  protected LocalDate dueDate;
  protected String description;
  protected boolean pending;
  protected LocalDate submitDate;
  
  public Assignment(LocalDate dueDate, String description) {
    this.dueDate = dueDate;
    this.description = description;
    this.submitDate = null;
    this.pending = true;
  }

  public String getDescription() {
    return this.description;
  }

  public boolean isPending() {
    return this.pending;
  }


  public void complete(LocalDate date) {
    this.submitDate = date;
    this.pending = false;
  }


  public int daysLeft() {
    return dueDate.compareTo(LocalDate.now());
  }

  private String status() {
    return "COMPLETE-ME";
  }


  public String message() {
    return "Assignment " + this.description + " is " + status();
    
  }

  

}

class GroupAssignment extends Assignment {
  private String teamMates;

  public String message() {
    return "COMPLETE-ME";
  
  }

  public GroupAssignment(LocalDate dueDate, String description, String teamMates) {
    super(dueDate, description);
    this.teamMates = teamMates;
  }

  
}

public class TrackAssignments {
  public static void main(String[] args) {
    
    ArrayList<Assignment> list = new ArrayList<Assignment>();
    list.add(new GroupAssignment(LocalDate.of(2022,9,15), "jamboard", "teamMate1, teamMate2"));
    list.add(new Assignment(LocalDate.of(2022,9,22),"java01"));
    list.add(new GroupAssignment(LocalDate.of(2022,9,27), "java02", "teamMate1"));
    list.add(new GroupAssignment(LocalDate.of(2022,10,13), "java06", "teamMate1"));


    System.out.println("\n==> Printing all assignment **OBJECTS**:");
    for (Assignment item : list) {
      System.out.println(item);
    }


    System.out.println("\n==> Printing all assignment **MESSAGES**:");
    for (Assignment item : list) {
      System.out.println(item.message());
    }

    for (Assignment item : list) {
      if (item.getDescription() == "jamboard") {
        item.complete(LocalDate.now());
      }
    }

    System.out.println("\n==> Printing all assignment messages **AGAIN**:");
    for (Assignment item : list) {
      System.out.println(item.message());
    }

    // COMPLETE-ME: count completed assignments
  }
  
}
