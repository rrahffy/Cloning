/**
 * This class contains the main method that will start the game from the player's side.
 * 
 * @author Maria Angelica Muñoz and Rafael Jack Rafanan
 * @version 20 May 2025
 */

public class GameStarter {
    
    /**
     * Main method to start the game client
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("----- COZEN GAME CLIENT -----");
        System.out.println("Connecting to game server...");
        
        GameFrame gameFrame = new GameFrame(1024, 768);
        gameFrame.connectToServer();
        gameFrame.setUpGUI();
    }
}