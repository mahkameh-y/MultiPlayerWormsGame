package shared.entity.holes;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 17, 2006
 * Time: 2:39:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HoleType extends Serializable{
    public final String BAZOOKA="BAZOOKA";
    public final String Bomb="Bomb";
    public int getWidth();
    public int getHeight();

}
