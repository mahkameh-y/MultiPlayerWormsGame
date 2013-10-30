package server.entity;


import shared.GUIScales;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: tannaz
 * Date: Feb 1, 2006
 * Time: 11:51:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bullet extends Entity implements Collideable {
    private int speed;
    private byte direction;
    private int XStartPoint;
    private Worm creator;
    //tananz2
    private static int bulletWidth=15;
    private static int bulletHeight=10;

    private final static int maxRange = 700;


    private Bullet(int x, int y, byte direction, int startX, Worm creator) {
        super(x, y);
        this.creator = creator;
        this.speed = GUIScales.bulletSpeed;
        this.direction = direction;
        this.XStartPoint = startX;
                //tannaz2
        setWidth(bulletWidth);
        setHeight(bulletHeight);
        this.colRect[0] = new CollisionRect(this, new Rectangle(X, Y, getWidth(), getHeight()));
    }

    public static Bullet createNewBullet(Worm creator) {
        int centeralX = creator.getForwardX();
        int centeralY = creator.getWeapenY();
        int bulletX = centeralX - ( bulletWidth / 2);
        int bulletY = centeralY - (bulletHeight / 2);

        Bullet newBullet = new Bullet(bulletX, bulletY, creator.getDirection(), bulletX, creator);
        return newBullet;
    }

    public byte getDirection() {
        return direction;
    }

    public Worm getCreator() {
        return creator;
    }

    public void setCreator(Worm creator) {
        this.creator = creator;
    }

    public boolean moveBullet() {
        int xOfs = speed * direction;
        moveWithOffset(xOfs, 0);
        if (Math.abs(X - XStartPoint) >= maxRange)
            return false; // finished
        return true;      //continue
    }

    public CollisionRect[] getCollisionRects() {
        return new CollisionRect[0];
    }

}
