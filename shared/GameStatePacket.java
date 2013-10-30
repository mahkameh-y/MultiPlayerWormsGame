package shared;

import server.state.GameState;
import server.entity.*;
import shared.events.Event;
import shared.events.SinkEvent;
import shared.entity.*;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Jan 31, 2006
 * Time: 2:50:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameStatePacket implements Serializable {
    private WormEntity worm1, worm2;

    private Vector islandStaticEntities;
    private Event event;
    private int score1;
    private int score2;
    private Vector movedObjects;
    private Vector deletedObjects;

    public GameStatePacket(GameState gameState) {
        Worm worm1 = gameState.getWorm1();
        Worm worm2 = gameState.getWorm2();
        Vector movedObjects = gameState.getMovingObjects();
        Vector deletedObjects = gameState.getDeletedObjects();
        Vector islandHoles = gameState.getIslandHoles();
        Vector walls = gameState.getWalls();
        boolean isSink = gameState.isSink();
/*
        this.worm1 = new WormEntity(worm1.getX()+(20 * worm1.getDirection()), worm1.getY()+20 , worm1.getState().toString(), worm1.getDirection(), worm1.isGameOver(),worm1.getScore(), worm1.getLife(), worm1.getEnergy(), worm1.getNumOfBullets(), worm1.getNumOfBombs(), worm1.getWeapon());
        this.worm2 = new WormEntity(worm2.getX()+(20 * worm1.getDirection()) , worm2.getY()+20 , worm2.getState().toString(), worm2.getDirection(), worm2.isGameOver(),worm2.getScore(), worm2.getLife(), worm2.getEnergy(), worm2.getNumOfBullets(), worm2.getNumOfBombs(), worm2.getWeapon());
*/
       this.worm1 = new WormEntity(worm1.getX(), worm1.getY()-3, worm1.getState().toString(), worm1.getDirection(), worm1.isGameOver(), worm1.getScore(), worm1.getLife(), worm1.getNumOfBullets(), worm1.getNumOfFlyingBombs(), worm1.getNumOfExplodingSheep(), worm1.getNumOfBazooka(), worm1.getWeapon());
        this.worm2 = new WormEntity(worm2.getX(), worm2.getY()-3, worm2.getState().toString(), worm2.getDirection(), worm2.isGameOver(), worm2.getScore(), worm2.getLife(), worm2.getNumOfBullets(), worm2.getNumOfFlyingBombs(), worm2.getNumOfExplodingSheep(), worm2.getNumOfBazooka(), worm2.getWeapon());


        if (isSink) {
            event = new SinkEvent();
        }

        this.deletedObjects = new Vector();
        this.movedObjects = new Vector();
        this.islandStaticEntities = new Vector();
//        this.walls = new Vector();
        for (int i = 0; i < deletedObjects.size(); i++) {
            Entity entity = (Entity) deletedObjects.get(i);
            if (entity instanceof Bomb)
                this.deletedObjects.add(new SheepEntity(entity.getX(), entity.getY()));
            else if (entity instanceof FlyingBomb)
                this.deletedObjects.add(new FlyingBombEntity(entity.getX(), entity.getY()));
            else if (entity instanceof Bazooka) {
                Bazooka bazooka = (Bazooka) entity;
                this.deletedObjects.add(new BazookaEntity(bazooka.getX(), bazooka.getY(), bazooka.getDirection()));
            }
        }
        for (int i = 0; i < islandHoles.size(); i++) {
            this.islandStaticEntities.add(islandHoles.get(i));
        }
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = (Wall) walls.get(i);
            this.islandStaticEntities.add(new WallEntity(wall.getX(), wall.getY()));
        }

        for (int i = 0; i < movedObjects.size(); i++) {
            Entity entity = (Entity) movedObjects.get(i);
            if (entity instanceof Bullet) {
                Bullet bulletEntity = (Bullet) entity;
                int x = 0;
                if (bulletEntity.getDirection() == -1)
                    x = bulletEntity.getX();
                else
                    x = bulletEntity.getX();

                BulletEntity bullet = new BulletEntity(x, bulletEntity.getY(), bulletEntity.getDirection());
                this.movedObjects.add(bullet);
            } else if (entity instanceof Bomb) { //camellia
                SheepEntity sheep = new SheepEntity(entity.getX(), entity.getY());
                this.movedObjects.add(sheep);
            } else if (entity instanceof Prize) {
                GiftEntity giftEntity = new GiftEntity(entity.getX(), entity.getY());
                this.movedObjects.add(giftEntity);
            } else if (entity instanceof Death) {
                DeathEntity deathEntity = new DeathEntity(entity.getX(), entity.getY());
                this.movedObjects.add(deathEntity);
            } else if (entity instanceof FlyingBomb) {
                FlyingBombEntity flyingBombEntity = new FlyingBombEntity(entity.getX(), entity.getY());
                this.movedObjects.add(flyingBombEntity);
            } else if (entity instanceof Bazooka) {
                Bazooka bazooka = (Bazooka) entity;
                BazookaEntity bazookaEntity = new BazookaEntity(entity.getX(), entity.getY(), bazooka.getDirection());

                this.movedObjects.add(bazookaEntity);
            }
        }
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public WormEntity getWorm1() {
        return worm1;
    }

    public WormEntity getWorm2() {
        return worm2;
    }

    public Vector getMovedObjects() {
        return movedObjects;
    }

    public Vector getDeletedObjects() {
        return deletedObjects;
    }

    public Vector getIslandStaticEntities() {
        return islandStaticEntities;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

}
