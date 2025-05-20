/**
	This is the second room in the game. It oontains hints about the cloning experiments and is made up of buffered images.
	
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

public class CloningRoom extends Room{

    /**
     * This loads the cloning room's items
     */
    public CloningRoom(){
        super(loadRoom());
    }
    
    /**
     * This loads the room based on the items added to the ArrayList that makes up the room
     * @return items The items in the ArrayList
     */
    private static ArrayList<Item> loadRoom(){
        ArrayList<Item> items = new ArrayList<>();
        try{
            //both has it 
            BufferedImage floor = ImageIO.read(CloningRoom.class.getResource("Assets/Floor.png"));
            BufferedImage sideWall = ImageIO.read(CloningRoom.class.getResource("Assets/SideWall.png"));
            BufferedImage bg = ImageIO.read(CloningRoom.class.getResource("Assets/Background.png"));
            BufferedImage wall = ImageIO.read(CloningRoom.class.getResource("Assets/CloningRoomWall.png"));
            BufferedImage leftDoor = ImageIO.read(CloningRoom.class.getResource("Assets/A1Door.png"));
            BufferedImage rightDoor = ImageIO.read(CloningRoom.class.getResource("Assets/A2Door.png"));
            BufferedImage wallScreen = ImageIO.read(CloningRoom.class.getResource("Assets/WallScreen.png"));
            BufferedImage metalTable = ImageIO.read(CloningRoom.class.getResource("Assets/MetalTableFront.png"));
            BufferedImage hologramScreen = ImageIO.read(CloningRoom.class.getResource("Assets/HologramScreen.png"));

            //a1 side
            BufferedImage tank = ImageIO.read(CloningRoom.class.getResource("Assets/Tank.png"));
            BufferedImage binary = ImageIO.read(CloningRoom.class.getResource("Assets/BinaryBook.png"));
            BufferedImage standScreen = ImageIO.read(CloningRoom.class.getResource("Assets/StandingScreen.png"));
            BufferedImage paper = ImageIO.read(CloningRoom.class.getResource("Assets/WrittenPaper.png"));
            BufferedImage cloneCircle = ImageIO.read(CloningRoom.class.getResource("Assets/CloneCircle.png"));
            BufferedImage table = ImageIO.read(CloningRoom.class.getResource("Assets/TableFront.png"));
            BufferedImage cloneMoon = ImageIO.read(CloningRoom.class.getResource("Assets/CloneMoon.png"));
            BufferedImage syringe = ImageIO.read(CloningRoom.class.getResource("Assets/Syringe.png"));
            BufferedImage chair = ImageIO.read(CloningRoom.class.getResource("Assets/ChairLeft.png"));
            BufferedImage paperStack = ImageIO.read(CloningRoom.class.getResource("Assets/PaperStack.png"));
            BufferedImage cloneTriangle = ImageIO.read(CloningRoom.class.getResource("Assets/CloneTriangle.png"));

            //a2 side
            BufferedImage pot = ImageIO.read(CloningRoom.class.getResource("Assets/PlantPot.png"));
            BufferedImage clone = ImageIO.read(CloningRoom.class.getResource("Assets/Clone.png"));
            BufferedImage button = ImageIO.read(CloningRoom.class.getResource("Assets/Button.png"));
            BufferedImage screen = ImageIO.read(CloningRoom.class.getResource("Assets/Screen.png"));
            BufferedImage testTube = ImageIO.read(CloningRoom.class.getResource("Assets/TestTube.png"));
            BufferedImage plantPod = ImageIO.read(CloningRoom.class.getResource("Assets/BigPlantPod.png"));
            BufferedImage plantPodLayered = ImageIO.read(CloningRoom.class.getResource("Assets/ManyPlantPod.png"));

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

            //a1 side 
            items.add(new Item(30, 30, 60, 120, cloneMoon));
            items.add(new Item(30, 130, 60, 120, cloneTriangle));
            items.add(new Item(30, 230, 60, 120, cloneCircle));
            items.add(new Item(20, 355, 280, 100, wall)); 
            items.add(new Item(400, 355, 100, 100, wall)); 
            items.add(new Item(160, 370, 100, 60, wallScreen)); 
            items.add(new Item(290, 25, 60, 100, hologramScreen));
            items.add(new Item(210, 200, 170, 50, table));
            items.add(new Item(400, 55, 50, 70, standScreen));
            items.add(new Item(240, 210, 15, 25, paper));
            items.add(new Item(245, 215, 15, 25, paper));
            items.add(new Item(260, 210, 15, 25, paper));
            items.add(new Item(300, 210, 10, 15, syringe));
            items.add(new Item(325, 200, 25, 30, paperStack));
            items.add(new Item(340, 205, 25, 25, paperStack));
            items.add(new Item(265, 220, 40, 70, chair));
            items.add(new Item(50, 460, 15, 20, binary));
            items.add(new Item(30, 425, 100, 70, tank));


            //a2 side
            items.add(new Item(700, 10, 10, 310, sideWall));
            items.add(new Item(700, 220, 100, 100, wall));
            items.add(new Item(860, 220, 140, 100, wall));
            items.add(new Item(720, 30, 100, 60, wallScreen));
            items.add(new Item(830, 90, 160, 60, metalTable));
            items.add(new Item(835, 75, 50, 35, screen));
            items.add(new Item(885, 75, 50, 35, screen));
            items.add(new Item(935, 75, 50, 35, screen));
            items.add(new Item(835, 40, 50, 35, screen));
            items.add(new Item(885, 40, 50, 35, screen));
            items.add(new Item(935, 40, 50, 35, screen));
            items.add(new Item(925, 250, 60, 120, clone));
            items.add(new Item(925, 350, 60, 120, clone));
            items.add(new Item(925, 450, 60, 120, clone));
            items.add(new Item(565, 20, 60, 125, plantPodLayered));
            items.add(new Item(630, 20, 60, 125, plantPod));
            items.add(new Item(620, 420, 160, 50, table));
            items.add(new Item(710, 250, 60, 100, hologramScreen));
            items.add(new Item(670, 425, 15, 25, pot));
            items.add(new Item(690, 425, 30, 25, testTube));
            items.add(new Item(645, 435, 10, 10, button));
            items.add(new Item(725, 425, 15, 25, paper));
            items.add(new Item(735, 430, 15, 25, paper));

        } catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }
}

