//package main.java;

import main.java.Direction;
import main.java.Sprite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
 * Created by Brian Shin. Copyright 2018.
 * NOTES: run ./gradlew.bat jar for compilation executable.
 */
public class Game implements Runnable {

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    JFrame frame;
    Canvas canvas;
    BufferStrategy bufferStrategy;

    public Game() {
        frame = new JFrame("Hamlet Game by Brian Shin");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        canvas.addMouseListener(new MouseControl());
        canvas.addKeyListener(new KeyControl());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        canvas.requestFocus();
    }

    private class KeyControl extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_W) {
                hamlet.setDirection(Direction.UP);
                if(!collision(hamlet)) {
                    hamlet.setY(hamlet.getY() - 5);
                } else if ((getCollDir(hamlet) != hamlet.getDirection())) {
                    hamlet.setY(hamlet.getY() - 5);
                }
            }
            if (key == KeyEvent.VK_A) {
                hamlet.setDirection(Direction.LEFT);
                if(!collision(hamlet)) {
                    hamlet.setX(hamlet.getX() - 5);
                } else if ((getCollDir(hamlet) != hamlet.getDirection())) {
                    hamlet.setX(hamlet.getX() - 5);
                }
            }
            if (key == KeyEvent.VK_S) {
                hamlet.setDirection(Direction.DOWN);
                if(!collision(hamlet)) {
                    hamlet.setY(hamlet.getY() + 5);
                } else if ((getCollDir(hamlet) != hamlet.getDirection())) {
                    hamlet.setY(hamlet.getY() + 5);
                }
            }
            if (key == KeyEvent.VK_D) {
                hamlet.setDirection(Direction.RIGHT);
                if(!collision(hamlet)) {
                    hamlet.setX(hamlet.getX() + 5);
                } else if ((getCollDir(hamlet) != hamlet.getDirection())) {
                    hamlet.setX(hamlet.getX() + 5);
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            /*int key = e.getKeyCode();
            if (key == KeyEvent.VK_W) {
            } else if (key == KeyEvent.VK_A) {
            } else if (key == KeyEvent.VK_S) {
            } else if (key == KeyEvent.VK_D) {
            } */
        }
    }

    private class MouseControl extends MouseAdapter {
        boolean mouseLeftPressed, mouseRightPressed;

        public void mouseClicked(MouseEvent e) {}
        public void mouseDragged(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseMoved(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {
            switch(e.getButton()) {
                case MouseEvent.BUTTON1:
                    mouseLeftPressed = true;
                    break;
                case MouseEvent.BUTTON3:
                    mouseRightPressed = true;
                    break;
            }
        }
        public void mouseReleased(MouseEvent e) {
            switch(e.getButton()) {
                case MouseEvent.BUTTON1:
                    mouseLeftPressed = false;
                    break;
                case MouseEvent.BUTTON3:
                    mouseRightPressed = false;
                    break;
            }
        }
    }

    Sprite background;
    Sprite foreground;
    Sprite hamlet;
    Sprite death;
    Sprite deathScreen;
    Sprite test;

    BufferedImage bg;
    BufferedImage fg;
    BufferedImage hammy;
    BufferedImage deathy;
    BufferedImage deathScreenImg;
    BufferedImage testy;

    public void initGame() {

        // TESTING
        /*ImageIcon bg = new ImageIcon("src/main/resources/bg.png");
        background = new Sprite(bg.getImage(), 0, 0);
        ImageIcon hammy = new ImageIcon("src/main/resources/hamlet.png");
        hamlet = new Sprite(hammy.getImage(), 300, 300); */

        try {
            bg = ImageIO.read(getClass().getResource("/assets/bg.png")); //start with /main/... for intellij
            fg = ImageIO.read(getClass().getResource("/assets/fg.png"));
            hammy = ImageIO.read(getClass().getResource("/assets/hamlet.png"));
            deathy = ImageIO.read(getClass().getResource("/assets/hamlet.png")); //TODO: change to real death sprite when made
            deathScreenImg = ImageIO.read(getClass().getResource("/assets/deathscreen.png"));
            testy = ImageIO.read(getClass().getResource("/assets/shield.png"));

            background = new Sprite(bg, 0, 0);
            foreground = new Sprite(fg, 0, 0);
            hamlet = new Sprite(hammy, 300, 300);
            death = new Sprite(deathy, 400, 400);
            deathScreen = new Sprite(deathScreenImg, 0, 0);
            test = new Sprite(testy, 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    long desiredFPS = 60;
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
    boolean running = true;

    public void run() {

        long beginLoopTime;
        long endLoopTime;
        long currentUpdateTime = System.nanoTime();
        long lastUpdateTime;
        long deltaLoop;

        initGame();

        while(running) {
            beginLoopTime = System.nanoTime();

            render();

            lastUpdateTime = currentUpdateTime;
            currentUpdateTime = System.nanoTime();
            update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));

            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;

            if(deltaLoop > desiredDeltaLoop) {
                //Do nothing.
            } else {
                try {
                    Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
                } catch(InterruptedException e) {
                    //Do nothing.
                }
            }
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(background.getImage(), background.getX(), background.getY(), null);
        g.drawImage(foreground.getImage(), foreground.getX(), background.getY(), null);
        render(g);
        g.dispose();
        bufferStrategy.show();
    }

    /**
     * Update method; game logic.
     */
    protected void update(int deltaTime) {
        if(getItemCollision(hamlet, test)) { // For the "collection" of items
            hamlet.setImage(testy);
            test.setVisible(false);
        }
        if(getItemCollision(hamlet, death)) { // RIP
            hamlet.die();
        }
    }

    /**
     * Main rendering method.
     */
    protected void render(Graphics2D g) {
        if(hamlet.isVisible()) {
            g.drawImage(hamlet.getImage(), hamlet.getX(), hamlet.getY(), null);
        } else {
            g.drawImage(deathScreen.getImage(), deathScreen.getX(), deathScreen.getY(), null);
        }
        if(death.isVisible()) g.drawImage(death.getImage(), death.getX(), death.getY(), null);
        if(test.isVisible()) g.drawImage(test.getImage(), test.getX(), test.getY(), null);
    }

    public boolean collision(Sprite s) {
        return (s.getX() < 0 || s.getY() < 0 || s.getX() > (WIDTH - 30) || s.getY() > (HEIGHT - 45));
    }

    public Direction getCollDir(Sprite s) {
        if(s.getX() < 0) {
           return Direction.LEFT;
        } else if(s.getY() < 0) {
            return Direction.UP;
        } else if(s.getX() > (WIDTH - 30)) {
            return Direction.RIGHT;
        } else if(s.getY() > (HEIGHT - 45)) {
            return Direction.DOWN;
        } else {
            return Direction.NONE;
        }
    }

    public Rectangle getObjectBounds(Sprite s) {
        Rectangle bounds;
        int sSizeX = s.getImage().getWidth(null);
        int sSizeY = s.getImage().getHeight(null);

        bounds = new Rectangle(s.getX(), s.getY(), sSizeX, sSizeY);
        return bounds;
    }

    public boolean getItemCollision(Sprite s1, Sprite s2) {
        Rectangle s1Bounds = new Rectangle(getObjectBounds(s1));
        Rectangle s2Bounds = new Rectangle(getObjectBounds(s2));

        if(s1.isVisible() && s2.isVisible()) {
            return s1Bounds.intersects(s2Bounds);
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Game ex = new Game();
        new Thread(ex).start();
    }

}