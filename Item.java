import java.awt.*;
import java.awt.image.*;

public class Item {
    private int x, y, width, height;
    private BufferedImage roomItem;

    public Item(int x, int y, int width, int height, BufferedImage roomItem){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.roomItem = roomItem;
    }

    public void draw(Graphics2D g2d){
        if(roomItem != null){
            g2d.drawImage(roomItem, x, y, width, height, null);
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public BufferedImage getItem(){
        return roomItem;
    }

}
