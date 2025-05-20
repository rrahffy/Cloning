/**
 * This class manages the creation, loading, and transitions between game rooms.
 * It encapsulates all room-related logic.
 *
 * @author Maria Angelica Muñoz (243172) and Rafael Jack Rafanan (246338)
 * @version 20 May 2025
 *
 * I have not discussed the Java language code in my program 
 * with anyone other than my instructor or the teaching assistants 
 * assigned to this course.
 *
 * I have not used Java language code obtained from another student, 
 * or any other unauthorized source, either modified or unmodified.
 *
 * If any Java language code or documentation used in my program 
 * was obtained from another source, such as a textbook or website, 
 * that has been clearly noted with a proper citation in the comments 
 * of my program.
**/

import java.util.ArrayList;

public class RoomManager {
    // List of rooms in order of progression
    private String[] roomNames = {
        "ExperimentRoom", "CloningRoom", "PhaseRoom", "TheresholdRoom", "EndingRoom"
    };
    
    private Room currentRoom;
    private int currentRoomIndex = 0;
    private boolean isInTransition = false;
    private Player player;
    private int playerID;
    private int width, height;
    
    /**
     * Constructor for RoomManager
     * 
     * @param player The player object to manage position when changing rooms
     * @param playerID The ID of the player
     * @param width The width of the game window
     * @param height The height of the game window
     */
    public RoomManager(Player player, int playerID, int width, int height) {
        this.player = player;
        this.playerID = playerID;
        this.width = width;
        this.height = height;
        loadCurrentRoom();
    }
    
    /**
     * Loads the current room based on currentRoomIndex
     */
    public void loadCurrentRoom() {
        // Save player position if needed for room transitions
        double playerX = 0;
        double playerY = 0;
        
        if (player != null) {
            playerX = player.getX();
            playerY = player.getY();
        }
        
        // Create the appropriate room based on index
        switch (roomNames[currentRoomIndex]) {
            case "ExperimentRoom":
                currentRoom = new ExperimentRoom();
                break;
            case "CloningRoom":
                currentRoom = new CloningRoom();
                break;
            case "PhaseRoom":
                currentRoom = new PhaseRoom(playerID);
                break;
            case "TheresholdRoom":
                currentRoom = new ThresholdRoom();
                break;
            case "EndingRoom":
                currentRoom = new EndingRoom(playerID);
                break;
            default:
                currentRoom = new ExperimentRoom();
        }
        
        // Set the player's current room and position them appropriately
        if (player != null) {
            player.setCurrentRoom(currentRoom);
            
            // If this is a room transition, position player appropriately
            if (isInTransition) {
                // Position the player near the entry point of the new room
                if (currentRoomIndex > 0) {
                    // Coming from previous room - position at entry door
                    player.setX(width / 2);
                    player.setY(550); // Position near the bottom of the screen
                } else {
                    // First room or special case - use default position
                    player.setX(playerX);
                    player.setY(playerY);
                }
                isInTransition = false;
            }
        }
    }
    
    /**
     * Moves to the next room
     * 
     * @return The index of the new room
     */
    public int goToNextRoom() {
        currentRoomIndex++;
        if (currentRoomIndex >= roomNames.length) {
            currentRoomIndex = roomNames.length - 1;
        }
        
        isInTransition = true;
        loadCurrentRoom();
        
        return currentRoomIndex;
    }
    
    /**
     * Change to a specific room
     * 
     * @param roomIndex The index of the room to change to
     * @return true if the room change was successful, false otherwise
     */
    public boolean changeRoom(int roomIndex) {
        if (roomIndex >= 0 && roomIndex < roomNames.length) {
            currentRoomIndex = roomIndex;
            isInTransition = true;
            loadCurrentRoom();
            return true;
        }
        return false;
    }
    
    /**
     * Updates the EndingRoom winner ID when the game ends
     * 
     * @param winnerID The ID of the winning player
     */
    public void updateEndingRoom(int winnerID) {
        if (currentRoom instanceof EndingRoom) {
            EndingRoom endingRoom = (EndingRoom) currentRoom;
            endingRoom.changePlayerID(winnerID);
        }
    }
    
    /**
     * Checks if the player is near a door and can interact with it
     * 
     * @param playerX Player's X position
     * @param playerY Player's Y position
     * @param interacting Whether the player is attempting to interact
     * @return The type of door interaction or null if none
     */
    public String checkDoorInteractions(double playerX, double playerY, boolean interacting) {
        if (!interacting) return null;
        
        // Only check door interactions in ExperimentRoom and CloningRoom
        // For the TheresholdRoom, the exit door is special and handled differently
        if (currentRoomIndex > 1) {
            // Check for exit door in TheresholdRoom
            if (currentRoom instanceof ThresholdRoom) {
                // Check if player is near the exit door
                if (playerY >= 480 && playerY <= 580 &&
                    playerX >= 810 && playerX <= 900) {
                    return "exit";
                }
            }
            return null;
        }
        
        // Check if player is in door area (common for both rooms)
        if (playerY >= 480 && playerY <= 580) {  // Adjusted Y coordinates to be higher up
            double doorWidth = 50;
            double leftDoorX = 480 - doorWidth/2;
            double rightDoorX = 520 - doorWidth/2;
            
            boolean isAtLeftDoor = playerX >= leftDoorX && playerX <= leftDoorX + doorWidth;
            boolean isAtRightDoor = playerX >= rightDoorX && playerX <= rightDoorX + doorWidth;
            
            // If player is at either door
            if (isAtLeftDoor || isAtRightDoor) {
                return "door";
            }
        }
        
        return null;
    }
    
