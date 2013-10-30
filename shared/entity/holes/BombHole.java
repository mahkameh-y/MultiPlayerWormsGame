package shared.entity.holes;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 17, 2006
 * Time: 2:42:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class BombHole implements HoleType{

    private int radius = 45;
    public int getRadius() {
        return radius;
    }

    public int getWidth() {
        return 2*radius;
    }

    public int getHeight() {
        return 2*radius;
    }

}
