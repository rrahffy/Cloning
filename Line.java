import java.awt.*;
import java.awt.geom.*;

public class Line extends Drawing{
    private int x1, y1, x2, y2, thickness;
    private Color color;

    public Line(int x1, int y1, int x2, int y2, int thickness, Color color){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.thickness = thickness;
        this.color = color;
    }

    public void draw(Graphics2D g2d){
        Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(color);
        g2d.draw(line);
    }
}
