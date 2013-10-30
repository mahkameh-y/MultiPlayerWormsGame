package shared;

import shared.WormState;

import java.io.Serializable;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: soft
 * Date: Mar 7, 2006
 * Time: 4:31:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Weapon implements Serializable {
    public Dimension getSuitablePlace(int direction,int xWorm, int yWorm, String state)  {
        int x = xWorm;
        int y = yWorm + 37;
        if (direction == 1) {
            if (state.equals(WormState.JUMP))
                x = x + 6;
            else
                x = x + 26;
        }
        return new Dimension(x,y);
    }
}
