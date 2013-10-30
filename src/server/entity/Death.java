package server.entity;

import shared.GUIScales;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: a.moallem
 * Date: Mar 13, 2006
 * Time: 9:50:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Death extends Entity {

    private final long MAX_LIVE_TIME = 20000; //milliseconds
    private long startTime;
    private final int Y_SPEED = 5;


    public Death(int X, int Y, long startTime) {
        super(X, Y);
        this.startTime = startTime;
                //tannaz2
        setWidth(40);
        setHeight(40);
        this.colRect[0] = new CollisionRect(this, new Rectangle(X, Y, getWidth(), getHeight()));
    }

    public CollisionRect[] getCollisionRects() {
        return new CollisionRect[0];
    }

    public void moveDown() {
        Island island = Island.getInstance();
        int islandY = GUIScales.gameYDimension - island.getYLocation(X);
        if (Y + Y_SPEED < islandY) {
            moveWithOffset(0, Y_SPEED);
        } else {
            moveToLocation(X, islandY - getHeight());
        }
    }

    public boolean isFinished() {
        return (System.currentTimeMillis() - startTime > MAX_LIVE_TIME);
    }

}
