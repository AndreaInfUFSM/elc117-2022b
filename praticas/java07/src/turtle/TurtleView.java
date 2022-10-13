import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import javax.swing.*;

public abstract class TurtleView {

  BaseTurtle turtle;

  public abstract void initialize();

  public abstract void update();

  public abstract void reset();

  public abstract void drawLine(Pen pen, double turtleX, double turtleY, double x, double y);

  public abstract void clear();

  public void setTurtle(BaseTurtle turtle) {
    this.turtle = turtle;
  }

}

class SwingTurtleView extends TurtleView {

  private BufferedImage canvas; // The offscreen, official copy of the picture, without the turtle.
  private Graphics canvasGraphics; // A graphics context for drawing on the canvas.
  private TurtlePanel turtlePanel; // The panel where the drawing is displayed.

  public void clear() {
    Color c = canvasGraphics.getColor();
    canvasGraphics.setColor(Color.WHITE);
    canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    canvasGraphics.setColor(c);
  }

  public void drawLine(Pen pen, double turtleX, double turtleY, double x, double y) {
    canvasGraphics.setColor(new Color(pen.getRed(), pen.getGreen(), pen.getBlue()));
    if (pen.getStrokeWidth() < 2)
      ((Graphics2D) canvasGraphics).setStroke(new BasicStroke((float) pen.getStrokeWidth()));
    else
      ((Graphics2D) canvasGraphics).setStroke(new BasicStroke((float) pen.getStrokeWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    synchronized (turtlePanel) {
      double x1 = (0.5 + turtleX / 20.0) * canvas.getWidth();
      double y1 = (0.5 - turtleY / 20.0) * canvas.getHeight();
      double x2 = (0.5 + x / 20.0) * canvas.getWidth();
      double y2 = (0.5 - y / 20.0) * canvas.getHeight();
      canvasGraphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }
  }


  public void reset() {
    canvasGraphics.setColor(Color.WHITE);
    canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    canvasGraphics.setColor(Color.RED);
    ((Graphics2D) canvasGraphics).setStroke(new BasicStroke(1));
  }

  public void update() {
    turtlePanel.repaint();
  }

  public void initialize() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int maxSize = Math.min(screenSize.width, screenSize.height) - 120;
    int size = Math.min(800, maxSize);
    turtlePanel = new TurtlePanel(size);
    JFrame window = new JFrame("Turtle Land");
    window.setContentPane(turtlePanel);
    window.pack();
    window.setResizable(false);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocation((screenSize.width - window.getWidth()) / 2,
        (screenSize.height - window.getHeight()) / 2);
    window.setVisible(true);

  }

  private class TurtlePanel extends JPanel {

    /**
     * Create a panel for the turtle to draw in. It will be a square with a
     * specified
     * size. The turtle is placed at (0,0), in the center of the panel, facing
     * right.
     * Note that there can only ever be one TurtlePanel, since some of the data
     * that is uses is static.
     */
    public TurtlePanel(int preferredSize) {
      setPreferredSize(new Dimension(preferredSize, preferredSize));
      canvas = new BufferedImage(preferredSize, preferredSize, BufferedImage.TYPE_INT_ARGB);
      canvasGraphics = canvas.createGraphics();
      canvasGraphics.setColor(Color.WHITE);
      canvasGraphics.fillRect(0, 0, preferredSize, preferredSize);
      canvasGraphics.setColor(Color.RED);
      ((Graphics2D) canvasGraphics).setRenderingHint(
          RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * Draw the picture by copying the off-screen canvas onto the panel. If the
     * turtle is visible, draw it on top of the picture from the canvas.
     */
    @Override
    synchronized protected void paintComponent(Graphics g) {
      g.drawImage(canvas, 0, 0, getWidth(), getHeight(), null);
      double turtleX = turtle.getX();
      double turtleY = turtle.getY();
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(new Color(30, 30, 30, 130)); // somewhat transparent dark gray
      Path2D path = new Path2D.Double();
      double rad = turtle.getFacing() / 180 * Math.PI;
      double dx = Math.cos(rad);
      double dy = Math.sin(rad);
      path.moveTo(getWidth() * (0.5 + (turtleX + dx) / 20), getHeight() * (0.5 - (turtleY + dy) / 20));
      path.lineTo(getWidth() * (0.5 + (turtleX - dy / 3) / 20), getHeight() * (0.5 - (turtleY + dx / 3) / 20));
      path.lineTo(getWidth() * (0.5 + (turtleX + dy / 3) / 20), getHeight() * (0.5 - (turtleY - dx / 3) / 20));
      path.closePath();
      g2.fill(path);
    }
  }

}
