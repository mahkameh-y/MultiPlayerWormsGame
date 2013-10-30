package server.state;

import shared.*;

/**
 * Created by IntelliJ IDEA.
 * User: soft
 * Date: Mar 16, 2006
 * Time: 6:13:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class FlyingBombJumpingState implements FlyingBombState {
    private int jumpingYSpeed = GUIScales.wormFallSpeed / 2;
    private int jumpingXSpeed = GUIScales.wormFallSpeed / 4;

    public int getJumpingYSpeed() {
        return jumpingYSpeed;
    }

    public int getJumpingXSpeed() {
        return jumpingXSpeed;
    }

    public void setJumpingYSpeed(int jumpingYSpeed) {
        this.jumpingYSpeed = jumpingYSpeed;
    }
}
