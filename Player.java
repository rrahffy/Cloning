/**
 * This class contains the code that manages the player's appearance and functionality.
 * It extends PlayerSprite with additional game-specific functionality.
 * 
 * @author Maria Angelica Muñoz and Rafael Jack Rafanan
 * @version 20 May 2025
 */

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Player extends PlayerSprite {
    private int playerID;
    private String name;
    private boolean isAlive;
    private boolean hasKey;
    private Rectangle2D.Double hitBox;
    private double speed;
    private double size; // This was missing proper initialization
    private Room currentRoom;
    
    // Direction tracking
    public enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction currentDirection;
    
    // Player images for different directions
    private BufferedImage imageUp;
    private BufferedImage imageDown;
    private BufferedImage imageLeft;
    private BufferedImage imageRight;
    private boolean useImages = false;

    /**
     * Constructor for the Player class
     * 
     * @param x The x-coordinate of the player
     * @param y The y-coordinate of the player
     * @param size The size of the player sprite
     * @param c The color of the player
     * @param id The player's ID (1 or 2)
     */
    public Player(double x, double y, double size, Color c, int pid) {
        super(x, y, size, c);
        this.playerID = pid;
        this.name = "Player " + pid;
        this.isAlive = true;
        this.hasKey = false;
        this.hitBox = new Rectangle2D.Double(x, y, size, size);
        this.speed = 5.0;
        this.size = size; // Store the size parameter
        this.currentDirection = Direction.DOWN; // Default direction
        
        // Set default player names based on the manual
        if (playerID == 1) {
            this.name = "A1";
        } else if (playerID == 2) {
            this.name = "A2";
        }
        
        // Try to load player images
        try {
            String basePath = "assets/player" + playerID + "_";
            imageUp = ImageIO.read(new File(basePath + "up.png"));
            imageDown = ImageIO.read(new File(basePath + "down.png"));
            imageLeft = ImageIO.read(new File(basePath + "left.png"));
            imageRight = ImageIO.read(new File(basePath + "right.png"));
            useImages = true;
            System.out.println("Player " + playerID + " images loaded successfully");
        } catch (IOException e) {
            System.out.println("Could not load player images. Using colored squares instead.");
            useImages = false;
        }
    }

    /**
     * Moves the player horizontally and updates the hitbox
     * 
     * @param n The distance to move
     */
    @Override
    public void moveH(double n) {
        // Update player direction based on movement
        if (n > 0) {
            currentDirection = Direction.RIGHT;
        } else if (n < 0) {
            currentDirection = Direction.LEFT;
        }
        
        // Check if movement is within bounds of the current room
        if (currentRoom != null) {
            double newX = getX() + n;
            // Basic boundary collision - keep player within window
            if (newX >= 10 && newX <= 980 - getSize()) {
                // Check for collision with room divider
                if ((newX <= 490 && getX() <= 490) || (newX >= 510 && getX() >= 510) || 
                    (getY() >= 580 && getY() <= 680)) {  // Allow passage through the door area
                    super.moveH(n);
                    updateHitBox();
                }
            }
        } else {
            // If no room constraints, just move
            super.moveH(n);
            updateHitBox();
        }
    }

    /**
     * Moves the player vertically and updates the hitbox
     * 
     * @param n The distance to move
     */
    @Override
    public void moveV(double n) {
        // Update player direction based on movement
        if (n > 0) {
            currentDirection = Direction.DOWN;
        } else if (n < 0) {
            currentDirection = Direction.UP;
        }
        
        // Check if movement is within bounds
        if (currentRoom != null) {
            double newY = getY() + n;
            // Basic boundary collision - keep player within window
            if (newY >= 40 && newY <= 580 - getSize()) {
                super.moveV(n);
                updateHitBox();
            }
        } else {
            // If no room constraints, just move
            super.moveV(n);
            updateHitBox();
        }
    }

    /**
     * Updates the position of the player's hitbox
     */
    private void updateHitBox() {
        hitBox.setFrame(getX(), getY(), getSize(), getSize());
    }

    /**
     * Gets the player's ID
     * 
     * @return The player's ID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Gets the player's name
     * 
     * @return The player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name
     * 
     * @param name The new name for the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the player is alive
     * 
     * @return True if the player is alive, false otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the player's alive status
     * 
     * @param isAlive The new alive status
     */
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * Checks if the player has a key
     * 
     * @return True if the player has a key, false otherwise
     */
    public boolean hasKey() {
        return hasKey;
    }

    /**
     * Sets whether the player has a key
     * 
     * @param hasKey True if the player has a key, false otherwise
     */
    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    /**
     * Gets the player's hitbox for collision detection
     * 
     * @return The player's hitbox
     */
    public Rectangle2D.Double getHitBox() {
        return hitBox;
    }

    /**
     * Gets the player's movement speed
     * 
     * @return The player's speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the player's movement speed
     * 
     * @param speed The new speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Sets the current room the player is in
     * 
     * @param room The room the player is in
     */
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    /**
     * Gets the current room the player is in
     * 
     * @return The current room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    @Override
    public double getSize() {
        return this.size;
    }

    /**
     * Gets the player's current direction
     * 
     * @return The player's current direction
     */
    public Direction getDirection() {
        return currentDirection;
    }
    
    /**
     * Sets the player's current direction
     * 
     * @param direction The new direction
     */
    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    /**
     * Overrides the drawSprite method to add player-specific features
     * and directional images
     * 
     * @param g2d The Graphics2D object to draw with
     */
    @Override
    public void drawSprite(Graphics2D g2d) {
        if (useImages) {
            // Draw the appropriate directional image
            BufferedImage currentImage;
            switch (currentDirection) {
                case UP:
                    currentImage = imageUp;
                    break;
                case LEFT:
                    currentImage = imageLeft;
                    break;
                case RIGHT:
                    currentImage = imageRight;
                    break;
                case DOWN:
                default:
                    currentImage = imageDown;
                    break;
            }
            
            g2d.drawImage(currentImage, (int)getX(), (int)getY(), (int)getSize(), (int)getSize(), null);
        } else {
            // Fall back to drawing a colored square if images aren't available
            super.drawSprite(g2d);
        }
        
        // Add player ID label above the player
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString(name, (float)(getX() + getSize()/4), (float)(getY() - 5));
    }

    /**
     * Checks for collision with another player
     * 
     * @param otherPlayer The other player to check collision with
     * @return True if collision detected, false otherwise
     */
    public boolean collidesWith(Player otherPlayer) {
        return hitBox.intersects(otherPlayer.getHitBox());
    }
}