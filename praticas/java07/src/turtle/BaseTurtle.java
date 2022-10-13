/**
 * This class represents a virtual "turtle" which moves around, possibly 
 * leaving a trail as it goes.  The turtle has a pen that can be raised and lowered; 
 * when the pen is down, it leaves a trail.  
 * There are commands for moving the turtle and for telling
 * it which direction to face.  The turtle is shown as a translucent gray
 * triangle pointing in the direction that the turtle is facing.  Any change to the
 * picture produces an automatic repaint of the window .
 * The color and width of the trail left by the turtle can be changed.  
 * For the purposes of the turtle, the coordinate system on the drawing area 
 * extends from -10 at the left  * to 10 at the right and from -10 at the bottom to 10 at the top.  
 * By default, there is a short delay between actions of the
 * turtle, but this delay can be changed by calling the setDelay() method.
 * Source code based on: 
 * https://math.hws.edu/eck/cs124/f21/lab7/lab7-files/Turtle.java
 * The original code was refactored to improve object orientation / separation of concerns.
 */

public class BaseTurtle {

	private static final int DEFAULT_DELAY = 150;
	private double x, y;   // Location of the turtle.
	private double facing; // What direction the turtle is facing, given in degrees.
	private int delay;     // Milliseconds of delay inserted between turtle actions.

	private TurtleView view;
	protected Pen pen;

	public BaseTurtle() {
		this.pen = null;
		this.view = null;
		this.delay = DEFAULT_DELAY;
		this.x = this.y = this.facing = 0;
	}

	public void setView(TurtleView view) {
		this.view = view;
		view.setTurtle(this);
	}

	public void setPen(Pen pen) {
		this.pen = pen;
	}
	/**
	 * Poor BaseTurtle... It draws nothing, but its children will do amazing drawings
	 */
	public void draw() {

	}

	/**
	 * Move the turtle forward in the direction it is facing for a given distance.
	 * (Note: negative distance will make the turtle back up.)
	 */
	protected void forward(double distance) {
		double rad = facing / 180 * Math.PI;
		double dx = Math.cos(rad) * distance;
		double dy = Math.sin(rad) * distance;
		go(x + dx, y + dy);
	}

	/**
	 * Move the turtle in the direction opposite from the direction it is facing
	 * for a given distance. This is the same as forward( -distance ). 
	 * (Note: negative distance will make the turtle move forward.)
	 */
	protected void back(double distance) {
		forward(-distance);
	}

	/**
	 * Move the turtle dx units horizontally and dy units vertically from its
	 * current
	 * position. The turtle's direction is not taken into consideration and does
	 * not change.
	 */
	protected void moveBy(double dx, double dy) {
		go(x + dx, y + dy);
	}

	/**
	 * Move the turtle to the point (x,y). The turtle's direction is not taken into
	 * consideration and does not change.
	 */
	protected void moveTo(double x, double y) {
		go(x, y);
	}


	/**
	 * Turn the turtle through a given angle, specified in degrees.  The angle is just
	 * added to the angle that gives the turtle's direction.  For example, 
	 * turn(90) is a left turn, and turn(180) reverses the direction of the turtle.
	 */
	protected void turn(double angle) {
		facing += angle;
		updateView();

	}

	/**
	 * Turn the turtle so that it faces is in given direction. The angle is given in
	 * degrees. Zero degrees means facing right. Positive angles are
	 * counterclockwise
	 * from that orientation.
	 */
	protected void face(double angle) {
		facing = angle;
		updateView();
	}

	/**
	 * Move the turtle to (0,0), and set its direction angle to 0 (facing right).
	 */
	protected void home() {
		go(0, 0);
		facing = 0;
		updateView();
	}

	/**
	 * Restores the turtle panel to its initial state.
	 * Any drawing is cleared, the turtle is placed at (0,0) facing right, 
	 * the color is set to red, and the line width is set to 1. 
	 * The pen is lowered. Finally, the delay is set to the default.
	 */
	protected void reset() {

		view.reset();
		x = y = facing = 0;
		pen.down();
		pen.setColor(1, 0, 0);
		pen.setStrokeWidth(1);
		delay = DEFAULT_DELAY;
		updateView();
	}


	

	/**
	 * Erase the picture by filling it with white. The turtle does not move
	 * or change direction.
	 */
	public void clear() {
		view.clear();
		updateView();
	}



	/**
	 * Set the delay between actions of the turtle to the specified number of
	 * milliseconds. If the value is less than or equal to zero, there is
	 * no delay. If the delay is very short or zero, multiple turtle actions
	 * can take place between repaint of the drawing area.
	 */
	protected void setDelay(int milliseconds) {
		delay = Math.max(0, milliseconds);
	}

	/**
	 * Causes the program to pause for a specified number of milliseconds. This is
	 * an extra pause, in addition to the default pause between turtle motions.
	 * Calling this method does not change the length of the default delay; to do
	 * that, use setDelay().
	 */
	public void pause(int secondsDelay) {
		doDelay(1000 * secondsDelay);
	}

	/**
	 * Returns the current x-coordinate of the turtle.
	 */
	protected double getX() {
		return x;
	}

	/**
	 * Returns the current y-coordinate of the turtle.
	 */
	protected double getY() {
		return y;
	}

	/**
	 * Returns the direction in which the turtle is facing, as an angle measured in
	 * degrees from the positive x-axis. The value, x, that is returned is greater
	 * than 180 and less than or equal to -180.
	 */
	protected double getHeading() {
		double heading = facing - 360 * Math.floor(facing / 360);
		if (heading > 180)
			heading -= 360;
		return heading;
	}

	public double getFacing() {
		return this.facing;
	}



	/* The private part of the class. */


	/**
	 * Private method used internally to move the turtle from its current position
	 * to (x,y). If the pen is down, a line is drawn between the two points.
	 */
	private void go(double x, double y) {
		if (!pen.isUp()) {
			view.drawLine(pen, this.x, this.y, x, y);
		}
		this.x = x;
		this.y = y;
		//System.out.printf("%1.3f %1.3f %1.3f%n", getX(), getY(), getHeading());
  	updateView();
	
	}

	private void updateView() { 
		view.update();
		doDelay(delay);
	}

	/**
	 * Insert a pause of a specified number of milliseconds into the execution of
	 * the program. If the number of milliseconds is zero or less, there is no
	 * pause. 
	 * This private method is used internally to implement delays.
	 */
	private void doDelay(int millis) {
		if (millis > 0) {
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
			}
		}
	}


}
