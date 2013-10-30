package server.entity;

import server.entity.CollisionRect;

/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Jan 28, 2006
 * Time: 11:03:57 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Entity implements Collideable {

    protected int X;
    protected int Y;
    protected CollisionRect[] colRect;
    private int height;
    private int width;


    public Entity(int X, int Y) {
        this.X = X;
        this.Y = Y;
        colRect = new CollisionRect[1];
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getNorth() {
        return Y;
    }

    public int getSouht() {
        return Y + getHeight();
    }

    public int getEast() {
        return X;
    }

    public int getWest() {
        return X + getWidth();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public CollisionRect[] getColRects() {
        return colRect;
    }

    public boolean intersectsWith(Entity other) {
        CollisionRect[] myRects = getColRects();
        CollisionRect[] otherRects = other.getColRects();
        for (int i = 0; i < myRects.length; i++) {
            for (int j = 0; j < otherRects.length; j++) {
                if (myRects[i].collides(otherRects[j]))
                    return true;
            }
        }
        return false;
    }

    public void updateEntityCollisionRects(int Xofs, int Yofs) {
        for (int i = 0; i < colRect.length; i++) {
            colRect[i].move(Xofs, Yofs);
        }
    }

    public void reCenterCollisionRects(int newX, int newY) {
        for (int i = 0; i < colRect.length; i++) {
            colRect[i].recenter(newX, newY);
        }
    }

    public void moveToLocation(int x, int y) {
        int xOfs = x - X;
        int yOfs = y - Y;
        X = x;
        Y = y;
        updateEntityCollisionRects(xOfs, yOfs);
    }

    public void moveWithOffset(int xOfs, int yOfs) {
        X = X + xOfs;
        Y = Y + yOfs;
        updateEntityCollisionRects(xOfs, yOfs);
    }

    //alan fagaht bara ye khooneyee ha hasta havaset abshe

    public int getXCenter() {
        return X + getWidth() / 2;
    }

    public int getYCenter() {
        return Y + getHeight() / 2;
    }


}
