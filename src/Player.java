import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player
{
    public int positionX, positionY, width, height, speed, gravity, max_speed;
    public Color color;
    public static Rectangle hitbox;
    
    public Player()
    {
        this.width = 40;
        this.height = this.width;

        hitbox = new Rectangle(5, 5, 30, 30);
        
        this.positionX = 100;
        this.positionY = Game.HEIGHT - this.height;

        this.max_speed = 6;
        this.gravity = 4;
        this.speed = 0;
        
        this.color = Color.DARK_GRAY;
    }

    public void update()
    {   
        if (Game.controls.jump()) {
            speed = -2*max_speed;
        }
        else if (Game.frameCounter % 3 == 0 && speed < max_speed) {
            speed += gravity;
        }

        if (speed > 0 && positionY + height >= Game.HEIGHT) {
            positionY = Game.HEIGHT - height;
        }
        else if (speed < 0 && positionY <= 0) {
            positionY = 0;
        }
        else {
            positionY += speed;
        }
    }

    public void render(Graphics2D g2)
    {
        // Rotate
        g2.rotate(Math.toRadians(speed), positionX + width/2, positionY + height/2);
        
        // Tail
        g2.setColor(color);
        g2.fillRect(positionX -10, positionY, 8, height);
        g2.setColor(color.darker());
        g2.fillRect(positionX -5, positionY +5, width -10, height -10);
        
        // Body
        g2.setColor(color);
        g2.fillRect(positionX, positionY, width, height);

        // Glass
        g2.setColor(Color.BLUE.darker());
        g2.fillRect(positionX +10, positionY +5, width -10, height -10);
        g2.setColor(Color.BLUE.brighter());
        g2.fillRect(positionX +15, positionY +10, width -15, height -20);

        // Revert Rotate
        g2.rotate(Math.toRadians(-speed), positionX + width/2, positionY + height/2);


        if (Game.debug) {
            g2.setColor(Color.RED);
            g2.fillRect(positionX+hitbox.x, positionY+hitbox.y, hitbox.width, hitbox.height);
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle(positionX+hitbox.x, positionY+hitbox.y, hitbox.width, hitbox.height);
    }
}
