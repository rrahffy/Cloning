/**
	This class contains the main method that will start the game from the player's side.
    Run class for each player.
	
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

public class GameStarter {
    
    /**
     * Main method to start the game client
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("----- COZEN GAME CLIENT -----");
        System.out.println("Connecting to game server...");
        
        GameFrame gameFrame = new GameFrame(1024, 768);
        gameFrame.connectToServer();  // Connect to server first to get playerID
        // GUI setup will be handled by connectToServer() after getting player ID
    }
}