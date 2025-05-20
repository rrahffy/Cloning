/**
	This is the ending scenes for both players. Depending on who won, different images will be flashed in the finale of the screen.
	
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

import java.awt.image.*;
import java.util.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EndingRoom extends Room{
    private int playerID;

    /**
     * To load which scene will be shown on the ending scene
     * @param playerID To tell which player's scene will be flashed on the screen
     */
    public EndingRoom(int playerID){
        super(loadRoom(playerID));
        this.playerID = playerID;
    }

    /**
     * This loads the room depending on which player won
     * @param playerID To determine which player won and which room to load
     * @return items To create the room based on the ArrayList
     */
    private static ArrayList<Item> loadRoom(int playerID){
        ArrayList<Item> items = new ArrayList<>();
        try{
            BufferedImage bg = ImageIO.read(EndingRoom.class.getResource("Assets/Background.png"));
            BufferedImage ending37 = ImageIO.read(EndingRoom.class.getResource("Assets/A1Ending.png"));
            BufferedImage endingAdmin = ImageIO.read(EndingRoom.class.getResource("Assets/A2Ending.png"));

            items.add(new Item(0, 0, 1024, 768, bg));

            if(playerID == 1){ 
                items.add(new Item(0, 0, 1024, 650, ending37));
            } else if(playerID == 2){
                items.add(new Item(0, 0, 1024, 650, endingAdmin));
            } //since you're not done with player yet, playerIDs are untested

        } catch (IOException e){
            e.printStackTrace();
        } 
        return items;
    }

    /**
     * This is for changing the player ID depending on who won and what will be shown
     * @param changedID This is what the player ID of who won is 
     */
    public void changePlayerID(int changedID){
        this.playerID = changedID;
    }

}