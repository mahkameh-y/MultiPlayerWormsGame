package server.gameEngine;

import server.actions.*;
import server.events.DeathEvent;
import server.events.Event;
import server.events.PrizeEvent;
import server.events.SinkEvent;
import server.state.*;
import server.entity.*;
import shared.GUIScales;
import shared.entity.HoleEntity;
import shared.entity.holes.HoleType;
import shared.entity.holes.BombHole;
import shared.entity.holes.BazookaHole;
import shared.weapons.Shotgun;
import shared.Weapon;

import java.util.Random;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: a.moallem
 * Date: Jan 27, 2006
 * Time: 12:02:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameLogic {

    Random random = new Random();

    private int numOfPrizesEachTime;
    private int numOfDangersEachTime;
    private int prizeScore=10;
    private int killScore=50;

    public void setNumOfPrizesEachTime(int numOfPrizesEachTime) {
        this.numOfPrizesEachTime = numOfPrizesEachTime;
    }

    public void setNumOfDangersEachTime(int numOfDangersEachTime) {
        this.numOfDangersEachTime = numOfDangersEachTime;
    }

    protected GameState nextState(GameState currentState, Object actionOrEvent) {

        Worm worm1 = currentState.getWorm1();
        Worm worm2 = currentState.getWorm2();
        Island island = Island.getInstance();
        Vector movingObjects = currentState.getMovingObjects();
        if (actionOrEvent != null) {
            if (actionOrEvent instanceof Action) {
                if (((Action) actionOrEvent).isPlayer1())
                    applyAction((Action) actionOrEvent, worm1, currentState);
                else
                    applyAction((Action) actionOrEvent, worm2, currentState);
            } else if (actionOrEvent instanceof Event) {
                applyEvent((Event) actionOrEvent, currentState);
            }
        }
        updateMovingObjects(currentState, movingObjects);
        updateWorm(worm1);
        updateWorm(worm2);
        checkCollisionOfMovingObjects(worm1, worm2, movingObjects, currentState);
        return currentState;
    }

    private void checkCollisionOfMovingObjects(Worm worm1, Worm worm2, Vector movingObjects, GameState currentState) {
        for (int i = 0; i < movingObjects.size(); i++) {
            Object movingObj = movingObjects.elementAt(i);
            if (movingObj instanceof Bullet) {   //bullet
                Bullet bullet = (Bullet) movingObj;
                if (bullet.intersectsWith(worm1) && (!bullet.getCreator().equals(worm1))) {
                    movingObjects.remove(bullet);
                    worm1.kill(shared.WormState.DEAD);
                    worm2.updateScore(killScore);
                }
                if (bullet.intersectsWith(worm2) && (!bullet.getCreator().equals(worm2))) {
                    movingObjects.remove(bullet);
                    worm2.kill(shared.WormState.DEAD);
                    worm1.updateScore(killScore);
                }
            } else if (movingObj instanceof Bazooka) {
                Bazooka bazooka = (Bazooka) movingObj;
                if (bazooka.intersectsWith(worm1) && (!bazooka.getCreator().equals(worm1))) {
                    movingObjects.remove(bazooka);
                    worm1.kill(shared.WormState.DEAD);
                    worm2.updateScore(killScore);
                } else if (bazooka.intersectsWith(worm2) && (!bazooka.getCreator().equals(worm2))) {
                    movingObjects.remove(bazooka);
                    worm2.kill(shared.WormState.DEAD);
                    worm1.updateScore(killScore);
                }
                if (!Island.getInstance().isValidWormSpace(bazooka, 0, 0)) {
                    currentState.addDeletedObjects(bazooka);
                    destroyIsland(HoleType.BAZOOKA, bazooka.getForwardX(), bazooka.getYCenter(), currentState);
                    movingObjects.remove(bazooka);
                }

            } else if (movingObj instanceof Prize) {  //prize
                Prize prize = (Prize) movingObj;
                if (worm1.intersectsWith(prize)) {
                    worm1.updateScore(prizeScore);
                    movingObjects.remove(prize);
                } else if (worm2.intersectsWith(prize)) {
                    worm2.updateScore(prizeScore);
                    movingObjects.remove(prize);
                }
            } else if (movingObj instanceof Death) {    //death
                Death death = (Death) movingObj;
                if (worm1.intersectsWith(death)) {
                    worm1.kill(shared.WormState.DEAD);
                    movingObjects.remove(death);
                } else if (worm2.intersectsWith(death)) {
                    worm2.kill(shared.WormState.DEAD);
                    movingObjects.remove(death);
                }
            }
        }


    }

    private void applyAction(Action action, Worm worm, GameState currentState) {
/*        if (action instanceof ShootAction) {
            if (worm.getWeapon() instanceof Shotgun)
                applyShooting(worm, currentState);
            else if (worm.getWeapon() instanceof shared.weapons.Bomb)
                applyExploding(worm, currentState);
        } else if (validJump(action, worm)) {
            worm.setState(new JumpingState());
        } else if (action instanceof MoveRightAction) {
            applyRightmoving(worm);
        } else if (action instanceof MoveLeftAction) {
            applyLeftmoving(worm);
        } else if (action instanceof ChangeWeaponAction) {
            applyChangeWeapon(((ChangeWeaponAction) action).getWeapon(), worm);
        } else if (action instanceof BuildingWallAction) {
            applyBuildingWall(worm, currentState);
        } else if (action instanceof FlyingBobmAction) {
            applyFlyingBombAction(currentState, worm);
        } else if (action instanceof BazookaAction) {
            applyBazookaAction(currentState, worm);
        }*/
        if (action instanceof ShootAction) {
//            System.out.println("shoot");
            if (worm.getWeapon() instanceof Shotgun)
                applyShooting(worm, currentState);
            else if (worm.getWeapon() instanceof shared.weapons.Sheep)
                applyExploding(worm, currentState);
            //////////////camellia
            else if (worm.getWeapon() instanceof shared.weapons.Wall)
                applyBuildingWall(worm, currentState);
            else if (worm.getWeapon() instanceof shared.weapons.Bazoka)
                applyBazookaAction(currentState, worm);
            else if (worm.getWeapon() instanceof shared.weapons.FlyingBomb)
                applyFlyingBombAction(currentState, worm);
        } else if (validJump(action, worm)) {
//            System.out.println("jump");
            worm.setState(new JumpingState(worm.getNumOfLives()));
        } else if (action instanceof MoveRightAction) {
//            System.out.println("right");
            applyRightmoving(worm);
        } else if (action instanceof MoveLeftAction) {
//            System.out.println("left");
            applyLeftmoving(worm);
        } else if (action instanceof ChangeWeaponAction) {
            //    System.out.println("change weapon");
            applyChangeWeapon(((ChangeWeaponAction) action).getWeapon(), worm);
        }

    }


    private void applyFlyingBombAction(GameState currentState, Worm worm) {
        FlyingBomb bomb = worm.createFlyingBomb();
        if (bomb != null)
            currentState.addMoveObject(bomb);
    }

    private void applyBuildingWall(Worm creator, GameState currentState) {
        Wall wall = creator.createWall();
        boolean valid = wall.replaceInValidLocation();
        if (valid) {
            Island.getInstance().buildWall(wall);
            currentState.addWall(wall);
        }
    }

    private void applyChangeWeapon(shared.Weapon weapon, Worm creator) {
        creator.setWeapon(weapon);
    }

    private void applyExploding(Worm worm, GameState currentState) {
        Bomb bomb = getAnUnExplodedBomb(worm, currentState);
        if (bomb != null) {
            killIntersectedEntities(currentState.getWorm1(), currentState.getWorm2(), bomb);
//            destroyIsland(bomb.getXCenter(), bomb.getYCenter(), currentState);
            destroyIsland(HoleType.Bomb, bomb.getXCenter(), bomb.getYCenter(), currentState);
            currentState.deleteMovingObjects(bomb);
            //currentState.addDeletedObjects(bomb);
        } else {
            Bomb newExplodingSheep = worm.createExplodingSheep();
            currentState.addMoveObject(newExplodingSheep);
        }
    }

    private Bomb getAnUnExplodedBomb(Worm creator, GameState currentState) {
        Vector movingObjects = currentState.getMovingObjects();
        for (int i = 0; i < movingObjects.size(); i++) {
            Object o = movingObjects.elementAt(i);
            if (o instanceof Bomb) {
                if (((Bomb) o).getCreator().equals(creator)) {
                    return (Bomb) o;
                }
            }
        }
        return null;
    }

    private void destroyIsland(String holeType, int x, int y, GameState currentState) {

        if (holeType.equals(HoleType.Bomb)) {
            currentState.addIslandHole(new HoleEntity(x, y, new BombHole()));
            Island.getInstance().destroy(x, y, new BombHole());
        } else if (holeType.equals(HoleType.BAZOOKA)) {
//            System.out.println("dakhele destroy island");
            currentState.addIslandHole(new HoleEntity(x, y, new BazookaHole()));

            Island.getInstance().destroy(x, y, new BazookaHole());
        }
    }

    private void killIntersectedEntities(Worm worm1, Worm worm2, Entity entity) {
        if (entity.intersectsWith(worm1)) {
            worm1.kill("DEAD");
            worm2.updateScore(killScore);
        } else if (entity.intersectsWith(worm2)) {
            worm2.kill("DEAD");
            worm1.updateScore(killScore);
        }
    }

    private void applyLeftmoving(Worm worm) {
        if (!worm.isGameOver() && (!(worm.getState() instanceof DrawnState)) && (!(worm.getState() instanceof DeadState))) {
            worm.setDirection(-1);
            worm.move();
        }
    }

    private void applyRightmoving(Worm worm) {
        if (!worm.isGameOver() && (!(worm.getState() instanceof DrawnState)) && (!(worm.getState() instanceof DeadState))) {
            worm.setDirection(1);
            worm.move();
        }
    }

    private boolean validJump(Action action, Worm worm) {
        return (action instanceof JumpAction && (worm.getState() instanceof NormalState));
    }

    private void applyShooting(Worm creator, GameState currentState) {
        Bullet bullet = creator.shoot();
        if (bullet != null)
            currentState.addMoveObject(bullet);
        else {
        }
    }

    public void applyBazookaAction(GameState currentState, Worm worm) {
        //worm.getForwardX()+worm.getWidth() chon jolotar shoroo kone ta hamoon ja ro kharab nakone
        Bazooka bazooka = worm.createBazooka();
        currentState.addMoveObject(bazooka);
    }


    private void updateMovingObjects(GameState currentState, Vector movingObjects) {
        for (int i = 0; i < movingObjects.size(); i++) {
            Object movingObj = movingObjects.elementAt(i);
            if (movingObj instanceof Bullet) {
                Bullet bullet = (Bullet) movingObj;
                boolean willContinue = bullet.moveBullet();
                if (!willContinue)
                    movingObjects.remove(movingObj);
            } else if (movingObj instanceof Bazooka) {
                Bazooka bazooka = (Bazooka) movingObj;
                boolean willContinue = bazooka.move();
                if (!willContinue)
                    movingObjects.remove(movingObj);
            } else if (movingObj instanceof Prize) {
                Prize prize = (Prize) movingObj;
                if (prize.isFinished()) {
                    movingObjects.remove(movingObj);

                } else {
                    prize.moveDown();

                }
            } else if (movingObj instanceof Death) {
                Death death = (Death) movingObj;
                if (death.isFinished()) {
                    movingObjects.remove(movingObj);
                } else {
                    death.moveDown();
                }
            } else if (movingObj instanceof FlyingBomb) {
                FlyingBomb flyingBomb = (FlyingBomb) movingObj;
                if (flyingBomb.isFinished()) {
                    currentState.addDeletedObjects(flyingBomb);
                    killIntersectedEntities(currentState.getWorm1(), currentState.getWorm2(), flyingBomb);
                    //bastegi dare kojaye bazooka bekhaim markaze takhrib bashe
                    destroyIsland(HoleType.Bomb, flyingBomb.getXCenter(), flyingBomb.getYCenter(), currentState);
                    movingObjects.remove(movingObj);
                } else {
                    flyingBomb.move();
                }
            }
        }


    }

    private void updateWorm(Worm worm) {
        if (worm.isGameOver())
            return;
        WormState state = worm.getState();
        if (state instanceof NormalState) {
            worm.moveToIslandSurface();
        } else if (state instanceof FallingState) {
            worm.moveDown();
        } else if (state instanceof JumpingState) {
            worm.jump();
        } else if (state instanceof DeadState || state instanceof DrawnState) {
            worm.reset();
        }
    }

    private void applyEvent(Event event, GameState currentState) {
        if (event instanceof PrizeEvent) {
            for (int i = 0; i < numOfPrizesEachTime; i++)
                currentState.addMoveObject(new Prize(random.nextInt(GUIScales.MAX_X_DOMAIN), 0, System.currentTimeMillis()));

        } else if (event instanceof DeathEvent) {
            for (int i = 0; i < numOfDangersEachTime; i++)
                currentState.addMoveObject(new Death(random.nextInt(GUIScales.MAX_X_DOMAIN), 0, System.currentTimeMillis()));

        } else if (event instanceof SinkEvent) {
            Island.getInstance().sink();
            currentState.setSink();
        }
    }
}