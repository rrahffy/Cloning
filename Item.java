/**
	Each component in the room is considered an item. This will help set the properties of each item, 
	
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
import java.awt.image.*;

public class Item {
    private int x, y, width, height;
    private BufferedImage roomItem;

    /**
     * This will set position, size, and look of the items
     * @param x The position of the item in the x-axis
     * @param y Its position in the y-axis
     * @param width The item's width
     * @param height The item's height
     * @param roomItem The image that the item will take on 
     */
    public Item(int x, int y, int width, int height, BufferedImage roomItem){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.roomItem = roomItem;
    }

    /**
     * This will draw the item's graphics 
     * @param g2d The pen that will draw the graphics 
     */
    public void draw(Graphics2D g2d){
        if(roomItem != null){
            g2d.drawImage(roomItem, x, y, width, height, null);
        }
    }

    /**
     * Returns the item's x position
     * @return x The position in the x-axis of the item 
     */
    public int getX(){
        return x;
    }

    /**
     * Returns the item's y position 
     * @return y The position in the y-axis of the item 
     */
    public int getY(){
        return y;
    }

    /**
     * Returns the width of the item 
     * @return width The width of the item 
     */
    public int getWidth(){
        return width;
    }

    /**
     * Returns the height of the item 
     * @return height The height of the item 
     */
    public int getHeight(){
        return height;
    }

    /**
     * Returns the item's name and image
     * @return roomItem The item's image name 
     */
    public BufferedImage getItem(){
        return roomItem;
    }
	
    /**
     * To change the item's x position
     * @param x The item's new x position
     */
    public void changeX(int x){
        this.x = x;
    }

    /**
     * To change the item's y position
     * @param y The item's new y position
     */
    public void changeY(int y){
        this.y = y;
    }

    /**
     * To change the item shown
     * @param roomItem The new item shown
     */
    public void changeItem(BufferedImage roomItem){
        this.roomItem = roomItem;
    }
}

