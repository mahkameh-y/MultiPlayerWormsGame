package server.entity;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Jan 29, 2006
 * Time: 4:01:06 PM
 * To change this template use File | Settings | File Templates.
 */
// east mishe chape ma ha !!!!!!
interface Collideable {
    public CollisionRect[] getCollisionRects();
}

class CollisionRect {
    private int N, S, E, W;
    private Collideable owner;

    public CollisionRect(Collideable rectOwner) {
        owner = rectOwner;
    }

    public CollisionRect(Collideable rectOwner, Rectangle bounds) {
        owner = rectOwner;
        setBounds(bounds);
    }

    public Collideable getOwner() {
        return owner;
    }

    public Rectangle getBounds() {
        return new Rectangle(E, N, E - W, N - S);
    }

    public int getEast() {
        return E;
    }

    public int getWest() {
        return W;
    }

    public int getNorth() {
        return N;
    }

    public int getSouth() {
        return S;
    }

    public int getWidth() {
        return W - E;
    }

    public int getHeight() {
        return S - N;
    }

    public void setBounds(int north, int south, int east, int west) {
        N = north;
        S = south;
        E = east;
        W = west;
    }

    public void setBounds(Rectangle bounds) {
        N = bounds.y;
        S = bounds.y + bounds.height;
        E = bounds.x;
        W = bounds.x + bounds.width;
    }

    public void recenter(Point p) {
        recenter(p.x, p.y);
    }

    public void recenter(int newX, int newY) {
        int xRadius = (E - W) / 2;      // ye adade manfi mishe ha
        E = newX + xRadius;
        W = newX - xRadius;
        int yRadius = (N - S) / 2;
        N = newY + yRadius;
        S = newY - yRadius;
    }

    public void move(int xOfs, int yOfs) {
        E += xOfs;
        W += xOfs;
        N += yOfs;
        S += yOfs;
    }

    public boolean collides(CollisionRect otherRect) {
        boolean horizOverlap = (((otherRect.W >= this.E) && (otherRect.E <= this.E)) || ((otherRect.W >= this.W) && (otherRect.E <= this.W)));
        boolean vertOverlap = (((otherRect.S >= this.N) && (otherRect.N <= this.N)) || ((otherRect.S >= this.S) && (otherRect.N <= this.S)));
        return horizOverlap && vertOverlap;
    }

}
