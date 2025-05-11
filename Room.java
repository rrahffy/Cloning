import java.awt.*;
import java.util.*;

public abstract class Room{
    private ArrayList<Item> items;

    public Room(ArrayList<Item> items){
        this.items = items;
    }

    public void drawRoom(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for(Item item : items){
            g2d.drawImage(item.getItem(), item.getX(), item.getY(), item.getWidth(), item.getHeight(), null);
        }
    }

    public ArrayList<Item> getItems(){
        return items;
    } 

    // public void checkCollision(Player player){

    // }
}