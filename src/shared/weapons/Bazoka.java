package shared.weapons;

import shared.*;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Camellia
 * Date: Mar 14, 2006
 * Time: 6:48:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bazoka extends shared.Weapon {
    //----camellia
    public Dimension getSuitablePlace(int direction, int xWorm, int yWorm, String state) {
        int x = xWorm;
        int y = yWorm + 42;
        if (direction == 1) {
            x = x + 20;
        } else {
            x -= 10;
        }
        return new Dimension(x, y);
    }
    //----camellia
}
