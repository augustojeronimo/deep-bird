import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ObstacleManager
{
    public ArrayList<Obstacle> obstacles;

    public ObstacleManager()
    {
        obstacles = new ArrayList<>();
    }

    public void update()
    {
        if (Game.seconds % 2 == 0 && Game.frameCounter % 60 == 0) {
            generateObstacle();
        }

        for (Obstacle obs : obstacles) {
            
            if (obs.positionX + obs.width < 0) {
                obstacles.remove(obs);
                Game.points++;
            }
            
            obs.update();

            if (collision(obs, Game.player) && !Game.debug) {
                Game.stop();
            }
        }
    }

    public void render(Graphics2D g2)
    {
        for (Obstacle obs : obstacles) {
            obs.render(g2);
        }
    }

    public void generateObstacle()
    {
        obstacles.add(new Obstacle());
    }

    public boolean collision(Obstacle obs, Player p)
    {
        Rectangle hb = p.getHitbox();
        boolean collision = ((obs.positionX <= hb.x + hb.width) && (obs.positionX + obs.width >= hb.x) && (obs.top.height > hb.y || obs.bottom.y < hb.y + hb.height));

        obs.highlight = Game.debug ? collision : false;

        return collision;
    }
}
