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


    Rectangle deathBox;

    Sprite background;
    Sprite foreground;
    Sprite hamlet;
    Sprite death;
    Sprite deathScreen;
    Sprite fightScreen;
    Sprite skull;
    Sprite crown;
    Sprite poison;
    Sprite shield;

    BufferedImage bg;
    BufferedImage fg;
    BufferedImage hammy;
    BufferedImage deathy;
    BufferedImage deathScreenImg;
    BufferedImage fightScreenImg;
    BufferedImage skully;
    BufferedImage crowny;
    BufferedImage poisony;
    BufferedImage hamletCrown;
    BufferedImage hamletSkull;
    BufferedImage hamletPoison;
    BufferedImage hamletShield;
    BufferedImage shieldy;

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
            deathy = ImageIO.read(getClass().getResource("/assets/death.png")); //TODO: change to real death sprite when made
            deathScreenImg = ImageIO.read(getClass().getResource("/assets/deathscreen.png"));
            fightScreenImg = ImageIO.read(getClass().getResource("/assets/fightscreen.png"));
            skully = ImageIO.read(getClass().getResource("/assets/skull.png"));
            crowny = ImageIO.read(getClass().getResource("/assets/crown.png"));
            poisony = ImageIO.read(getClass().getResource("/assets/poison.png"));
            hamletCrown = ImageIO.read(getClass().getResource("/assets/hamletcrown.png"));
            hamletSkull = ImageIO.read(getClass().getResource("/assets/hamletskull.png"));
            hamletPoison = ImageIO.read(getClass().getResource("/assets/hamletpoison.png"));
            hamletShield = ImageIO.read(getClass().getResource("/assets/hamletshield.png"));
            shieldy = ImageIO.read(getClass().getResource("/assets/shield.png"));

            background = new Sprite(bg, 0, 0);
            foreground = new Sprite(fg, 0, 0);
            hamlet = new Sprite(hammy, 455, 650);
            death = new Sprite(deathy, 500, 380);
            deathScreen = new Sprite(deathScreenImg, 0, 0);
            fightScreen = new Sprite(fightScreenImg, 0, 0);
            skull = new Sprite(skully, 75, 120);
            crown = new Sprite(crowny, 915, 540);
            poison = new Sprite(poisony, 900, 100);
            shield = new Sprite(shieldy, 200, 480);

            deathBox = new Rectangle(death.getX()-80, death.getY()-80, death.getImage().getWidth(null)+160, death.getImage().getHeight(null)+160);
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
        if(getItemCollision(hamlet, shield)) { // For the "collection" of items
            hamlet.setImage(hamletShield);
            shield.setVisible(false);
        }
        if(getItemCollision(hamlet, skull)) {
            hamlet.setImage(hamletSkull);
            skull.setVisible(false);
        }
        if(getItemCollision(hamlet, crown)) {
            hamlet.setImage(hamletCrown);
            crown.setVisible(false);
        }
        if(getItemCollision(hamlet, poison)) {
            hamlet.setImage(hamletPoison);
            poison.setVisible(false);
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
        if(skull.isVisible()) g.drawImage(skull.getImage(), skull.getX(), skull.getY(), null);
        if(crown.isVisible()) g.drawImage(crown.getImage(), crown.getX(), crown.getY(), null);
        if(poison.isVisible()) g.drawImage(poison.getImage(), poison.getX(), poison.getY(), null);
        if(shield.isVisible()) g.drawImage(shield.getImage(), shield.getX(), shield.getY(), null);
        if(enteredBoundary(hamlet, deathBox) && hamlet.isVisible()) {
            g.drawImage(fightScreen.getImage(), fightScreen.getX(), fightScreen.getY(), null);
        }
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

    public boolean enteredBoundary(Sprite s, Rectangle rect) {
        Rectangle sBounds = new Rectangle(getObjectBounds(s));
        return sBounds.intersects(rect);
    }

    public static void main(String[] args) {
        Game ex = new Game();
        new Thread(ex).start();
    }

}