import java.awt.image.*;
import java.util.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TheresholdRoom extends Room{
    public TheresholdRoom(){
        super(loadRoom());
    }

    private static ArrayList<Item> loadRoom(){
        ArrayList<Item> items = new ArrayList<>();
        try{
            BufferedImage floor = ImageIO.read(ExperimentRoom.class.getResource("Assets/ExitFloor.png"));
            BufferedImage sideWall = ImageIO.read(ExperimentRoom.class.getResource("Assets/ExitSideWall.png"));
            BufferedImage bg = ImageIO.read(ExperimentRoom.class.getResource("Assets/Background.png"));  
            BufferedImage exit = ImageIO.read(ExperimentRoom.class.getResource("Assets/Exit.png"));
            BufferedImage wall = ImageIO.read(ExperimentRoom.class.getResource("Assets/ExitWall.png"));
            BufferedImage clock = ImageIO.read(ExperimentRoom.class.getResource("Assets/Clock.png"));
            BufferedImage laserUp = ImageIO.read(ExperimentRoom.class.getResource("Assets/LaserUp.png"));
            BufferedImage clone = ImageIO.read(ExperimentRoom.class.getResource("Assets/Clone.png"));
            BufferedImage shelf = ImageIO.read(ExperimentRoom.class.getResource("Assets/Bookshelf.png"));
            BufferedImage pot = ImageIO.read(ExperimentRoom.class.getResource("Assets/PlantPot.png"));
            BufferedImage cabinet = ImageIO.read(ExperimentRoom.class.getResource("Assets/Cabinet.png"));
            BufferedImage table = ImageIO.read(ExperimentRoom.class.getResource("Assets/TableFront.png"));
            BufferedImage metalTable = ImageIO.read(ExperimentRoom.class.getResource("Assets/MetalTableFront.png"));
            BufferedImage metalTableSide = ImageIO.read(ExperimentRoom.class.getResource("Assets/MetalTableSide.png"));
            BufferedImage tableSide = ImageIO.read(ExperimentRoom.class.getResource("Assets/TableSide.png"));
            BufferedImage paper = ImageIO.read(ExperimentRoom.class.getResource("Assets/WrittenPaper.png"));
            BufferedImage laser = ImageIO.read(ExperimentRoom.class.getResource("Assets/Laser.png"));
            BufferedImage box = ImageIO.read(ExperimentRoom.class.getResource("Assets/Box.png"));
            BufferedImage tank = ImageIO.read(ExperimentRoom.class.getResource("Assets/Tank.png"));
            BufferedImage paperStack = ImageIO.read(ExperimentRoom.class.getResource("Assets/PaperStack.png"));

            //walls
            items.add(new Item(0, 0, 1024, 768, bg));
            items.add(new Item(10, 40, 990, 540, floor));
            items.add(new Item(10, 10, 990, 100, wall));
            items.add(new Item(10, 10, 10, 650, sideWall));
            items.add(new Item(990, 10, 10, 650, sideWall));
            items.add(new Item(10, 580, 800, 100, wall));
            items.add(new Item(900, 580, 100, 100, wall));
            items.add(new Item(810, 580, 90, 100, exit));
            items.add(new Item(500, 10, 10, 230, sideWall));
            items.add(new Item(280, 200, 230, 40, wall));
            items.add(new Item(280, 200, 10, 100, sideWall));
            items.add(new Item(270, 300, 20, 40, wall));
            items.add(new Item(510, 170, 210, 40, wall));
            items.add(new Item(910, 10, 10, 110, sideWall));
            items.add(new Item(910, 100, 30, 40, wall));
            items.add(new Item(710, 170, 10, 90, sideWall));
            items.add(new Item(840, 170, 10, 90, sideWall));
            items.add(new Item(710, 220, 50, 40, wall));
            items.add(new Item(820, 220, 90, 40, wall));
            items.add(new Item(850, 300, 140, 40, wall));
            items.add(new Item(830, 390, 100, 40, wall));
            items.add(new Item(830, 380, 10, 10, sideWall));
            items.add(new Item(800, 490, 190, 40, wall));
            items.add(new Item(540, 390, 30, 40, wall));
            items.add(new Item(540, 380, 10, 10, sideWall));
            items.add(new Item(620, 390, 30, 40, wall));
            items.add(new Item(640, 380, 10, 10, sideWall));
            items.add(new Item(540, 270, 20, 40, wall));
            items.add(new Item(540, 260, 10, 10, sideWall));
            items.add(new Item(630, 300, 20, 40, wall));
            items.add(new Item(640, 290, 10, 10, sideWall));
            items.add(new Item(380, 440, 90, 40, wall));
            items.add(new Item(460, 430, 10, 10, sideWall));
            items.add(new Item(380, 440, 10, 90, sideWall));
            items.add(new Item(460, 290, 10, 10, sideWall));
            items.add(new Item(450, 300, 20, 40, wall));
            items.add(new Item(360, 290, 10, 10, sideWall));
            items.add(new Item(360, 300, 20, 40, wall));
            items.add(new Item(100, 20, 10, 150, sideWall));
            items.add(new Item(100, 170, 100, 40, wall));
            items.add(new Item(230, 520, 10, 60, sideWall));
            items.add(new Item(740, 490, 30, 40, wall)); 

            //normal objects 
            items.add(new Item(570, 420, 60, 90, clone)); 
            items.add(new Item(570, 490, 60, 90, clone)); 
            items.add(new Item(170, 40, 30, 30, clock)); 
            items.add(new Item(230, 70, 70, 50, shelf)); 
            items.add(new Item(530, 70, 70, 50, shelf));
            items.add(new Item(300, 40, 60, 80, shelf)); 
            items.add(new Item(160, 290, 50, 60, shelf)); 
            items.add(new Item(110, 290, 50, 60, shelf)); 
            items.add(new Item(215, 320, 30, 30, pot)); 
            items.add(new Item(110, 400, 30, 80, metalTableSide)); 
            items.add(new Item(110, 450, 100, 40, metalTable)); 
            items.add(new Item(115, 430, 15, 15, paper)); 
            items.add(new Item(120, 435, 15, 15, paper)); 
            items.add(new Item(160, 455, 30, 15, paperStack)); 
            items.add(new Item(250, 390, 30, 70, tableSide)); 
            items.add(new Item(300, 490, 60, 30, box)); 
            items.add(new Item(690, 330, 100, 30, table)); 
            items.add(new Item(750, 130, 60, 55, shelf)); 
            items.add(new Item(870, 325, 25, 20, pot)); 
            items.add(new Item(440, 470, 25, 20, pot)); 
            items.add(new Item(470, 530, 50, 40, table)); 
            items.add(new Item(390, 370, 30, 30, pot)); 
            items.add(new Item(220, 240, 45, 45, metalTable)); 
            items.add(new Item(645, 420, 45, 55, cabinet)); 
            items.add(new Item(390, 220, 45, 30, cabinet)); 
            items.add(new Item(180, 500, 40, 30, box)); 
            items.add(new Item(30, 90, 40, 50, box));
            items.add(new Item(35, 70, 20, 30, box));
            items.add(new Item(30, 530, 50, 40, table));
            items.add(new Item(945, 70, 40, 50, tank));

            //lasers
            items.add(new Item(760, 235, 60, 5, laser)); 
            items.add(new Item(460, 320, 5, 110, laserUp)); 
            items.add(new Item(545, 290, 5, 90, laserUp)); 
            items.add(new Item(570, 405, 50, 5, laser)); 
            items.add(new Item(650, 405, 180, 5, laser)); 
            items.add(new Item(640, 320, 5, 60, laserUp)); 
            items.add(new Item(380, 320, 70, 5, laser)); 
            items.add(new Item(770, 510, 30, 5, laser));

        } catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }
}
