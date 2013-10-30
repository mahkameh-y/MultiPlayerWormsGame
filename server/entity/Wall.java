package server.entity;

import shared.GUIScales;

import java.awt.*;

import server.state.GameState;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 14, 2006
 * Time: 11:18:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Wall extends Entity {
    public Wall(int X, int Y) {
        super(X, Y);
        setWidth(50);
        setHeight(50);
        this.colRect[0] = new CollisionRect(this, new Rectangle(X, Y, getWidth(),getHeight()));
    }


    public CollisionRect[] getCollisionRects() {
        return new CollisionRect[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean replaceInValidLocation() {
        Island island = Island.getInstance();
        if (!island.isValidWormSpace(this, 0, 0)) {
            return false;
        } else {
            int yOfs = island.getYOfsOfDownwardValidSpace(this,0);
            moveWithOffset(0, yOfs);
            if (getY() >= GUIScales.gameYDimension - GameState.getIslandY()) {
                
                moveToLocation(X,GUIScales.gameYDimension - GameState.getIslandY()-getHeight()/2);
            }
         }
        return true;
    }

}
