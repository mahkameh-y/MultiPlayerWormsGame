package shared.entity;

import shared.Weapon;

/**
 * Created by IntelliJ IDEA.
 * User: C.ghoroghi
 * Date: Feb 1, 2006
 * Time: 9:05:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class WormEntity extends IslandEntity {
    shared.Weapon weapon;
    private String state;
    private int direction; // 1 = right -1 = left
    private boolean gameOver;
    private int score;
    private int life;
    private int numOfBullets;
    private int numOfBombs;

    //camellia
    private int numOfSheeps;
    private int numOfBazookaBullet;




    public WormEntity(int x, int y, String state, int direction, boolean gameOver,int score , int life,  int numOfBullets, int numOfFlyingBombs, int numOfExplodingSheep, int numOfBazooka ,shared.Weapon weapon) {
        super(x, y);
        this.state = state;
        this.direction = direction;
        this.gameOver = gameOver;
        this.life = life;
        this.numOfBullets = numOfBullets;
        this.numOfBombs = numOfFlyingBombs;
        this.numOfSheeps = numOfExplodingSheep;
        this.numOfBazookaBullet = numOfBazooka;
        this.weapon = weapon;
        this.score = score;
    }
    //**camellia
    public WormEntity(int x, int y, shared.Weapon weapon, String state, int direction, boolean gameOver, int score, int life, int energy, int numOfBullets, int numOfBombs, int numOfSheeps, int numOfWalls, int numOfBazookaBullet) {
        super(x, y);
        this.weapon = weapon;
        this.state = state;
        this.direction = direction;
        this.gameOver = gameOver;
        this.score = score;
        this.life = life;
        this.numOfBullets = numOfBullets;
        this.numOfBombs = numOfBombs;
        this.numOfSheeps = numOfSheeps;
        this.numOfBazookaBullet = numOfBazookaBullet;
    }

    public int getNumOfSheeps() {
        return numOfSheeps;
    }

    public void setNumOfSheeps(int numOfSheeps) {
        this.numOfSheeps = numOfSheeps;
    }


    public int getNumOfBazookaBullet() {
        return numOfBazookaBullet;
    }

    public void setNumOfBazookaBullet(int numOfBazookaBullet) {
        this.numOfBazookaBullet = numOfBazookaBullet;
    }
    //**camellia

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getDirection() {
        return direction;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getNumOfBullets() {
        return numOfBullets;
    }

    public void setNumOfBullets(int numOfBullets) {
        this.numOfBullets = numOfBullets;
    }

    public int getNumOfBombs() {
        return numOfBombs;
    }

    public void setNumOfBombs(int numOfBombs) {
        this.numOfBombs = numOfBombs;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(shared.Weapon weapon) {
        this.weapon = weapon;
    }
}
