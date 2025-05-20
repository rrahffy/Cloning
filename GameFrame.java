/**
	This class contains the code that sets up the main JFrame for the player.
    It manages the main game window and handles connections to the server.
	
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
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class GameFrame extends JFrame {
  
   private int width, height;
   private Container contentPane;
   private Player player;
   private Player enemyPlayer;
   private GameCanvas gameCanvas;
   private Timer animationTimer;
   private boolean up, down, left, right, interacting;
   private Socket socket;
   private int playerID;
   private ReadFromServer rfsRunnable;
   private WriteToServer wtsRunnable;
   private Room currentRoom;
   private int currentRoomIndex = 0;
   private boolean isInTransition = false;
  
   // List of rooms in order of progression
   private String[] roomNames = {
       "ExperimentRoom", "CloningRoom", "PhaseRoom", "TheresholdRoom", "EndingRoom"
   };
  
   // Game state flags
   private boolean gameStarted = false;
   private boolean gameEnded = false;
   private String gameMessage = "";
  
   // Constants for collision detection
   private final int COLLISION_BUFFER = 5;


   /**
    * Constructor for GameFrame
    *
    * @param width The width of the game window
    * @param height The height of the game window
    */
   public GameFrame(int width, int height) {
       this.width = width;
       this.height = height;
       up = false;
       down = false;
       left = false;
       right = false;
       interacting = false;
   }


   /**
    * Sets up the GUI components
    */
   public void setUpGUI() {
       contentPane = this.getContentPane();
       this.setTitle("Cozen - Player #" + playerID);
       contentPane.setPreferredSize(new Dimension(width, height));
      
       createPlayers();
       loadCurrentRoom();
      
       gameCanvas = new GameCanvas(player, enemyPlayer, currentRoom);
       gameCanvas.setGameStarted(gameStarted);
       contentPane.add(gameCanvas);
      
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.pack();
       this.setVisible(true);
      
       setUpAnimationTimer();
       setUpKeyListener();
   }
  
   /**
    * Creates the player objects
    */
  private void createPlayers() {
      // Player 1 is always blue and named A1
      // Player 2 is always red and named A2
      if (playerID == 1) {
          player = new Player(400, 500, 50, Color.BLUE, 1);
          player.setName("A1");
          enemyPlayer = new Player(600, 500, 50, Color.RED, 2);
          enemyPlayer.setName("A2");
      } else {
          player = new Player(600, 500, 50, Color.RED, 2);
          player.setName("A2");
          enemyPlayer = new Player(400, 500, 50, Color.BLUE, 1);
          enemyPlayer.setName("A1");
      }
     
      // Debug output to confirm player creation
      System.out.println("Created player with ID: " + playerID);
      System.out.println("Player position: " + player.getX() + ", " + player.getY());
      System.out.println("Enemy position: " + enemyPlayer.getX() + ", " + enemyPlayer.getY());
  }
  
   /**
    * Loads the current room based on currentRoomIndex
    */
   private void loadCurrentRoom() {
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
               currentRoom = new TheresholdRoom();
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
      
       // Update the game canvas with the new room
       if (gameCanvas != null) {
           gameCanvas.setCurrentRoom(currentRoom);
       }
   }
  
   /**
    * Moves to the next room
    */
   public void goToNextRoom() {
       currentRoomIndex++;
       if (currentRoomIndex >= roomNames.length) {
           currentRoomIndex = roomNames.length - 1;
       }
      
       isInTransition = true;
       loadCurrentRoom();
      
       // Notify the server about room change
       if (wtsRunnable != null) {
           wtsRunnable.sendRoomChange(currentRoomIndex);
       }
   }
  
   /**
    * Change to a specific room
    * @param roomIndex The index of the room to change to
    */
   public void changeRoom(int roomIndex) {
       if (roomIndex >= 0 && roomIndex < roomNames.length) {
           currentRoomIndex = roomIndex;
           isInTransition = true;
           loadCurrentRoom();
          
           // Notify the server about room change
           if (wtsRunnable != null) {
               wtsRunnable.sendRoomChange(currentRoomIndex);
           }
       }
   }


   /**
    * Sets up the animation timer for smooth movement
    */
   private void setUpAnimationTimer() {
       int interval = 10;
       ActionListener al = new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
              if (!gameStarted || gameEnded) {
                  return;
              }
             
              // Don't process movement for transition scenes
              if (currentRoom instanceof PhaseRoom || currentRoom instanceof EndingRoom) {
                  gameCanvas.repaint();
                  return;
              }
             
              double originalX = player.getX();
              double originalY = player.getY();
              double speed = player.getSpeed();
              boolean moved = false;
             
              // Handle diagonal movement by processing all active directions
              if (up) {
                  player.setDirection(Player.Direction.UP);
                  double newY = player.getY() - speed;
                  if (!checkCollision(player.getX(), newY)) {
                      player.moveV(-speed);
                      moved = true;
                  }
              }
              if (down) {
                  player.setDirection(Player.Direction.DOWN);
                  double newY = player.getY() + speed;
                  if (!checkCollision(player.getX(), newY)) {
                      player.moveV(speed);
                      moved = true;
                  }
              }
              if (left) {
                  player.setDirection(Player.Direction.LEFT);
                  double newX = player.getX() - speed;
                  if (!checkCollision(newX, player.getY())) {
                      player.moveH(-speed);
                      moved = true;
                  }
              }
              if (right) {
                  player.setDirection(Player.Direction.RIGHT);
                  double newX = player.getX() + speed;
                  if (!checkCollision(newX, player.getY())) {
                      player.moveH(speed);
                      moved = true;
                  }
              }
             
              // Check for door interactions
              checkDoorInteractions();
             
              // Check for object interactions
              if (interacting) {
                  checkObjectInteractions();
                  // Don't reset interacting here - it's reset on key release
              }
             
              gameCanvas.repaint();
          }
       };
       animationTimer = new Timer(interval, al);
       animationTimer.start();
   }
  
   /**
    * Checks if the player is colliding with any items in the room
    * @param newX The new X position to check
    * @param newY The new Y position to check
    * @return true if there's a collision, false otherwise
    */
   private boolean checkCollision(double newX, double newY) {
       if (currentRoom == null) return false;
      
       ArrayList<Item> items = currentRoom.getItems();
       int playerSize = (int) player.getSize();
       int halfPlayerSize = playerSize / 2;
      
       Rectangle playerBounds = new Rectangle(
           (int) newX - halfPlayerSize,
           (int) newY - halfPlayerSize,
           playerSize,
           playerSize
       );
      
       for (Item item : items) {
           // Skip background items and floor items that should be walkable
           if (item.getY() < 40 && item.getHeight() > 500) continue; // Skip background
           if (item.getY() == 40 && item.getHeight() == 540) continue; // Skip floor
          
           // Also skip special items that should be walkable
           // You might need to adjust this based on your game's assets
           if (item.getWidth() <= 30 && item.getHeight() <= 30) continue; // Small items
          
           Rectangle itemBounds = new Rectangle(
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
    * Checks if the player is near a door and can interact with it
    */
  private void checkDoorInteractions() {
      // Only check door interactions in ExperimentRoom and CloningRoom
      // For the TheresholdRoom, the exit door is special and handled differently
      if (currentRoomIndex > 1) return;
     
      // Check if player is in door area (common for both rooms)
      if (player.getY() >= 480 && player.getY() <= 580) {  // Adjusted Y coordinates to be higher up
          double doorWidth = 50;
          double leftDoorX = 480 - doorWidth/2;
          double rightDoorX = 520 - doorWidth/2;
         
          boolean isAtLeftDoor = player.getX() >= leftDoorX && player.getX() <= leftDoorX + doorWidth;
          boolean isAtRightDoor = player.getX() >= rightDoorX && player.getX() <= rightDoorX + doorWidth;
         
          // If player is at either door
          if (isAtLeftDoor || isAtRightDoor) {
              if (interacting) {
                  // This sends the door interaction to the server
                  wtsRunnable.sendInteraction("door", currentRoomIndex);
              }
          }
      }
     
      // Check for exit door in TheresholdRoom
      if (currentRoom instanceof TheresholdRoom) {
          // Check if player is near the exit door
          if (player.getY() >= 480 && player.getY() <= 580 &&
              player.getX() >= 810 && player.getX() <= 900) {
              if (interacting) {
                  wtsRunnable.sendInteraction("exit", currentRoomIndex);
              }
          }
      }
  }
  
   /**
    * Checks if the player is near an interactive object and can interact with it
    */
  private void checkObjectInteractions() {
       // Get objects that are nearby to the player for interaction
       // We'll define "nearby" as within a certain radius
       double playerX = player.getX();
       double playerY = player.getY();
       int interactionRadius = 50; // Adjust this value based on your game design
      
       // In the ExperimentRoom, check for specific objects
       if (currentRoom instanceof ExperimentRoom) {
           // Check for items like bookshelf, button, cabinet, etc.
           if (isNearObject(playerX, playerY, 30, 370, 70, 105, interactionRadius)) {
               // Bookshelf interaction
               wtsRunnable.sendInteraction("bookshelf", currentRoomIndex);
               gameCanvas.showMessage("Examining bookshelf...", 1000);
           }
           else if (isNearObject(playerX, playerY, 70, 115, 10, 5, interactionRadius)) {
               // Button interaction
               wtsRunnable.sendInteraction("button", currentRoomIndex);
               gameCanvas.showMessage("Button pressed!", 1000);
           }
           else if (isNearObject(playerX, playerY, 100, 380, 35, 95, interactionRadius)) {
               // Cabinet interaction
               wtsRunnable.sendInteraction("cabinet", currentRoomIndex);
               gameCanvas.showMessage("Looking in cabinet...", 1000);
           }
           else if (isNearObject(playerX, playerY, 150, 410, 45, 35, interactionRadius)) {
               // Laptop interaction
               wtsRunnable.sendInteraction("laptop", currentRoomIndex);
               gameCanvas.showMessage("Using laptop...", 1000);
           }
       }
       // In the CloningRoom, check for specific objects
       else if (currentRoom instanceof CloningRoom) {
           // Check for items like screens, clones, etc.
           if (isNearObject(playerX, playerY, 160, 370, 100, 60, interactionRadius)) {
               // Wall screen interaction
               wtsRunnable.sendInteraction("wallscreen", currentRoomIndex);
               gameCanvas.showMessage("Examining wall screen...", 1000);
           }
           else if (isNearObject(playerX, playerY, 290, 25, 60, 100, interactionRadius)) {
               // Hologram screen interaction
               wtsRunnable.sendInteraction("hologram", currentRoomIndex);
               gameCanvas.showMessage("Interacting with hologram...", 1000);
           }
       }
       // In the TheresholdRoom, check for specific objects
       else if (currentRoom instanceof TheresholdRoom) {
           // Check for items, avoiding lasers
           if (isNearLaser(playerX, playerY, interactionRadius)) {
               // Player touched a laser
               wtsRunnable.sendInteraction("laser", currentRoomIndex);
               gameCanvas.showMessage("Warning! Laser detected!", 1000);
           }
       }
   }
  
   /**
    * Checks if player is near a specific object
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
    * @param playerX Player's X position
    * @param playerY Player's Y position
    * @param radius Interaction radius
    * @return true if player is near a laser
    */
   private boolean isNearLaser(double playerX, double playerY, int radius) {
       if (!(currentRoom instanceof TheresholdRoom)) return false;
      
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
    * Sets up the key listener for player movement
    */
   private void setUpKeyListener() {
       KeyListener kl = new KeyListener() {
           public void keyTyped(KeyEvent ke) {
               // Not used
           }


          public void keyPressed(KeyEvent ke) {
              if (!gameStarted || gameEnded) {
                  return;
              }
             
              // Skip movement input for transition scenes
              if (currentRoom instanceof PhaseRoom || currentRoom instanceof EndingRoom) {
                  if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                      // Allow space key to advance transition scenes
                      interacting = true;
                      wtsRunnable.sendInteraction("advance", currentRoomIndex);
                  }
                  return;
              }
             
              int keyCode = ke.getKeyCode();
             
              switch (keyCode) {
                  case KeyEvent.VK_UP:
                      up = true;
                      // Don't disable other directions - this allows diagonal movement
                      break;
                  case KeyEvent.VK_DOWN:
                      down = true;
                      break;
                  case KeyEvent.VK_LEFT:
                      left = true;
                      break;
                  case KeyEvent.VK_RIGHT:
                      right = true;
                      break;
                  case KeyEvent.VK_SPACE:
                      // Used for interaction with objects
                      interacting = true;
                      break;
              }
          }


           public void keyReleased(KeyEvent ke) {
               int keyCode = ke.getKeyCode();
              
               switch (keyCode) {
                   case KeyEvent.VK_UP:
                       up = false;
                       break;
                   case KeyEvent.VK_DOWN:
                       down = false;
                       break;
                   case KeyEvent.VK_LEFT:
                       left = false;
                       break;
                   case KeyEvent.VK_RIGHT:
                       right = false;
                       break;
                   case KeyEvent.VK_SPACE:
                       interacting = false;
                       // Make sure we send end interaction message to server
                       if (wtsRunnable != null) {
                           wtsRunnable.sendEndInteraction();
                       }
                       break;
               }
           }
       };
       contentPane.setFocusable(true);
       contentPane.requestFocusInWindow();
       contentPane.addKeyListener(kl);
   }


   /**
    * Connects to the game server
    */
    public void connectToServer() {
        try {
            socket = new Socket("localhost", 45371);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
           
            playerID = in.readInt();
            System.out.println("You are player #" + playerID);
           
            // Set the title after we know the playerID
            this.setTitle("Cozen - Player #" + playerID);
           
            if (playerID == 1) {
                System.out.println("Waiting for Player #2 to connect...");
            }
           
            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            
            // Set up GUI first, then wait for start message
            setUpGUI();
            
            rfsRunnable.waitForStartingMsg();
        } catch (IOException ex) {
            System.out.println("IOException from connectToServer");
            ex.printStackTrace();
        }
    }


   /**
    * Thread to read data from the server
    */
   private class ReadFromServer implements Runnable {
       private DataInputStream dataIn;


       public ReadFromServer(DataInputStream in) {
           dataIn = in;
           System.out.println("RFS Runnable created");
       }


       public void run() {
           try {
               while (true) {
                   String messageType = dataIn.readUTF();
                   System.out.println("Received message type: " + messageType);
                  
                   if (messageType.equals("position")) {
                       // Read enemy position
                       double enemyX = dataIn.readDouble();
                       double enemyY = dataIn.readDouble();
                       int enemyRoom = dataIn.readInt();
                       int enemyDirection = dataIn.readInt();
                      
                       // Update enemy player information
                       if (enemyPlayer != null) {
                           enemyPlayer.setX(enemyX);
                           enemyPlayer.setY(enemyY);


                           // Set the enemy's direction
                           Player.Direction dir;
                           switch (enemyDirection) {
                               case 0:
                                   dir = Player.Direction.UP;
                                   break;
                               case 1:
                                   dir = Player.Direction.DOWN;
                                   break;
                               case 2:
                                   dir = Player.Direction.LEFT;
                                   break;
                               case 3:
                                   dir = Player.Direction.RIGHT;
                                   break;
                               default:
                                   dir = Player.Direction.DOWN;
                                   break;
                           }
                           enemyPlayer.setDirection(dir);
                       }
                      
                       // Check if enemy is in a different room
                       if (enemyRoom != currentRoomIndex) {
                           // Enemy is in a different room, don't render them
                           gameCanvas.setEnemyVisible(false);
                       } else {
                           gameCanvas.setEnemyVisible(true);
                       }
                      
                       // Check interaction status
                       String interactionStatus = dataIn.readUTF();
                       if (interactionStatus.equals("interaction")) {
                           String targetObject = dataIn.readUTF();
                           // Handle enemy interaction visually if needed
                           gameCanvas.setEnemyInteracting(true, targetObject);
                       } else {
                           gameCanvas.setEnemyInteracting(false, "");
                       }
                   }
                   else if (messageType.equals("room_change_result")) {
                       boolean success = dataIn.readBoolean();
                       int roomIndex = dataIn.readInt();
                      
                       if (success) {
                           // Room change was successful
                           currentRoomIndex = roomIndex;
                           isInTransition = true;
                           loadCurrentRoom();
                           System.out.println("Room changed to " + roomNames[currentRoomIndex]);
                       } else {
                           // Room change failed - maybe room is locked?
                           System.out.println("Room change failed. Room may be locked.");
                           gameCanvas.showMessage("This door is locked!", 2000);
                       }
                   }
                   else if (messageType.equals("room_unlock")) {
                       int roomIndex = dataIn.readInt();
                       System.out.println("Room " + roomNames[roomIndex] + " has been unlocked!");
                       gameCanvas.showMessage(roomNames[roomIndex] + " has been unlocked!", 2000);
                   }
                   else if (messageType.equals("phase_transition")) {
                       // Time to show the phase room revealing player identities
                       changeRoom(2); // PhaseRoom
                   }
                   else if (messageType.equals("chase_start")) {
                       // Start the chase sequence in TheresholdRoom
                       changeRoom(3); // TheresholdRoom
                   }
                   else if (messageType.equals("game_over")) {
                       String message = dataIn.readUTF();
                       int winnerID = dataIn.readInt();
                      
                       gameEnded = true;
                       gameMessage = message;
                      
                       // Show appropriate ending based on winner
                       EndingRoom endingRoom = (EndingRoom) currentRoom;
                       endingRoom.changePlayerID(winnerID);
                       gameCanvas.setCurrentRoom(endingRoom);
                      
                       gameCanvas.setGameEnded(true);
                       gameCanvas.setGameMessage(message);
                       System.out.println("Game over: " + message);
                   }
                   else if (messageType.equals("puzzle_solved")) {
                       String puzzleName = dataIn.readUTF();
                       gameCanvas.showMessage(puzzleName + " solved!", 2000);
                       System.out.println("Puzzle solved: " + puzzleName);
                   }
                   else if (messageType.equals("player_hit")) {
                       // Player has hit a hazard or been caught
                       boolean isDead = dataIn.readBoolean();
                       if (isDead) {
                           // Player is "dead" - handle accordingly
                           gameCanvas.showMessage("You were caught!", 2000);
                       } else {
                           // Player was hit but still alive
                           gameCanvas.showMessage("You were hit! Be careful!", 2000);
                       }
                   }
               }
           } catch (IOException ex) {
               System.out.println("IOException from RFS run()");
               ex.printStackTrace();
           }
       }


        public void waitForStartingMsg() {
            try {
                String msgType = dataIn.readUTF();
           
                if (msgType.equals("start")) {
                    String startMsg = dataIn.readUTF();
                    System.out.println("Message from server: " + startMsg);
               
                    gameStarted = true;
                    gameCanvas.setGameStarted(true);

                    Thread readThread = new Thread(rfsRunnable);
                    Thread writeThread = new Thread(wtsRunnable);
                    readThread.start();
                    writeThread.start();
                }
            } catch (IOException ex) {
                System.out.println("IOException from waitForStartingMsg()");
                ex.printStackTrace();
            }
        }
    }


   /**
    * Thread to write data to the server
    */
   private class WriteToServer implements Runnable {
       private DataOutputStream dataOut;
       private boolean currentlyInteracting = false;


       public WriteToServer(DataOutputStream out) {
           dataOut = out;
           System.out.println("WTS Runnable created");
       }


      public void run() {
          try {
              while (true) {
                  if (player != null) {
                      dataOut.writeUTF("position");
                      dataOut.writeDouble(player.getX());
                      dataOut.writeDouble(player.getY());
                      dataOut.writeInt(currentRoomIndex);
                     
                      // Send the player's current direction
                      dataOut.writeInt(player.getDirection().ordinal());
                     
                      // Send interaction status - use the local tracking variable
                      if (currentlyInteracting) {
                          dataOut.writeUTF("interaction");
                          dataOut.writeUTF(""); // No specific object yet
                      } else {
                          dataOut.writeUTF("no_interaction");
                      }
                     
                      dataOut.flush();
                  }
                 
                  try {
                      Thread.sleep(25);
                  } catch (InterruptedException ex) {
                      System.out.println("InterruptedException from WTS run()");
                  }
              }
          } catch (IOException ex) {
              System.out.println("IOException from WTS run()");
              ex.printStackTrace();
          }
      }
      
       /**
        * Sends room change request to the server
        *
        * @param roomIndex The index of the room to change to
        */
       public void sendRoomChange(int roomIndex) {
           try {
               dataOut.writeUTF("room_change");
               dataOut.writeInt(roomIndex);
               dataOut.flush();
           } catch (IOException ex) {
               System.out.println("IOException from sendRoomChange");
               ex.printStackTrace();
           }
       }
      
       /**
        * Sends interaction request to the server
        *
        * @param objectID The ID of the object to interact with
        * @param roomID The ID of the room containing the object
        */
       public void sendInteraction(String objectID, int roomID) {
           try {
               System.out.println("Sending interaction: " + objectID + " in room " + roomID);
               dataOut.writeUTF("interaction");
               dataOut.writeUTF(objectID);
               dataOut.writeInt(roomID);
               dataOut.flush();
              
               // Set the local tracking variable
               currentlyInteracting = true;
           } catch (IOException ex) {
               System.out.println("IOException from sendInteraction");
               ex.printStackTrace();
           }
       }
       
       /**
        * Sends end interaction message to the server
        */
       public void sendEndInteraction() {
           try {
               System.out.println("Sending end interaction");
               dataOut.writeUTF("end_interaction");
               dataOut.flush();
              
               // Reset the local tracking variable
               currentlyInteracting = false;
           } catch (IOException ex) {
               System.out.println("IOException from sendEndInteraction");
               ex.printStackTrace();
           }
       }
    }
}