import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    private boolean up = false, interval = false;

    public boolean jump()
    {
        boolean aux = up;

        up = false;

        return aux;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE && !interval) {
            up = true;
            interval = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE) {
            interval = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }
    
}
