package shared.entity;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Camellia khanoom
 * Date: Feb 1, 2006
 * Time: 8:06:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class IslandEntity implements Serializable {
    private int x;
    private int y;

    public IslandEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
