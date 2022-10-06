import java.util.ArrayList;

public class Laboratory {
  private String name;
  private ArrayList<Student> students;
  private ArrayList<Professor> professors;

  public Laboratory() {
  }

  public Laboratory(String name) {
    this.name = name;
    this.students = new ArrayList<Student>();
    this.professors = new ArrayList<Professor>();
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addMember(Student s) {
    this.students.add(s);
  }

  public void addMember(Professor p) {
    this.professors.add(p);
  }
  
  public ArrayList<String> getContactInfos() {
    ArrayList<String> contact = new ArrayList<String>();
    for (Student s: students) {
      contact.add(s.getContactInfo());
    }
    for (Professor p: professors) {
      contact.add(p.getContactInfo());
    }
    return contact;
  }

  public boolean userExists(String userId) {
    for (Student s: students) {
      if (s.getUserId() == userId) {
        return true;
      }
    }
    for (Professor p: professors) {
      if (p.getUserId() == userId) {
        return true;
      }
    }
    return false;
  }

  public int countMembers() {
    return students.size() + professors.size();
  }


}