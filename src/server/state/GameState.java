package server.state;

import server.entity.*;


import java.util.Vector;

import shared.GUIScales;
import shared.entity.HoleEntity;

/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Jan 25, 2006
 * Time: 3:14:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameState {
    private Worm worm1;
    private Worm worm2;
    private Vector movingObjects;
    private Vector staticObjects;
    private Vector deletedObjects;
    private Vector islandHoles;
    private Vector walls; // too har state toosh por mishe va ba'ade oon khali msihe
    private static int islandY;
    private boolean sink;

    public GameState() {
        //bayad moshakhasate worm ha ra az file naghsheye marboote entekhab konad
        //in object haram bayad az map bardare va dar dakhele vector ha add kone
        //ke kolan ba doonestane worm ha o object ha o iland hame chi maloom mishe
        worm1 = Worm.createWormWithInitialXY(50, 100, (byte) 1);
        worm2 = Worm.createWormWithInitialXY(1800, 100, (byte) -1);

//        worm1.moveToIslandSurface();
//        worm2.moveToIslandSurface();

        movingObjects = new Vector();
        staticObjects = new Vector();
        deletedObjects = new Vector();
        islandHoles = new Vector();
        walls = new Vector();
        sink = false;
        islandY = GUIScales.islandInitialY;
    }

    public static int getIslandY() {
        return islandY;
    }

    public static void setIslandY(int y) {
        islandY = y;
    }

    public void addMoveObject(Object object) {
        movingObjects.add(object);
    }

    public Vector getIslandHoles() {
        Vector preIslandHoles = (Vector) islandHoles.clone();
        islandHoles.removeAllElements();
        return preIslandHoles;
    }

    public Vector getWalls() {   //inam dar asl har dafe faghat yekie amma bara ehtiat
        Vector preWalls = (Vector) walls.clone();
        walls.removeAllElements();
        return preWalls;
    }

    public void addWall(Wall wall) {
        walls.add(wall);
    }

    public void setIslandHoles(Vector islandHoles) {
        this.islandHoles = islandHoles;
    }

    public Worm getWorm1() {
        return worm1;
    }

    public Worm getWorm2() {
        return worm2;
    }

    public Vector getMovingObjects() {
        return movingObjects;
    }

    public Vector getDeletedObjects() {
        Vector preDeletedObjects = (Vector) deletedObjects.clone();
        deletedObjects.removeAllElements();
        return preDeletedObjects;
    }

    public boolean inEvolution() {
        if ((worm1.getState() instanceof NormalState) &&
                worm2.getState() instanceof NormalState)

            if (!anyActiveMovingObject())
                return false;
        return true;
    }

    public boolean anyActiveMovingObject() {
        return (!(movingObjects.size() == 0));
    }

    public boolean gameIsFinished() {
        if (worm1.isGameOver() || worm2.isGameOver()) {
            return true;
        }
        return false;
    }

    public void addDeletedObjects(Entity entity) {
        deletedObjects.add(entity);
    }

    public void deleteMovingObjects(Bomb bomb) {
        movingObjects.remove(bomb);
    }

    public void addIslandHole(HoleEntity hole) {
        islandHoles.add(hole);
    }

    public boolean isSink() {
        boolean s = sink;
        sink = false;
        return s;
    }

    public void setSink() {
        sink = true;
    }

}
