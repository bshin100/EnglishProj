package main.java;

import java.awt.Image;

/**
 * Sprite Class
 * @author Brian Shin
 */
public class Sprite {

    private boolean visible;
    private Image image;
    protected int x;
    protected int y;
    protected Direction d;
    protected boolean dying;
    protected int dx;

    public Sprite() {
        visible = true;
    }

    public Sprite(Image image, int startX, int startY) {
        visible = true;
        this.image = image;
        this.x = startX;
        this.y = startY;
        d = Direction.UP;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(Direction d) {
        this.d = d;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Direction getDirection() {
        return d;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }
}