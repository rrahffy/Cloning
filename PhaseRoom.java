/**
	This is the transition scene before the third room. It gives context to the players who their identities are. 
	
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

public class PhaseRoom extends Room{
    private int playerID;

    /**
     * This loads the room that will be shown based on playerID
     * @param playerID To tell which image will be shown to which player
     */
    public PhaseRoom(int playerID){
        super(loadRoom(playerID));
        this.playerID = playerID;
    }

    /**
     * This contains the images on which will be shown to the first or second player to reveal their identity
     * @param playerID To determine which room to load
     * @return items To return the room items when loading
     */
    private static ArrayList<Item> loadRoom(int playerID){
        ArrayList<Item> items = new ArrayList<>();
        try{
            BufferedImage bg = ImageIO.read(PhaseRoom.class.getResource("Assets/Background.png"));
            BufferedImage phase37 = ImageIO.read(PhaseRoom.class.getResource("Assets/37Task.png"));
            BufferedImage phaseAdmin = ImageIO.read(PhaseRoom.class.getResource("Assets/AdministratorTask.png"));

            items.add(new Item(0, 0, 1024, 768, bg));

            if(playerID == 1){ 
                items.add(new Item(0, 0, 1024, 650, phase37));
            } else if(playerID == 2){
                items.add(new Item(0, 0, 1024, 650, phaseAdmin));
            } //since you're not done with player yet, playerIDs are untested

        } catch (IOException e){
            e.printStackTrace();
        } 
        return items;
    }

    /**
     * This is to switch the IDs around for who will get which scene
     * @param changedID This will be what the ID will be changed to
     */
    public void changePlayerID(int changedID){
        this.playerID = changedID;
    }
}
