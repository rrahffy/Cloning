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
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.Timer;

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
            if(item.getItem() != null){
                g2d.drawImage(item.getItem(), item.getX(), item.getY(), item.getWidth(), item.getHeight(), null);
            }
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

    /**
     * This translates the item to a new location
     * @param oldPlacement The item's original location
     * @param newX The item's new x position
     * @param newY The item's new y position
     */
    public void translate(BufferedImage oldPlacement, int newX, int newY){
        for(Item item : items){
            if(item.getItem() == oldPlacement){
                item.changeX(newX);
                item.changeY(newY);
                break;
            }
        }
    }

    /**
     * Process interactions with objects in the room
     * @param objectType The type of object to interact with
     * @return true if the interaction was processed, false otherwise
     */
    public boolean processInteraction(String objectType) {
        // Default implementation for room interactions
        System.out.println("Processing interaction with: " + objectType);
        
        switch(objectType) {
            case "bookshelf":
                // Change bookshelf appearance to show it's been examined
                replaceItem("Assets/Bookshelf.png", "Assets/BookshelfSafe.png");
                return true;
                
            case "button":
                // Show button being pressed
                replaceItem("Assets/Button.png", "Assets/ButtonPressed.png");
                // After a delay, change it back
                new Timer(500, e -> {
                    replaceItem("Assets/ButtonPressed.png", "Assets/Button.png");
                    ((Timer)e.getSource()).stop();
                }).start();
                return true;
                
            case "cabinet":
                // Change cabinet appearance to show it's been opened
                replaceItem("Assets/Cabinet.png", "Assets/CabinetOpen.png");
                return true;
                
            case "laptop":
                // Change laptop appearance to show it's been used
                replaceItem("Assets/Laptop.png", "Assets/LaptopOn.png");
                return true;
                
            case "wallscreen":
                // Change wallscreen appearance to show it's activated
                replaceItem("Assets/WallScreen.png", "Assets/WallScreenActive.png");
                return true;
                
            case "hologram":
                // Change hologram appearance to show interaction
                replaceItem("Assets/Hologram.png", "Assets/HologramActive.png"); 
                return true;
                
            default:
                return false;
        }
    }

    /**
     * Helper method to replace an item based on its image filename
     * 
     * @param oldImageName The filename of the image to replace
     * @param newImageName The filename of the new image
     * @return true if replacement was successful
     */
    public boolean replaceItem(String oldImageName, String newImageName) {
        try {
            for(Item item : getItems()) {
                if(item.getItem() != null) {
                    String itemPath = item.getItem().toString();
                    if(itemPath.contains(oldImageName)) {
                        item.changeItem(ImageIO.read(getClass().getResource(newImageName)));
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This changes the item's appearance while retaining the location
     * Legacy method - kept for compatibility
     */
    public void replaceItem(){
        try {
            for(Item item : items){
                BufferedImage itemList = item.getItem();
                
                if(itemList != null && itemList.toString().contains("Assets/Bookshelf.png")){
                    item.changeItem(ImageIO.read(getClass().getResource("Assets/BookshelfSafe.png")));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
