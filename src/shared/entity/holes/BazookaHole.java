package shared.entity.holes;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 17, 2006
 * Time: 2:45:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class BazookaHole implements HoleType{
    //tananz2
    private int height = 70;
    private int width = 60;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
}
