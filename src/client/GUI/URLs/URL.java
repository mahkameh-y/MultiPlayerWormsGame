package client.GUI.URLs;

import shared.entity.holes.HoleType;

/**
 * Created by IntelliJ IDEA.
 * User: Camellia khanoom
 * Date: Feb 3, 2006
 * Time: 10:49:59 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class URL {

    public abstract String getURL(String objName);

    public abstract String getWormURL(String state, int direction, int numOfPlayer);

    public abstract String getBulletURL(int direction);

    public abstract String getGunURL(int direction);

    public abstract String getHoleURL(HoleType holeType);

    public abstract String getBazokaURL(int direction);

    //camellia
    public abstract String getBazookaBulletURL(int direction);
    //camellia

    public abstract String getBombNoURL();

    public abstract String getWallNoURL();

    public abstract String getBulletNoURL();

    public abstract String getBazookaBulletNoURL();

    public abstract String getSheepNoURL();

    public abstract String getLifeNoURL(int life);
}
