/**
	This contains the first room in the game. It has all the hints about the experiments happening in this escape room and is made up of buffered images.
	
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
import javax.swing.Timer;


public class ExperimentRoom extends Room{

    /** 
     * This calls the loadRoom() method
     **/ 
    public ExperimentRoom(){
        super(loadRoom());
    }

    /**
     * This creates the ArrayList containing buffered images that makes up the room.
     * @return items The elements in the ArrayList
     */
    private static ArrayList<Item> loadRoom(){
        ArrayList<Item> items = new ArrayList<>();
        try{
            //both has it 
            BufferedImage floor = ImageIO.read(ExperimentRoom.class.getResource("Assets/Floor.png"));
            BufferedImage sideWall = ImageIO.read(ExperimentRoom.class.getResource("Assets/SideWall.png"));
            BufferedImage bg = ImageIO.read(ExperimentRoom.class.getResource("Assets/Background.png"));
            BufferedImage wall = ImageIO.read(ExperimentRoom.class.getResource("Assets/Wall.png"));
            BufferedImage leftDoor = ImageIO.read(ExperimentRoom.class.getResource("Assets/A1Door.png"));
            BufferedImage rightDoor = ImageIO.read(ExperimentRoom.class.getResource("Assets/A2Door.png"));
            BufferedImage comms = ImageIO.read(ExperimentRoom.class.getResource("Assets/Communicator.png"));
            BufferedImage laptop = ImageIO.read(ExperimentRoom.class.getResource("Assets/Laptop.png"));
            BufferedImage screen = ImageIO.read(ExperimentRoom.class.getResource("Assets/Screen.png"));
            BufferedImage metalTable = ImageIO.read(ExperimentRoom.class.getResource("Assets/MetalTableFront.png"));
            BufferedImage tableFront = ImageIO.read(ExperimentRoom.class.getResource("Assets/TableFront.png"));

            //a1 side
            BufferedImage bed = ImageIO.read(ExperimentRoom.class.getResource("Assets/Bed.png"));
            BufferedImage candle = ImageIO.read(ExperimentRoom.class.getResource("Assets/Candle.png"));
            BufferedImage drip = ImageIO.read(ExperimentRoom.class.getResource("Assets/IVDrip.png"));
            BufferedImage button = ImageIO.read(ExperimentRoom.class.getResource("Assets/Button.png"));
            BufferedImage bookshelfBefore = ImageIO.read(ExperimentRoom.class.getResource("Assets/Bookshelf.png"));
            BufferedImage bookshelfAfter = ImageIO.read(ExperimentRoom.class.getResource("Assets/BookshelfSafe.png"));
            BufferedImage blankPaper = ImageIO.read(ExperimentRoom.class.getResource("Assets/BlankPaper.png"));
            BufferedImage newspaper = ImageIO.read(ExperimentRoom.class.getResource("Assets/News.png"));
            BufferedImage chair = ImageIO.read(ExperimentRoom.class.getResource("Assets/ChairRight.png"));
            BufferedImage cabinet = ImageIO.read(ExperimentRoom.class.getResource("Assets/Cabinet.png"));
            BufferedImage lockedDoor = ImageIO.read(ExperimentRoom.class.getResource("Assets/FirstDoor.png"));
            BufferedImage stackPaper = ImageIO.read(ExperimentRoom.class.getResource("Assets/PaperStackWritten.png"));

            //a2 side 
            BufferedImage syringe = ImageIO.read(ExperimentRoom.class.getResource("Assets/Syringe.png"));
            BufferedImage safe = ImageIO.read(ExperimentRoom.class.getResource("Assets/Safe.png"));
            BufferedImage phone = ImageIO.read(ExperimentRoom.class.getResource("Assets/OldPhone.png"));
            BufferedImage fridge = ImageIO.read(ExperimentRoom.class.getResource("Assets/Fridge.png"));
            BufferedImage randomPaper = ImageIO.read(ExperimentRoom.class.getResource("Assets/WrittenPaper.png"));
            BufferedImage clock = ImageIO.read(ExperimentRoom.class.getResource("Assets/Clock.png"));
            BufferedImage box = ImageIO.read(ExperimentRoom.class.getResource("Assets/Box.png"));
            BufferedImage electricChair = ImageIO.read(ExperimentRoom.class.getResource("Assets/ElectricChair.png"));
            BufferedImage meds = ImageIO.read(ExperimentRoom.class.getResource("Assets/Medicine.png"));
            BufferedImage icePick = ImageIO.read(ExperimentRoom.class.getResource("Assets/IcePick.png"));
            BufferedImage standScreen = ImageIO.read(ExperimentRoom.class.getResource("Assets/StandingScreen.png"));
            BufferedImage testTube = ImageIO.read(ExperimentRoom.class.getResource("Assets/TestTube.png"));
            BufferedImage pressurePlate = ImageIO.read(ExperimentRoom.class.getResource("Assets/PressurePlate.png"));
            BufferedImage metalTableSide = ImageIO.read(ExperimentRoom.class.getResource("Assets/MetalTableSide.png"));

            //both has it
            items.add(new Item(0, 0, 1024, 768, bg));
            items.add(new Item(10, 40, 990, 540, floor));
            items.add(new Item(10, 10, 990, 100, wall));
            items.add(new Item(10, 10, 10, 650, sideWall));
            items.add(new Item(990, 10, 10, 650, sideWall));
            items.add(new Item(500, 10, 10, 650, sideWall));
            items.add(new Item(455, 580, 50, 100, leftDoor)); 
            items.add(new Item(505, 580, 50, 100, rightDoor));
            items.add(new Item(10, 580, 450, 100, wall));
            items.add(new Item(555, 580, 445, 100, wall));

            //a1 side only 
            items.add(new Item(200, 10, 10, 280, sideWall));
            items.add(new Item(20, 190, 30, 100, wall));
            items.add(new Item(100, 190, 100, 100, wall));
            items.add(new Item(50, 45, 50, 40, screen));
            items.add(new Item(30, 90, 90, 60, tableFront));
            items.add(new Item(70, 115, 10, 5, button));
            items.add(new Item(160, 65, 20, 20, comms));
            items.add(new Item(50, 190, 50, 100, lockedDoor));
            items.add(new Item(310, 80, 70, 120, bed));
            items.add(new Item(420, 80, 70, 120, bed));
            items.add(new Item(390, 60, 25, 90, drip));
            items.add(new Item(210, 100, 40, 40, metalTable));
            items.add(new Item(220, 80, 20, 40, candle));
            items.add(new Item(20, 350, 350, 100, wall));
            items.add(new Item(30, 370, 70, 105, bookshelfAfter));
            items.add(new Item(30, 370, 70, 105, bookshelfBefore));  
            items.add(new Item(100, 380, 35, 95, cabinet));
            items.add(new Item(140, 415, 140, 60, metalTable));
            items.add(new Item(150, 410, 45, 35, laptop));
            items.add(new Item(210, 410, 40, 35, stackPaper));
            items.add(new Item(230, 415, 30, 30, stackPaper));
            items.add(new Item(180, 440, 40, 70, chair));
            items.add(new Item(25, 510, 30, 20, newspaper));
            items.add(new Item(250, 150, 30, 20, blankPaper));

            //a2 side only
            items.add(new Item(520, 30, 30, 30, clock));
            items.add(new Item(560, 90, 205, 65, tableFront));
            items.add(new Item(610, 40, 100, 80, screen));
            items.add(new Item(565, 70, 60, 50, screen));
            items.add(new Item(700, 70, 60, 50, screen));
            items.add(new Item(825, 90, 70, 65, safe));
            items.add(new Item(905, 125, 10, 20, phone));
            items.add(new Item(895, 50, 60, 100, fridge));
            items.add(new Item(790, 65, 20, 20, comms));
            items.add(new Item(510, 230, 150, 100, wall));
            items.add(new Item(550, 260, 70, 110, electricChair));
            items.add(new Item(760, 260, 120, 60, metalTable));
            items.add(new Item(760, 260, 60, 200, metalTableSide));
            items.add(new Item(760, 415, 160, 60, metalTable));
            items.add(new Item(825, 245, 50, 45, laptop));
            items.add(new Item(550, 390, 15, 20, syringe));
            items.add(new Item(530, 380, 15, 20, icePick));
            items.add(new Item(880, 420, 30, 25, meds));
            items.add(new Item(770, 300, 20, 30, randomPaper));
            items.add(new Item(780, 320, 20, 30, randomPaper));
            items.add(new Item(770, 335, 20, 30, randomPaper));
            items.add(new Item(770, 420, 40, 30, testTube));
            items.add(new Item(815, 420, 30, 30, stackPaper));
            items.add(new Item(630, 270, 20, 70, standScreen));
            items.add(new Item(625, 350, 30, 30, pressurePlate));
            items.add(new Item(700, 500, 100, 40, box));
            items.add(new Item(760, 520, 100, 40, box));
            
        } catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }
    
    /**
     * Process interactions with objects in the ExperimentRoom
     * @param objectType The type of object being interacted with
     * @return true if the interaction was processed, false otherwise
     */
    @Override
    public boolean processInteraction(String objectType) {
        System.out.println("ExperimentRoom: Processing interaction with: " + objectType);
        
        switch(objectType) {
            case "bookshelf":
                // Custom logic for ExperimentRoom bookshelf
                replaceItem("Assets/Bookshelf.png", "Assets/BookshelfSafe.png");
                return true;
                
            case "button":
                // Custom logic for button - maybe it opens a secret panel
                replaceItem("Assets/Button.png", "Assets/ButtonPressed.png");
                
                // Add timed logic to show something happening
                new Timer(500, e -> {
                    replaceItem("Assets/ButtonPressed.png", "Assets/Button.png");
                    // Maybe reveal a hidden item or door
                    ((Timer)e.getSource()).stop();
                }).start();
                return true;
                
            case "cabinet":
                // Change cabinet appearance to show it's been opened
                replaceItem("Assets/Cabinet.png", "Assets/CabinetOpen.png");
                return true;
                
            case "laptop":
                // Change laptop appearance to show it's been used
                replaceItem("Assets/Laptop.png", "Assets/LaptopOn.png");
                return true;
                
            default:
                // Fall back to default implementation
                return super.processInteraction(objectType);
        }
    }
}