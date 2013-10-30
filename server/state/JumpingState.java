package server.state;

import shared.GUIScales;
import shared.WormState;
import server.entity.Worm;

/**
 * Created by IntelliJ IDEA.
 * User: tannaz
 * Date: Feb 3, 2006
 * Time: 11:18:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class JumpingState implements server.state.WormState {

    private int jumpingYSpeed;
    private int jumpingXSpeed = /*GUIScales.wormFallSpeed / 20*/0;


    public JumpingState(int wormLives) {
        if (wormLives == 5)
            jumpingYSpeed = 25;
        else if (wormLives == 4)
            jumpingYSpeed = 22;
        else if (wormLives == 3)
            jumpingYSpeed = 19;
        else if (wormLives == 2)
            jumpingYSpeed = 16;
        else if (wormLives == 1)
            jumpingYSpeed = 13;
    }

    public String toString() {
        return WormState.JUMP;
    }

    public int getJumpingYSpeed() {
        return jumpingYSpeed;
    }

    public int getJumpingXSpeed() {
        return jumpingXSpeed;
    }

    public void setJumpingYSpeed( int jumpingYSpeed) {
        this.jumpingYSpeed = jumpingYSpeed;

    }
}
