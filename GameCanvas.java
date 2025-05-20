/**
    This class extends JComponent and overrides the paintComponent method to
    render the game state, players, rooms, and UI elements for the game.

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
        
        // Make the message more noticeable with a temporary animation effect
        // This will create a small "popup" effect when messages appear
        javax.swing.Timer animationTimer = new javax.swing.Timer(50, new java.awt.event.ActionListener() {
            private int frameCount = 0;
            private final int maxFrames = 5;
            
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                frameCount++;
                if (frameCount > maxFrames) {
                    ((javax.swing.Timer)e.getSource()).stop();
                }
                repaint(); // Force repaint for animation effect
            }
        });
        animationTimer.start();
        
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
        
        // Draw temporary message with animation if active
        if (!tempMessage.isEmpty() && System.currentTimeMillis() < tempMessageEndTime) {
            // Calculate animation effects - pulse effect for the message box
            long timeRemaining = tempMessageEndTime - System.currentTimeMillis();
            long totalDuration = 2000; // typical duration
            float ratio = Math.min(1.0f, (float)timeRemaining / totalDuration);
            
            // Start with semi-transparent black background
            g2d.setColor(new Color(0, 0, 0, 180)); 
            
            // Draw message box with slight pulsing effect
            int boxWidth = 300;
            int boxHeight = 50;
            int boxX = getWidth()/2 - boxWidth/2;
            int boxY = 50;
            
            // Draw rounded rectangle with slight pulsing effect
            g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);
            
            // Add a colored border based on message type
            if (tempMessage.contains("locked") || tempMessage.contains("Warning")) {
                g2d.setColor(new Color(255, 50, 50, 200)); // Red for warnings
            } else if (tempMessage.contains("solved") || tempMessage.contains("unlocked")) {
                g2d.setColor(new Color(50, 255, 50, 200)); // Green for success
            } else {
                g2d.setColor(new Color(50, 150, 255, 200)); // Blue for information
            }
            
            // Draw the border
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);
            
            // Draw the message text
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            
            // Center the text
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(tempMessage);
            g2d.drawString(tempMessage, getWidth()/2 - textWidth/2, boxY + 30);
        }
    }
}