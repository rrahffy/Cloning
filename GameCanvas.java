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

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JComponent {
    
    private Player player;
    private Player enemyPlayer;
    private Room currentRoom;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private String gameMessage = "";
    
    // Added variables for enemy visibility and interaction
    private boolean enemyVisible = true;
    private boolean enemyInteracting = false;
    private String enemyInteractionTarget = "";
    
    // Added variables for temporary messages
    private String tempMessage = "";
    private long tempMessageEndTime = 0;
    
    /**
     * Constructor for GameCanvas
     * 
     * @param player The player controlled by this client
     * @param enemyPlayer The other player in the game
     * @param room The current room
     */
    public GameCanvas(Player player, Player enemyPlayer, Room room) {
        this.player = player;
        this.enemyPlayer = enemyPlayer;
        this.currentRoom = room;
    }
    
    /**
     * Default constructor
     */
    public GameCanvas() {
        this.player = null;
        this.enemyPlayer = null;
        this.currentRoom = null;
    }
    
    /**
     * Sets the player for this canvas
     * 
     * @param player The player controlled by this client
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Sets the enemy player for this canvas
     * 
     * @param enemyPlayer The other player in the game
     */
    public void setEnemyPlayer(Player enemyPlayer) {
        this.enemyPlayer = enemyPlayer;
    }
    
    /**
     * Sets the current room for this canvas
     * 
     * @param room The current room
     */
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
    
    /**
     * Sets the game started flag
     * 
     * @param started Whether the game has started
     */
    public void setGameStarted(boolean started) {
        this.gameStarted = started;
    }
    
    /**
     * Sets the game ended flag
     * 
     * @param ended Whether the game has ended
     */
    public void setGameEnded(boolean ended) {
        this.gameEnded = ended;
    }
    
    /**
     * Sets the game message to display
     * 
     * @param message The message to display
     */
    public void setGameMessage(String message) {
        this.gameMessage = message;
    }
    
    /**
     * Sets whether the enemy player is visible
     * 
     * @param visible Whether the enemy player is visible
     */
    public void setEnemyVisible(boolean visible) {
        this.enemyVisible = visible;
    }
    
    /**
     * Sets whether the enemy player is interacting with something
     * 
     * @param interacting Whether the enemy is interacting
     * @param targetObject The object the enemy is interacting with
     */
    public void setEnemyInteracting(boolean interacting, String targetObject) {
        this.enemyInteracting = interacting;
        this.enemyInteractionTarget = targetObject;
    }
    
    /**
     * Shows a temporary message for a specified duration
     * 
     * @param message The message to display
     * @param duration The duration in milliseconds to display the message
     */
    public void showMessage(String message, int duration) {
        this.tempMessage = message;
        this.tempMessageEndTime = System.currentTimeMillis() + duration;
        repaint();
    }
    
    /**
     * Overrides the paintComponent method to draw the game
     * 
     * @param g The Graphics object to draw with
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (!gameStarted) {
            // Draw waiting screen
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString("Waiting for another player...", getWidth()/2 - 150, getHeight()/2);
            return;
        }
        
        if (gameEnded) {
            // Draw end game screen
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString(gameMessage, getWidth()/2 - 150, getHeight()/2);
            return;
        }
        
        // Draw the current room
        if (currentRoom != null) {
            currentRoom.drawRoom(g2d);
        }
        
        // Draw enemy player if they exist and are visible
        if (enemyPlayer != null && enemyVisible) {
            enemyPlayer.drawSprite(g2d);
            
            // Draw interaction indicator if enemy is interacting
            if (enemyInteracting) {
                g2d.setColor(Color.YELLOW);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString("Interacting...", 
                              (int)enemyPlayer.getX() - 30, 
                              (int)enemyPlayer.getY() - 20);
            }
        }
        
        // Draw player if they exist
        if (player != null) {
            player.drawSprite(g2d);
        }
        
        // Draw game instructions
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Use arrow keys to move. Space to interact.", 10, 20);
        
        // Draw temporary message if active
        if (!tempMessage.isEmpty() && System.currentTimeMillis() < tempMessageEndTime) {
            g2d.setColor(new Color(0, 0, 0, 180)); // Semi-transparent black
            g2d.fillRoundRect(getWidth()/2 - 150, 50, 300, 40, 10, 10);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString(tempMessage, getWidth()/2 - 140, 75);
        }
    }
}