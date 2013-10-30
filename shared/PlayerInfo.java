package shared;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: soft
 * Date: Mar 7, 2006
 * Time: 2:33:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerInfo implements Serializable {
    private int playerNumber;

    public PlayerInfo(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
