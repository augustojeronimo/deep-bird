import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Obstacle {
    public static int speed;
    public Rectangle top, bottom;

    public int centerY, positionX, width;
    public Color color;

    public boolean highlight;

    public Obstacle()
    {
        speed = 3;
        width = 60;
        highlight = false;

        Random r = new Random();
        centerY = r.nextInt(240)+120;

        top = new Rectangle();
        bottom = new Rectangle();
        configure();
    }

    public void update()
    {
        positionX -= speed;

        top.x = positionX;
        bottom.x = positionX;
    }

    public void render(Graphics2D g2)
    {
        if (highlight) {
            color = Color.DARK_GRAY;
        } else {
            color = Color.BLACK;
        }

        
        // Obstacles
        g2.setColor(color);
        
        g2.fillRect(top.x, top.y, top.width, top.height);
        g2.fillRect(top.x -5, top.height -50, top.width +10, 50);

        g2.fillRect(bottom.x, bottom.y, bottom.width, bottom.height);
        g2.fillRect(bottom.x -5, bottom.y, bottom.width +10, 50);

        // Shadows
        g2.setColor(Color.BLACK.brighter().brighter());
        
        g2.fillRect(top.x, top.y, top.width/3, top.height);
        g2.fillRect(top.x -5, top.height -50, top.width/3 +10, 50);

        g2.fillRect(bottom.x, bottom.y, bottom.width/3, bottom.height);
        g2.fillRect(bottom.x -5, bottom.y, bottom.width/3 +10, 50);
    }

    private void configure()
    {
        positionX = Game.WIDTH;

        top.width = width;
        top.height = centerY - 60;
        
        bottom.width = width;
        bottom.height = Game.HEIGHT - centerY - 60;
        
        top.x = positionX;
        top.y = 0;

        bottom.x = positionX;
        bottom.y = centerY + 60;
    }
}
