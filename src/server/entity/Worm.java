package server.entity;

import server.state.*;
import server.gameEngine.GameEngine;
import shared.GUIScales;
import shared.Weapon;
import shared.GameInfo;
import shared.weapons.Shotgun;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: a.moallem
 * Date: Jan 27, 2006
 * Time: 11:44:18 AM
 * To change this template use File | Settings | File Templates.
 */

public class Worm extends Entity implements Collideable {
    private shared.Weapon weapon;
    private WormState state;
    private int fallingYspeed = GUIScales.wormFallSpeed;
    private byte direction; //1 = right -1=left
    private boolean gameOver;
    private int score;
    private int numOfLives;
    private int numOfBullets;
    private int numOfFlyingBombs;
    private int numOfExplodingSheep;
    private int numOfBazooka;
    private int initialX;
    private int initialY;
    private byte initialDirection;

    private static int eachLifeScore = 200;

    public Worm(int X, int Y, byte direction) {
        super(X, Y);
        this.initialX = X;
        this.initialY = Y;
        this.initialDirection = direction;
        this.gameOver = false;
        this.numOfLives = shared.InitialWormConfig.NUM_OF_LIVES;
        weapon = new Shotgun();
        reset();
    }

    public void reset() {
        if (GameEngine.level == GameInfo.HARD) {
            this.numOfBullets = shared.InitialWormConfig.NUM_OF_BULLETS_HARD;
            this.numOfBazooka = shared.InitialWormConfig.NUM_OF_BAZOOKA_HARD;
            this.numOfExplodingSheep = shared.InitialWormConfig.NUM_OF_EXPLODINGSHEEP_HARD;
            this.numOfFlyingBombs = shared.InitialWormConfig.NUM_OF_BOMBS_HARD;
        } else {
            this.numOfBullets = shared.InitialWormConfig.NUM_OF_BULLETS_EASY;
            this.numOfBazooka = shared.InitialWormConfig.NUM_OF_BAZOOKA_EASY;
            this.numOfExplodingSheep = shared.InitialWormConfig.NUM_OF_EXPLODINGSHEEP_EASY;
            this.numOfFlyingBombs = shared.InitialWormConfig.NUM_OF_BOMBS_EASY;

        }
        this.X = initialX;
        this.Y = initialY;
        setState(new NormalState());
        this.direction = initialDirection;
    }

    public static Worm createWormWithInitialXY(int x, int y, byte direction) {
        return new Worm(x, y, direction);
    }

