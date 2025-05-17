/**
	This is the abstract class for all the rooms. It contains what elements are similar in the rooms. 
	
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
import java.util.*;

public abstract class Room{
    private ArrayList<Item> items;

    /**
     * Each room will have an ArrayList of items
     * @param items The items that makes up the room
     */
    public Room(ArrayList<Item> items){
        this.items = items;
    }

    /**
     * For drawing the room's items 
     * @param g Will help draw the images
     */
    public void drawRoom(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for(Item item : items){
            g2d.drawImage(item.getItem(), item.getX(), item.getY(), item.getWidth(), item.getHeight(), null);
        }
    }

    /**
     * Gets the items that makes up the rooms
     * @return items The items inside the ArrayList
     */
    public ArrayList<Item> getItems(){
        return items;
    } 

    /**
     * Updates the rooms based on the new items in it
     * @param newRoomItems What new items will be added
     */
    public void updateRoom(ArrayList<Item> newRoomItems){
        this.items = newRoomItems;
    }

    // public void checkCollision(Player player){

    // }
}
