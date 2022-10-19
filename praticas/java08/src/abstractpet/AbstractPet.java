import java.util.ArrayList;


abstract class AbstractPet {
  private int x;
  private int y;
  private String name;

  AbstractPet(String name, int x, int y) {
    this.name = name;
    this.x = x;
    this.y = y;
  }

  public abstract String doingSomething();

  public void whereAreYou() {
    System.out.printf("%s \"%s\" is %s at position (%d,%d)\n", this.getClass().getName(), name, doingSomething(), x, y);
  }
}

class Dog extends AbstractPet {
  Dog(String name, int x, int y) {
    super(name, x, y);
    
  }
  @Override
  public String doingSomething() {
    return "sleeping";
  }
}

class Cat extends AbstractPet {
  Cat(String name, int x, int y) {
    super(name, x, y);
    
  }
  @Override
  public String doingSomething() {
    return "playing";
  }
}


class AbstractPetTest {
  public static void main(String[] args) {

    ArrayList<AbstractPet> list = new ArrayList<AbstractPet>();
    list.add(new Dog("Bartolomeu", 0, 1));
    list.add(new Cat("Edgar", 1, 2));
    for (AbstractPet pet : list) {
      pet.whereAreYou();
    }
  }

}