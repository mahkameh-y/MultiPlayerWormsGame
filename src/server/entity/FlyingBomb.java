package server.entity;

import server.state.*;
import shared.GUIScales;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: soft
 * Date: Mar 13, 2006
 * Time: 9:39:11 PM
 * To change this template use File | Settings | File Templates.
 */
//azin
public class FlyingBomb extends Entity implements Collideable {

    private final long MAX_LIVE_TIME = 4000; //milliseconds
    private long startTime;
    private FlyingBombState state;
    private byte direction;
    //tannaz2
    private static int flyingBombWidth=50;
    private static int flyingBombHeight=50;

    private FlyingBomb(int X, int Y, long startTime, byte direction) {
        super(X, Y);
        this.startTime = startTime;
        this.state = new FlyingBombJumpingState();
        this.direction = direction;
        //tannaz2

                setWidth(flyingBombWidth);
        setHeight(flyingBombHeight);
        this.colRect[0] = new CollisionRect(this, new Rectangle(X, Y, getWidth(), getHeight()));
    }

    public CollisionRect[] getCollisionRects() {
        return new CollisionRect[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isFinished() {
        return (System.currentTimeMillis() - startTime > MAX_LIVE_TIME);
    }

    public void move() {
        if (state instanceof FlyingBombJumpingState)
            jump();
        return;

    }

    public void jump() {
        boolean up;
        int Xofs = ((FlyingBombJumpingState) state).getJumpingXSpeed() * direction;
        int Yofs = -((FlyingBombJumpingState) state).getJumpingYSpeed();
        if (Yofs < 0)
            up = true;
        else
            up = false;

        Island island = Island.getInstance();
        if (X + Xofs < 0)   // be sare jazire reside va nabayad X tagheer kone
            Xofs = 0;
        if (island.isValidWormSpace(this, Xofs, Yofs)) {
            moveWithOffset(Xofs, Yofs);
            if ((Y + getHeight() >= GUIScales.gameYDimension - GameState.getIslandY())) { //too ab oftade
                setState(new FlyingBombNormalState());
                return;
            }
            ((FlyingBombJumpingState) state).setJumpingYSpeed(((FlyingBombJumpingState) state).getJumpingYSpeed() - 1);

        } else {
            if (!up) {//paieen ooamdadan ke ja nashode
                setState(new FlyingBombNormalState());
                Yofs = island.getYOfsOfDownwardValidSpace(this, Xofs);
                moveWithOffset(Xofs, Yofs);
                return;
            } else {
                if (direction == 1)
                    direction = -1;
                else
                    direction = 1;
                ((FlyingBombJumpingState) state).setJumpingYSpeed(-((FlyingBombJumpingState) state).getJumpingYSpeed());

            }
        }
    }

    public void setState(FlyingBombState state) {
        this.state = state;
    }

    public FlyingBombState getState() {
        return state;
    }
    //tannaz2

    public static FlyingBomb createNewFlyingBomb(Worm creator) {
        int centeralX = creator.getForwardX();
        int centeralY = creator.getWeapenY();
        int bombX;
        if (creator.getDirection() == -1)
            bombX = centeralX - flyingBombWidth;
        else
            bombX = centeralX;

        int bombY = centeralY - flyingBombHeight;
        FlyingBomb bomb = new FlyingBomb(bombX, bombY, System.currentTimeMillis(), creator.getDirection());
        return bomb;
    }
}
