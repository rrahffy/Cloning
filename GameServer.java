/**
	This class contains the code that manages the game server's functionality.
    It handles connections from players and relays information between them.
	
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

import java.io.*;
import java.net.*;

public class GameServer {
     
    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;
    private int port;
    private boolean serverRunning;

    private Socket p1Socket;
    private Socket p2Socket;
    private ReadFromClient p1ReadRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private WriteToClient p2WriteRunnable;

    // Player positions
    private double p1x, p1y, p2x, p2y;
    private int p1Direction, p2Direction;
    
    // Current room tracking
    private int p1Room, p2Room;
    
    // Room password states
    private boolean[] roomUnlocked = new boolean[3]; // Track if room passwords are solved
    
    // Interactive states
    private boolean p1Interacting = false;
    private boolean p2Interacting = false;
    private String p1InteractionTarget = "";
    private String p2InteractionTarget = "";

    /**
     * Constructor for GameServer
     */
    public GameServer() {
        this(45371); // Use default port
    }
    
    /**
     * Constructor for GameServer with specified port
     * 
     * @param port The port number to use for the server
     */
    public GameServer(int port) {
        System.out.println("===== COZEN GAME SERVER =====");
        this.port = port;
        numPlayers = 0;
        maxPlayers = 2;
        serverRunning = false;

        // Initial player positions
        p1x = 100;
        p1y = 400;
        p2x = 490;
        p2y = 400;
        
        // Initial player directions (0 = UP, 1 = DOWN, 2 = LEFT, 3 = RIGHT)
        p1Direction = 1; // DOWN
        p2Direction = 1; // DOWN

        // Both players start in room 0 (ExperimentRoom)
        p1Room = 0;
        p2Room = 0;
        
        // Initialize all rooms as locked except first room
        roomUnlocked[0] = true; // First room is always accessible
        for (int i = 1; i < roomUnlocked.length; i++) {
            roomUnlocked[i] = false;
        }

        initializeServer();
    }
    
    /**
     * Initialize the server socket
     * 
     * @return true if server initialized successfully, false otherwise
     */
    private boolean initializeServer() {
        try {
            ss = new ServerSocket(port);
            serverRunning = true;
            System.out.println("Server started on port " + port);
            return true;
        } catch (BindException e) {
            System.out.println("Port " + port + " is already in use.");
            System.out.println("Try using a different port or stopping any other applications using port " + port);
            return false;
        } catch (IOException ex) {
            System.out.println("IOException from GameServer constructor");
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Accepts connections from players
     */
    public void acceptConnections() {
        if (!serverRunning) {
            System.out.println("Cannot accept connections - server not running.");
            return;
        }
        
        try {
            System.out.println("Waiting for connections...");

            while(numPlayers < maxPlayers) {
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected.");
            
                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if(numPlayers == 1) {
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;
                    
                    // Start the game once both players are connected
                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();
                    
                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();
                    
                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();
                }
            }

            System.out.println("Maximum players reached. No longer accepting connections.");

        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
            ex.printStackTrace();
        }
    }
    
    /**
     * Processes a room change request
     * 
     * @param playerID The ID of the player requesting the room change
     * @param roomIndex The index of the room to change to
     * @return true if the room change is allowed, false otherwise
     */
    private boolean processRoomChange(int playerID, int roomIndex) {
        // Check if room is valid
        if (roomIndex < 0 || roomIndex > 2) {
            return false;
        }
        
        // Check if the room is unlocked (except for first room)
        if (roomIndex > 0 && !roomUnlocked[roomIndex - 1]) {
            return false;
        }
        
        // Update player's room
        if (playerID == 1) {
            p1Room = roomIndex;
        } else {
            p2Room = roomIndex;
        }
        
        // Reset player position for the new room
        if (playerID == 1) {
            p1x = 100;
            p1y = 400;
        } else {
            p2x = 490;
            p2y = 400;
        }
        
        System.out.println("Player #" + playerID + " moved to room " + roomIndex);
        return true;
    }
    
    /**
     * Process interaction with an item in the room
     * 
     * @param playerID The ID of the player interacting
     * @param objectID The ID of the object being interacted with
     * @param roomID The ID of the room containing the object
     */
    private void processInteraction(int playerID, String objectID, int roomID) {
        System.out.println("Player #" + playerID + " interacted with " + objectID + " in room " + roomID);
        
        // Store interaction state
        if (playerID == 1) {
            p1Interacting = true;
            p1InteractionTarget = objectID;
        } else {
            p2Interacting = true;
            p2InteractionTarget = objectID;
        }
        
        // Process specific interactions based on game logic
        // These would be based on your game's requirements
        
        // Example: If player interacts with the door in room 0 and password is correct
        if (objectID.equals("door") && roomID == 0) {
            roomUnlocked[0] = true;
        }
    }
    
    /**
     * End an interaction
     * 
     * @param playerID The ID of the player ending the interaction
     */
    private void endInteraction(int playerID) {
        if (playerID == 1) {
            p1Interacting = false;
            p1InteractionTarget = "";
        } else {
            p2Interacting = false;
            p2InteractionTarget = "";
        }
    }

    /**
     * Inner class to read data from clients
     */
    private class ReadFromClient implements Runnable {
        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;
            System.out.println("RFC" + playerID + " Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    // Read message type
                    String messageType = dataIn.readUTF();
                    
                    if (messageType.equals("position")) {
                        // Handle position updates
                        if(playerID == 1) {
                            p1x = dataIn.readDouble();
                            p1y = dataIn.readDouble();
                            p1Room = dataIn.readInt();
                            p1Direction = dataIn.readInt();
                        } else {
                            p2x = dataIn.readDouble();
                            p2y = dataIn.readDouble();
                            p2Room = dataIn.readInt();
                            p2Direction = dataIn.readInt();
                        }
                    } 
                    else if (messageType.equals("room_change")) {
                        // Handle room change requests
                        int roomIndex = dataIn.readInt();
                        processRoomChange(playerID, roomIndex);
                    }
                    else if (messageType.equals("interaction")) {
                        // Handle interaction with objects in room
                        String objectID = dataIn.readUTF();
                        int roomID = dataIn.readInt();
                        processInteraction(playerID, objectID, roomID);
                    }
                    else if (messageType.equals("end_interaction")) {
                        // Handle end of interaction
                        endInteraction(playerID);
                    }
                }
            } catch (IOException ex) {
                System.out.println("Player #" + playerID + " disconnected.");
                if (playerID == 1) {
                    try {
                        p2WriteRunnable.sendGameOver("Player 1 disconnected. Game over.");
                    } catch (Exception e) {
                        System.out.println("Error notifying player 2 of disconnection.");
                    }
                } else {
                    try {
                        p1WriteRunnable.sendGameOver("Player 2 disconnected. Game over.");
                    } catch (Exception e) {
                        System.out.println("Error notifying player 1 of disconnection.");
                    }
                }
            }
        }
    }

    /**
     * Inner class to write data to clients
     */
    private class WriteToClient implements Runnable {
        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;
            System.out.println("WTC" + playerID + " Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    if(playerID == 1) {
                        // Send player 2's data to player 1
                        dataOut.writeUTF("position");
                        dataOut.writeDouble(p2x);
                        dataOut.writeDouble(p2y);
                        dataOut.writeInt(p2Room);
                        dataOut.writeInt(p2Direction);
                        
                        // Send interaction data if player 2 is interacting
                        if (p2Interacting) {
                            dataOut.writeUTF("interaction");
                            dataOut.writeUTF(p2InteractionTarget);
                        } else {
                            dataOut.writeUTF("no_interaction");
                        }
                        
                        dataOut.flush();
                    } else {
                        // Send player 1's data to player 2
                        dataOut.writeUTF("position");
                        dataOut.writeDouble(p1x);
                        dataOut.writeDouble(p1y);
                        dataOut.writeInt(p1Room);
                        dataOut.writeInt(p1Direction);
                        
                        // Send interaction data if player 1 is interacting
                        if (p1Interacting) {
                            dataOut.writeUTF("interaction");
                            dataOut.writeUTF(p1InteractionTarget);
                        } else {
                            dataOut.writeUTF("no_interaction");
                        }
                        
                        dataOut.flush();
                    }
                    
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTC run() - Player #" + playerID);
            }
        }

        /**
         * Sends the start message to the client
         */
        public void sendStartMsg() {
            try {
                dataOut.writeUTF("start");
                dataOut.writeUTF("We now have 2 players. Let the game begin!");
            } catch (IOException ex) {
                System.out.println("IOException from sendStartMsg");
            }
        }
        
        /**
         * Sends game over message to the client
         * 
         * @param message The message to send
         */
        public void sendGameOver(String message) {
            try {
                dataOut.writeUTF("game_over");
                dataOut.writeUTF(message);
            } catch (IOException ex) {
                System.out.println("IOException from sendGameOver");
            }
        }
    }
    
    /**
     * Shuts down the server and releases resources
     */
    public void shutdownServer() {
        try {
            if (ss != null && !ss.isClosed()) {
                ss.close();
                System.out.println("Server shutdown complete.");
            }
        } catch (IOException ex) {
            System.out.println("Error shutting down server: " + ex.getMessage());
        }
    }

    /**
     * Main method to start the server
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Try default port first
        GameServer gs = new GameServer();
        
        // If server didn't start, try alternative ports
        if (!gs.serverRunning) {
            int[] alternativePorts = {45372, 45373, 45374, 45375};
            
            for (int altPort : alternativePorts) {
                System.out.println("Trying alternative port: " + altPort);
                gs = new GameServer(altPort);
                if (gs.serverRunning) {
                    break;  // Found a working port
                }
            }
        }
        
        // Only accept connections if server is running
        if (gs.serverRunning) {
            gs.acceptConnections();
        } else {
            System.out.println("Failed to start server on any port. Please check network configuration.");
        }
    }
}