    public CollisionRect[] getCollisionRects() {
        return colRect;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getNumOfBullets() {
        return numOfBullets;
    }

    public void setNumOfBullets(int numOfBullets) {
        this.numOfBullets = numOfBullets;
    }

    public int getNumOfFlyingBombs() {
        return numOfFlyingBombs;
    }

    public int getNumOfExplodingSheep() {
        return numOfExplodingSheep;
    }

    public int getNumOfBazooka() {
        return numOfBazooka;
    }

    public int getLife() {
        return numOfLives;
    }

    public void setLife(int life) {
        this.numOfLives = life;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public WormState getState() {
        return state;
    }

    public byte getDirection() {
        return direction;
    }

    public int getNumOfLives() {
        return numOfLives;
    }

    public void setState(WormState state) {
        //tannaz2
        this.state = state;
        if (state instanceof NormalState) {
            setWidth(40);
            setHeight(50);
        }
        if (state instanceof JumpingState) {
            setWidth(30);
            setHeight(50);
        }
        this.colRect[0] = new CollisionRect(this, new Rectangle(X, Y, getWidth(), getHeight()));
    }

    public void setDirection(int direction) {
        this.direction = (byte) direction;

    }

    public shared.Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(shared.Weapon weapon) {
        this.weapon = weapon;
    }

    public void updateScore(int point) {
        score += point;
        if ((score >= eachLifeScore) && (numOfLives < shared.InitialWormConfig.NUM_OF_LIVES)) {
            score = score % eachLifeScore;
            numOfLives++;
        }
    }


    public int getYCenter() {
        return (Y + getSouht()) / 2;
    }

    //tannaz2
    public int getWeapenY() {
        return (Y + (9 * getHeight() / 10));
    }


    public int getXCenter() {
        return (X + getWest()) / 2;
    }


    public int getCollisionRectHeigthOfNormalState() {
        return 40;
    }

    public int getCollisionRectWidthOfNormalState() {
        return 40;
    }

    public int getCollisionRectHeigthOfJumpState() {
        return 50;
    }

    public int getCollisionRectWidthOfJumpState() {
        return 10;
    }


    public int getForwardX() {
        // batavajoh be direction joloye kerm o mide bara sakhtane tir o .....
        if (direction == 1) {
            return getWest();
        } else if (direction == -1)
            return getEast();
        return -1;
    }

    public void kill(String deadType) {
        decrementNumOfLives();
        setEnergy();
        if (deadType.equals(shared.WormState.DEAD)) {
            setState(new DeadState());
        } else if (deadType.equals(shared.WormState.DRAWN)) {
            setState(new DrawnState());
        }
        if (numOfLives <= 0)
            gameOver = true;

    }

    private void decrementNumOfLives() {
        numOfLives--;
    }

    private void setEnergy() {

    }

    public void setFinalScore() {
        score = score + (numOfLives * eachLifeScore);
    }

    public void moveDown() {
        int yOfs = fallingYspeed;
        moveWithOffset(0, yOfs);
        if (getSouht() >= GUIScales.gameYDimension - GameState.getIslandY() + 50) {
            kill("DRAWN");
        }
    }

    public void move() {
        Island island = Island.getInstance();
        int Xofs = direction * island.getX_LENGTH_EACH_RECTANGLE();
        if (state instanceof NormalState) {
            if (island.isValidWormSpace(this, Xofs, 0)) {   //ya mire jolo ya mire jolo paieen
                int Yofs = 0;
                if (X + Xofs < 0) {
                    Xofs = 0;
                    Yofs = 0;
                } else
                    Yofs = island.getYOfsOfDownwardValidSpace(this, Xofs);//age sefr bashe yani fagaht bayad bere jolo
                moveWithOffset(Xofs, Yofs);
                // yani zire kerm
                if (getSouht() >= GUIScales.gameYDimension - GameState.getIslandY()) {
                    setState(new FallingState());
                }

            } else {  //ya bere bala ya inke tekoon nakhore kollan
                int Yofs = island.getYOfsOfUpwardValidSpace(this, Xofs);
                if (Yofs == -1) // yani nemitoone harekat kone
                    return;
                else { //yani mitoone ye jayee bala bere
                    moveWithOffset(Xofs, -Yofs);
                }
            }
        } else if (state instanceof JumpingState) { // to hava bar asar zadan masalan right faghat bayad x esh tagheer kone
            if (island.isValidWormSpace(this, Xofs, 0)) {
                moveWithOffset(Xofs, 0);
                JumpingState jumpState = (JumpingState) getState();
                setState(new NormalState());
                if (island.getYOfsOfDownwardValidSpace(this, Xofs) == 0) {
                    if (getSouht() >= GUIScales.gameYDimension - GameState.getIslandY()) {
                        setState(new FallingState());

                    }
                } else {//bayad be paresh edame bede
                    setState(jumpState);
                }
            }
        }
    }

    public void jump() {
        boolean up;
        int Xofs = ((JumpingState) state).getJumpingXSpeed() * direction;
        int Yofs = -((JumpingState) state).getJumpingYSpeed();
        if (Yofs < 0)
            up = true;
        else
            up = false;
        int statesHeightDifferences = (getCollisionRectHeigthOfJumpState() - getCollisionRectHeigthOfNormalState());
        Island island = Island.getInstance();
        if (X + Xofs < 0)   // be sare jazire reside va nabayad X tagheer kone
            Xofs = 0;
        if (island.isValidWormSpace(this, Xofs, Yofs)) {
            moveWithOffset(Xofs, Yofs)/*+ statesHeightDifferences)*/;

            if ((getSouht() >= GUIScales.gameYDimension - GameState.getIslandY())) { //too ab oftade
                setState(new FallingState());
                return;
            }
/*
            if (island.getYOfsOfDownwardValidSpace(this, Xofs) == 0) {
                setState(new NormalState());
                return;
            } else
*/
            ((JumpingState) state).setJumpingYSpeed(((JumpingState) state).getJumpingYSpeed() - 1);

        } else {
            if (!up) {//paieen ooamdadan ke ja nashode
                setState(new NormalState());
                Yofs = island.getYOfsOfDownwardValidSpace(this, Xofs);
                Yofs = Yofs /*+ statesHeightDifferences*/;
                moveWithOffset(Xofs, Yofs);
                return;
            } else {
                if (direction == 1)
                    direction = -1;
                else
                    direction = 1;
                ((JumpingState) state).setJumpingYSpeed(-((JumpingState) state).getJumpingYSpeed());

            }
        }
    }

    public Bullet shoot() {
        if (numOfBullets > 0) {
            numOfBullets--;
            return Bullet.createNewBullet(this);
        }
        return null;
    }

    public Bazooka createBazooka() {
        if (numOfBazooka > 0) {
            numOfBazooka--;
            return Bazooka.createNewBazooka(this);

        }
        return null;
    }

    public Bomb createExplodingSheep() {
        if (numOfExplodingSheep > 0) {
            numOfExplodingSheep--;
            return Bomb.createNewBomb(this);
        }
        return null;
    }

    public void moveToIslandSurface() {
        // System.out.println("--------------------");
//        System.out.println("(GUIScales.gameYDimension - GameState.getIslandY()) = " + (GUIScales.gameYDimension - GameState.getIslandY()));
//        System.out.println("X = " + X);
//        System.out.println("Y = " + Y);
        int yOfs = Island.getInstance().getYOfsOfDownwardValidSpace(this, 0);
//        System.out.println("yOfs = " + yOfs);
        if (yOfs != 0) {
            moveWithOffset(0, yOfs);
//             System.out.println("(GUIScales.gameYDimension - GameState.getIslandY()) = " + (GUIScales.gameYDimension - GameState.getIslandY()));
//            System.out.println("getSouht() = " + getSouht());
            if (getSouht() >= GUIScales.gameYDimension - GameState.getIslandY()) {
                setState(new FallingState());
//                System.out.println("falling");
            }
        }
    }

    public Wall createWall() {
        int x = getForwardX();
        int y = Y;
        if (direction == 1)
            return new Wall(x, y);
        else if (direction == -1) {
            // be andazeye width e wall
            return new Wall(x - 50 , y);
        }
        return null;
    }

    public FlyingBomb createFlyingBomb() {
        if (numOfFlyingBombs > 0) {
            numOfFlyingBombs--;
            return FlyingBomb.createNewFlyingBomb(this);
//            return new FlyingBomb(getForwardX(), getYCenter(), System.currentTimeMillis(), getDirection());
        }
        return null;
    }
}
