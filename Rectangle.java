import java.awt.*;
import java.awt.geom.*;

public class Rectangle extends Drawing{
    private double x, y, width, height;
    private int borderSize;
    private Color color, borderColor;

    public Rectangle(double x, double y, double width, double height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.borderColor = color;
        this.borderSize = 0; 
    }

    public Rectangle(double x, double y, double width, double height, int borderSize, Color color, Color borderColor){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.borderColor = borderColor;
        this.borderSize = borderSize; 
    }

    public void draw(Graphics2D g2d){
        Rectangle2D.Double rect = new Rectangle2D.Double(x, y, width, height);
        g2d.setStroke(new BasicStroke(borderSize));
        g2d.setColor(borderColor);
        g2d.draw(rect);
        g2d.setColor(color);
        g2d.fill(rect);
    }
}
