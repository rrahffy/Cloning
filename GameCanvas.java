import javax.swing.*;
import java.awt.*;

/**
 * This class extends JComponent and overrides the paintComponent method
 * in order to create the custom drawing for the game.
 * 
 * @author Maria Angelica Muñoz and Rafael Jack Rafanan
 * @version 20 May 2025
 */
public class GameCanvas extends JComponent {
    
    private Player player;
    private Player enemyPlayer;
    private Room currentRoom;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private String gameMessage = "";
    
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
        
        // Draw players if they exist
        if (enemyPlayer != null) {
            enemyPlayer.drawSprite(g2d);
        }
        
        if (player != null) {
            player.drawSprite(g2d);
        }
        
        // Draw game instructions
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Use arrow keys to move. Space to interact.", 10, 20);
    }
}