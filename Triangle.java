import java.awt.*;
import java.awt.geom.*;

public class Triangle extends Drawing{
    private double x, y, width, height; 
    private int borderSize;
    private Color color, borderColor;

    public Triangle(double x, double y, double width, double height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.borderColor = color;
        this.borderSize = 0; 
    }

    public Triangle(double x, double y, double width, double height, int borderSize, Color color, Color borderColor){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.borderColor = borderColor;
        this.borderSize = borderSize; 
    }

    public void draw(Graphics2D g2d){
        Path2D.Double triangle = new Path2D.Double();
        triangle.moveTo(x, y);
        triangle.lineTo(x + width*4, y);
        triangle.lineTo(x + width*2, y - height*2);
        triangle.closePath();
        g2d.setStroke(new BasicStroke(borderSize));
        g2d.setColor(borderColor);
        g2d.draw(triangle);
        g2d.setColor(color);
        g2d.fill(triangle);
    }
}
