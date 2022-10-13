public class Pen {

  private int r, g, b;
  private double width; 
  private boolean isUp;
  
  public Pen() {
    this.r = 255; // red pen is the default
    this.g = 0;
    this.b = 0;  
    this.width = 1;
    this.isUp = false;
  }

  public void setColor(double r, double g, double b) {
    this.r = (int) (255 * Math.min(1, Math.max(r, 0)));
    this.g = (int) (255 * Math.min(1, Math.max(g, 0)));
    this.b = (int) (255 * Math.min(1, Math.max(b, 0)));
  }
  
  public void up() {
    this.isUp = true;
  }

  public void down() {
    this.isUp = false;
  }

  public boolean isUp() {
    return this.isUp;
  }

  public int getRed() {
    return this.r;
  }

  public int getGreen() {
    return this.g;
  }

  public int getBlue() {
    return this.b;
  }
  
  public void setRandomColor() {
    this.r = (int) (256 * Math.random());
		this.g = (int) (256 * Math.random());
		this.b = (int) (256 * Math.random());
  }
  
  public void setStrokeWidth(double width) {
    this.width = width;
  }
  public double getStrokeWidth() {
    return this.width;
  }

}
