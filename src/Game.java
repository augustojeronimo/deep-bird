import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable
{
    public static boolean debug = false;
    
    private static boolean running;

    public static final int WIDTH = 640, HEIGHT = 480;

    public static int frameCounter = 0, seconds = 0, points = 0;


    public static Player player;
    public static KeyHandler controls;
    public static ObstacleManager obsManager;

    private static Thread gameThread;
    
    public Game()
    {
        gameThread = new Thread(this);

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);

        player = new Player();
        controls = new KeyHandler();
        this.addKeyListener(controls);

        obsManager = new ObstacleManager();
    }
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Deep Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        Game game = new Game();
        frame.add(game);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        start();
    }

    public static void start()
    {
        gameThread.start();
    }

    public static void stop()
    {
        running = false;
    }

    @Override
    public void run()
    {
        running = true;

        double frameInterval = 1_000_000_000 / 60;
        double nextFrame = System.nanoTime() + frameInterval;

        while (running) {
            update();
            render();

            try {
                double interval = (nextFrame - System.nanoTime()) / 1_000_000;
                nextFrame += frameInterval;

                if (interval > 0) {
                    Thread.sleep((long) interval);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update()
    {
        if (frameCounter > 60) {
            frameCounter = 0;
            seconds++;
        }
        frameCounter++;

        obsManager.update();
        player.update();
    }

    public void render()
    {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
        g2.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));

        // Render
        backgroundRender(g2);

        player.render(g2);
        obsManager.render(g2);

        foregroundRender(g2);

        g2.dispose();
        bs.show();
    }

    private void backgroundRender(Graphics2D g2)
    {
        Color water = new Color(16, 16, 64);
        Color rock = new Color(10, 10, 18);
        Color floor = new Color(8, 8, 16);

        // Water
        g2.setColor(water);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // Floor
        g2.setColor(floor);
        g2.rotate(Math.toRadians(-5), WIDTH/2, 440);
        g2.fillRect(-20, 400, WIDTH +40, 150);
        g2.rotate(Math.toRadians(5), WIDTH/2, 440);

        // Rocks
        g2.setColor(rock);
        g2.fillOval(560, 330, 200, 100);
        g2.setColor(rock.brighter());
        g2.fillOval(580, 390, 60, 50);
    }

    private void foregroundRender(Graphics2D g2)
    {
        // Color water = new Color(16, 16, 64);
        // Color rock = new Color(10, 10, 18);
        // Color floor = new Color(8, 8, 16);
        Color filter = new Color(16, 8, 16, 32);

        // Filter
        g2.setColor(filter);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        // UI
        g2.setColor(Color.BLACK);
        g2.fillPolygon(
            new int[] {500, 550, 570, 580, 600, 640, 640},
            new int[] {480, 460, 460, 450, 440, 440, 480},
            7
        );

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString(String.valueOf(points), WIDTH -30, HEIGHT -15);
    }
}