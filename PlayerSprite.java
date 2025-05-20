/**
	This is where the basic functions for Player are.
	
	@author Maria Angelica Muñoz (243172) and Rafael Jack Rafanan (246338)
	@version 20 May 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import java.awt.*;
import java.awt.geom.*;

public class PlayerSprite {

    protected double x, y, size;
    private Color color;

    public PlayerSprite(double a, double b, double s, Color c) {
        x = a;
        y = b;
        size = s;
        color = c;
    }

    public void drawSprite(Graphics2D g2d) {
        Rectangle2D.Double square = new Rectangle2D.Double(x,y,size,size);
        g2d.setColor(color);
        g2d.fill(square);
    }

    public void moveH(double n) {
        x +=  n;
    }
    
    public void moveV(double n) {
        y += n;
    }
    public void setX(double n) {
        x =  n;
    }

    public void setY(double n) {
        y =  n;
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
}