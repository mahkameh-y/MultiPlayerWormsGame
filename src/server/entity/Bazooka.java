package server.entity;

import shared.GUIScales;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: a.moallem
 * Date: Mar 17, 2006
 * Time: 8:37:19 AM
 * To change this template use File | Settings | File Templates.
 */
//azin
public class Bazooka extends Entity implements Collideable {

    private int speed;
    private byte direction;
    private int XStartPoint;
    private Worm creator;
    //tannaz2
    private static int bazookaWidth = 30;
    private static int bazookaHeight = 20;

    private Bazooka(int X, int Y, byte direction, int startX, Worm creator) {
        super(X, Y);
        this.speed = GUIScales.bulletSpeed;
        this.direction = direction;
        this.XStartPoint = startX;
        this.creator = creator;
        //tannaz2
        setWidth(bazookaWidth);
        setHeight(bazookaHeight);
        this.colRect[0] = new CollisionRect(this, new Rectangle(X, Y, getWidth(), getHeight()));
    }

    //tannaz2
    public static Bazooka createNewBazooka(Worm creator) {
        int centeralX = creator.getForwardX();
        int centeralY = creator.getWeapenY();
        int bulletX = centeralX - (bazookaWidth / 2);
        int bulletY = centeralY - (bazookaHeight / 2);

        Bazooka newBazooka = new Bazooka(bulletX, bulletY, creator.getDirection(), bulletX, creator);
        return newBazooka;
    }

    public CollisionRect[] getCollisionRects() {
        return new CollisionRect[0];
    }

    public boolean move() {
        int xOfs = speed * direction;
        moveWithOffset(xOfs, 0);
        if (Math.abs(X - XStartPoint) >= 650)
            return false; // finished
        return true;      //continue
    }

    public Worm getCreator() {
        return creator;
    }

    public byte getDirection() {
        return direction;
    }

    public void setDirection(byte direction) {
        this.direction = direction;
    }

    public int getForwardX() {
        // batavajoh be direction joloye kerm o mide bara sakhtane tir o .....
        if (direction == 1) {
            return getWest();
        } else if (direction == -1)
            return getEast();
        return -1;
    }


}
