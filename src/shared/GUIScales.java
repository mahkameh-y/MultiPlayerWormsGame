package shared;

import server.entity.Island;

/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Jan 31, 2006
 * Time: 5:36:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GUIScales {
    final public static int wormFallSpeed = 20;
    final public static int bulletSpeed = 10;
    final public static int islandInitialY = 80;
    final public static int eachSinkingScale = 10;
    final public static int gameYDimension = 680;
    final public static int gameXDimension = 940;
    final public int MAX_UPWARD_ABILITY = 3 * Island.getInstance().getY_LENGTH_EACH_RECTANGLE();
    final public int MAX_X_DOMAIN = 2000;

}