    /**
     * Checks if the player is near an interactive object and can interact with it
     * 
     * @param playerX Player's X position
     * @param playerY Player's Y position
     * @param interacting Whether the player is attempting to interact
     * @return The type of object interaction or null if none
     */
    public String checkObjectInteractions(double playerX, double playerY, boolean interacting) {
        if (!interacting) return null;
        
        // Define interaction radius
        int interactionRadius = 50;
        
        // In the ExperimentRoom, check for specific objects
        if (currentRoom instanceof ExperimentRoom) {
            // Check for items like bookshelf, button, cabinet, etc.
            if (isNearObject(playerX, playerY, 30, 370, 70, 105, interactionRadius)) {
                return "bookshelf";
            }
            else if (isNearObject(playerX, playerY, 70, 115, 10, 5, interactionRadius)) {
                return "button";
            }
            else if (isNearObject(playerX, playerY, 100, 380, 35, 95, interactionRadius)) {
                return "cabinet";
            }
            else if (isNearObject(playerX, playerY, 150, 410, 45, 35, interactionRadius)) {
                return "laptop";
            }
        }
        // In the CloningRoom, check for specific objects
        else if (currentRoom instanceof CloningRoom) {
            // Check for items like screens, clones, etc.
            if (isNearObject(playerX, playerY, 160, 370, 100, 60, interactionRadius)) {
                return "wallscreen";
            }
            else if (isNearObject(playerX, playerY, 290, 25, 60, 100, interactionRadius)) {
                return "hologram";
            }
        }
        // In the TheresholdRoom, check for specific objects
        else if (currentRoom instanceof ThresholdRoom) {
            // Check for items, avoiding lasers
            if (isNearLaser(playerX, playerY, interactionRadius)) {
                return "laser";
            }
        }
        
        return null;
    }
    
    /**
     * Checks if player is near a specific object
     * 
     * @param playerX Player's X position
     * @param playerY Player's Y position
     * @param objX Object's X position
     * @param objY Object's Y position
     * @param objWidth Object's width
     * @param objHeight Object's height
     * @param radius Interaction radius
     * @return true if player is near the object
     */
    private boolean isNearObject(double playerX, double playerY,
                            int objX, int objY, int objWidth, int objHeight,
                            int radius) {
        // Calculate center of object
        int objCenterX = objX + (objWidth / 2);
        int objCenterY = objY + (objHeight / 2);
        
        // Calculate distance between player and object center
        double distance = Math.sqrt(
            Math.pow(playerX - objCenterX, 2) +
            Math.pow(playerY - objCenterY, 2)
        );
        
        return distance <= radius;
    }
    
    /**
     * Checks if player is near any laser in TheresholdRoom
     * 
     * @param playerX Player's X position
     * @param playerY Player's Y position
     * @param radius Interaction radius
     * @return true if player is near a laser
     */
    private boolean isNearLaser(double playerX, double playerY, int radius) {
        if (!(currentRoom instanceof ThresholdRoom)) return false;
        
        ArrayList<Item> items = currentRoom.getItems();
        
        for (Item item : items) {
            // Check if this item is a laser (based on image name or size)
            String imageName = item.getItem().toString();
            if (imageName.contains("Laser") || imageName.contains("LaserUp")) {
                if (isNearObject(playerX, playerY, item.getX(), item.getY(),
                                item.getWidth(), item.getHeight(), radius)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Checks if the player is colliding with any items in the room
     * 
     * @param newX The new X position to check
     * @param newY The new Y position to check
     * @param playerSize The size of the player
     * @return true if there's a collision, false otherwise
     */
    public boolean checkCollision(double newX, double newY, double playerSize) {
        if (currentRoom == null) return false;
        
        ArrayList<Item> items = currentRoom.getItems();
        int halfPlayerSize = (int) playerSize / 2;
        int COLLISION_BUFFER = 5;
        
        java.awt.Rectangle playerBounds = new java.awt.Rectangle(
            (int) newX - halfPlayerSize,
            (int) newY - halfPlayerSize,
            (int) playerSize,
            (int) playerSize
        );
        
        for (Item item : items) {
            // Skip background items and floor items that should be walkable
            if (item.getY() < 40 && item.getHeight() > 500) continue; // Skip background
            if (item.getY() == 40 && item.getHeight() == 540) continue; // Skip floor
            
            // Also skip special items that should be walkable
            if (item.getWidth() <= 30 && item.getHeight() <= 30) continue; // Small items
            
            java.awt.Rectangle itemBounds = new java.awt.Rectangle(
                item.getX() + COLLISION_BUFFER,
                item.getY() + COLLISION_BUFFER,
                item.getWidth() - (2 * COLLISION_BUFFER),
                item.getHeight() - (2 * COLLISION_BUFFER)
            );
            
            if (playerBounds.intersects(itemBounds)) {
                return true; // Collision detected
            }
        }
        
        // Check boundaries of the screen
        if (newX - halfPlayerSize < 10 || newX + halfPlayerSize > width - 10 ||
            newY - halfPlayerSize < 10 || newY + halfPlayerSize > height - 10) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Get the current room
     * 
     * @return The current room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    /**
     * Get the current room index
     * 
     * @return The current room index
     */
    public int getCurrentRoomIndex() {
        return currentRoomIndex;
    }
    
    /**
     * Set the current room index
     * 
     * @param index The new room index
     */
    public void setCurrentRoomIndex(int index) {
        if (index >= 0 && index < roomNames.length) {
            currentRoomIndex = index;
        }
    }
    
    /**
     * Check if the room is a transition scene
     * 
     * @return true if the room is a transition scene
     */
    public boolean isTransitionScene() {
        return currentRoom instanceof PhaseRoom || currentRoom instanceof EndingRoom;
    }
}