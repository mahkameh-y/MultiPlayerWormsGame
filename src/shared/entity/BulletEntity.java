package shared.entity;

/**
 * Created by IntelliJ IDEA.
 * User: Camellia khanoom
 * Date: Feb 14, 2006
 * Time: 10:18:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class BulletEntity extends IslandEntity {
    int direction = 1;

    public BulletEntity(int x, int y) {
        super(x, y);
    }

    public BulletEntity(int x, int y, int direction) {
        super(x, y);
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
