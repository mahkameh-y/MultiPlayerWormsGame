package server.actions;

import server.networkManager.PlayerType;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: a.moallem
 * Date: Jan 27, 2006
 * Time: 11:52:21 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class Action implements Serializable{
    protected byte player;

    public boolean isPlayer1() {
        return (player == PlayerType.worm1);
    }

    public boolean isPlayer2() {
        return (player == PlayerType.worm2);
    }

    public void setPlayer(byte player) {
        this.player = player;
    }
}
