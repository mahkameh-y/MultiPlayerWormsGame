package server.entity;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Feb 21, 2006
 * Time: 12:26:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bomb extends Entity implements Collideable {
    private Worm creator;
    //tannaz2
    private static int bombWidth = 40;
    private static int bombHeight = 40;

    private Bomb(int X, int Y, Worm creator) {
        super(X, Y);
        this.creator = creator;
        //tannaz2
        setWidth(bombWidth);
        setHeight(bombHeight);
        this.colRect[0] = new CollisionRect(this, new Rectangle(X, Y, getWidth(), getHeight()));
    }

    public Worm getCreator() {
        return creator;
    }

    public CollisionRect[] getCollisionRects() {
        return new CollisionRect[0];
    }


    public static Bomb createNewBomb(Worm creator) {
        int centeralX = creator.getForwardX();
        int centeralY = creator.getWeapenY();
        int bombX;
        if (creator.getDirection() == -1)
            bombX = centeralX - bombWidth;
        else
            bombX = centeralX;
        int bombY = centeralY - bombHeight;
        Bomb bomb = new Bomb(bombX, bombY, creator);
        int yOfs = Island.getInstance().getYOfsOfDownwardValidSpace(bomb, 0);
        if (!(yOfs == 0)) {
            bomb.moveWithOffset(0, yOfs);
        }
        return bomb;
    }
}
