import java.awt.*;
import java.awt.geom.*;

public class Ellipse extends Drawing{
    private double x, y, width, height; 
    private int borderSize;
    private Color color, borderColor;

    public Ellipse(double x, double y, double width, double height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.borderColor = color;
        this.borderSize = 0;
    }

    public Ellipse(double x, double y, double width, double height, int borderSize, Color color, Color borderColor){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.borderColor = borderColor;
        this.borderSize = borderSize;
    }

    public void draw(Graphics2D g2d){
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, width, height);
        g2d.setStroke(new BasicStroke(borderSize));
        g2d.setColor(borderColor);
        g2d.draw(ellipse);
        g2d.setColor(color);
        g2d.fill(ellipse);
    }
}
