package shared.entity;

/**
 * Created by IntelliJ IDEA.
 * User: a.moallem
 * Date: Mar 17, 2006
 * Time: 11:27:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class BazookaEntity extends IslandEntity{
    int direction = 1;
    public BazookaEntity(int x, int y,int direction) {
        super(x, y);
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}//azin
