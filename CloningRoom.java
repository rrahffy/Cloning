import java.awt.image.*;
import java.util.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CloningRoom extends Room{
    public CloningRoom(){
        super(loadRoom());
    }
    
    private static ArrayList<Item> loadRoom(){
        ArrayList<Item> items = new ArrayList<>();
        try{
            //both has it 
            BufferedImage floor = ImageIO.read(ExperimentRoom.class.getResource("Assets/Floor.png"));
            BufferedImage sideWall = ImageIO.read(ExperimentRoom.class.getResource("Assets/SideWall.png"));
            BufferedImage bg = ImageIO.read(ExperimentRoom.class.getResource("Assets/Background.png"));
            BufferedImage wall = ImageIO.read(ExperimentRoom.class.getResource("Assets/CloningRoomWall.png"));
            BufferedImage leftDoor = ImageIO.read(ExperimentRoom.class.getResource("Assets/A1Door.png"));
            BufferedImage rightDoor = ImageIO.read(ExperimentRoom.class.getResource("Assets/A2Door.png"));
            BufferedImage wallScreen = ImageIO.read(ExperimentRoom.class.getResource("Assets/WallScreen.png"));
            BufferedImage metalTable = ImageIO.read(ExperimentRoom.class.getResource("Assets/MetalTableFront.png"));
            BufferedImage hologramScreen = ImageIO.read(ExperimentRoom.class.getResource("Assets/HologramScreen.png"));

            //a1 side
            BufferedImage tank = ImageIO.read(ExperimentRoom.class.getResource("Assets/Tank.png"));
            BufferedImage standScreen = ImageIO.read(ExperimentRoom.class.getResource("Assets/StandingScreen.png"));
            BufferedImage paper = ImageIO.read(ExperimentRoom.class.getResource("Assets/WrittenPaper.png"));
            BufferedImage cloneCircle = ImageIO.read(ExperimentRoom.class.getResource("Assets/CloneCircle.png"));
            BufferedImage table = ImageIO.read(ExperimentRoom.class.getResource("Assets/TableFront.png"));
            BufferedImage cloneMoon = ImageIO.read(ExperimentRoom.class.getResource("Assets/CloneMoon.png"));
            BufferedImage syringe = ImageIO.read(ExperimentRoom.class.getResource("Assets/Syringe.png"));
            BufferedImage chair = ImageIO.read(ExperimentRoom.class.getResource("Assets/ChairLeft.png"));
            BufferedImage paperStack = ImageIO.read(ExperimentRoom.class.getResource("Assets/PaperStack.png"));
            BufferedImage cloneTriangle = ImageIO.read(ExperimentRoom.class.getResource("Assets/CloneTriangle.png"));

            //a2 side
            BufferedImage pot = ImageIO.read(ExperimentRoom.class.getResource("Assets/PlantPot.png"));
            BufferedImage clone = ImageIO.read(ExperimentRoom.class.getResource("Assets/Clone.png"));
            BufferedImage screen = ImageIO.read(ExperimentRoom.class.getResource("Assets/Screen.png"));
            BufferedImage testTube = ImageIO.read(ExperimentRoom.class.getResource("Assets/TestTube.png"));
            BufferedImage plantPod = ImageIO.read(ExperimentRoom.class.getResource("Assets/BigPlantPod.png"));
            BufferedImage plantPodLayered = ImageIO.read(ExperimentRoom.class.getResource("Assets/ManyPlantPod.png"));

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
            items.add(new Item(30, 420, 100, 60, tank));

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
            items.add(new Item(725, 425, 15, 25, paper));
            items.add(new Item(735, 430, 15, 25, paper));

             //will add/delete more things based on need and since we're changing parts of the story but so far this is the first room. I'll add wires in the final.
             //will add the booleans when you're done with player so I can check first if they'll work. planned booleans are: collidable, clue, gameOver, or do this in a for loop with getItem() or just go if statement with getItem()? 
        } catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }
}
