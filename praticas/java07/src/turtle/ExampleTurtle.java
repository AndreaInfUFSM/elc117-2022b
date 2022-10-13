public class ExampleTurtle extends BaseTurtle {



	public void draw() {

  	//----------- Example 1:  A triangle ---------------

		this.back(4);			// back up 4 units to put the triangle at the center of the base
		this.forward(8); 	// draw first side (using default one-pixel-wide red line)
		this.turn(120); 	// rotate 120 degrees
		pen.setColor(0, 0, 1); // the next side will be blue.
		this.forward(8); 	// draw the second side
		this.turn(120);
		pen.setColor(0, 1, 0); // the last side will be green.
		this.forward(8); 	// draw the third side
		this.turn(120);
		

		this.pause(2); 	// pause for two seconds
		this.reset(); 	// return to initial state


		//------------ Example 2: Many squares ------------------

		this.setDelay(20);  // Use a shorter delay so it draws faster.
		pen.setColor(0,0,0);
		for (int i = 0; i < 60; i++) {
			this.square(7);	// Draw a square (NOT a built-in turtle command; t's defined later in this file!).
			this.turn(6);  	// Turn 6 degrees before drawing the next square.
		}

		this.pause(2); 	// pause for two seconds
		this.reset(); 	// return to initial state


		//------------- Example 3: Many lines ---------------

		this.setDelay(100);  // Restore delay to 100 milliseconds.
		pen.setStrokeWidth(10);
		this.face(0);  // Make sure turtle is facing to the right.
		for (int i = -9; i <= 9; i++) {
			// Draw a wide, randomly colored line from (-9,i) to (9,i).
			pen.setRandomColor();
			pen.up();  // Turtle won't draw a line as it moves.
			this.moveTo(-9, i);
			pen.down();  // Resume drawing.
			this.forward(18);
		}

	}


	private void square(double size) {
		for (int i = 0; i < 4; i++) {
			this.forward(size);
			this.turn(90);
		}
	}


	
}
