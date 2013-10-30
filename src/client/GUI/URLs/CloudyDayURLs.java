package client.GUI.URLs;

import client.GUI.ObjectNames;
import shared.WormState;
import shared.entity.holes.BombHole;
import shared.entity.holes.BazookaHole;
import shared.entity.holes.HoleType;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 28, 2006
 * Time: 10:24:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class CloudyDayURLs extends URL implements ObjectNames, WormState, SharedURLs {


    public CloudyDayURLs() {
    }

    private static String PATH = "../../resources/";

    public static String BEE_URL = "YJacket.gif";
    public static String BACKGROUND_URL = "sky.gif";


    public static String SEA_URL = "SEA.gif";
    public static String BOTTLE_URL = "bottle.gif";

    public static String FROG_URL = "frog.gif";
    public static String DIVER_URL = "aqua_sheep.png";

    public static String SKELTON_URL = "SKELTON1.gif";
    public static String DANGER_URL = "DANGER.gif";

    public static String AQUA1_URL = "donkey.png";
    public static String AQUA2_URL = "donkey.png";

    public static String CLOUD1_URL = "cloud1.gif";
    public static String CLOUD2_URL = "cloud2.gif";

    public static String HOLE_BOMB_URL = "hole/bomb.gif";
    public static String HOLE_TUNNEL_URL = "hole/tunnel.gif";
    public static String HOLE_WELL_URL = "hole/wall.gif";

    public static String DEATH_URL = "DANGER.gif";

    public static String GIFT_URL = "prize.png";
    public static String MOON_URL = "moon.gif";
    public static String STAR_URL = "star.gif";


    public String getURL(String objName) {
        if (objName.equals(BEE)) {
            return PATH + BEE_URL;
        } else if (objName.equals(SHEEP)) {
            return PATH + SHEEP_URL;
        } else if (objName.equals(LADYBUG)) {
            return PATH + LADYBUG_URL;
        } else if (objName.equals(BACKGROUND)) {
            return PATH + "cloudy/" + BACKGROUND_URL;
        } else if (objName.equals(GROUND)) {
            return PATH + GROUND_URL;
        } else if (objName.equals(SEA)) {
            return PATH + SEA_URL;
        } else if (objName.equals(BOTTLE)) {
            return PATH + BOTTLE_URL;
        } else if (objName.equals(AQUA1)) {
            return PATH + AQUA1_URL;
        } else if (objName.equals(AQUA2)) {
            return PATH + AQUA2_URL;
        } else if (objName.equals(FROG)) {
            return PATH + "cloudy/" + FROG_URL;
        } else if (objName.equals(DIVER)) {
            return PATH + DIVER_URL;
        } else if (objName.equals(CLOUD1)) {
            return PATH + "cloudy/" + CLOUD1_URL;
        } else if (objName.equals(CLOUD2)) {
            return PATH + "cloudy/" + CLOUD2_URL;
        } else if (objName.equals(BIRD)) {
            return PATH + BIRD_URL;
        } else if (objName.equals(EXPLOSION)) {
            return PATH + EXPLOSION_URL;
        } else if (objName.equals(GAMEOVER)) {
            return PATH + GAMEOVER_URL;
        } else if (objName.equals(WIN)) {
            return PATH + WIN_URL;
        } else if (objName.equals(SKELTON)) {
            return PATH + SKELTON_URL;
        } else if (objName.equals(DANGER)) {
            return PATH + "cloudy/" + DANGER_URL;
        } else if (objName.equals(GUN)) {
            return PATH + GUN_URL;
        } else if (objName.equals(LFBOMB)) {
            return PATH + LITTLE_FBOMB_URL;
        } else if (objName.equals(GIFT)) {
            return PATH + "cloudy/" + GIFT_URL;
        } else if (objName.equals(DEATH)) {
            return PATH + "cloudy/" + DEATH_URL;
        } else if (objName.equals(WALL)) {
            return PATH + WALL_URL;
        } else if (objName.equals(FLYINGBOMB)) {
            return PATH + FLYINGBOMB_URL;
        } else if (objName.equals(SSHEEP)) {
            return PATH + LITTLE_SHEEP_URL;
        } else if (objName.equals(SWALL)) {
            return PATH + LITTLE_WALL_URL;
        } else if (objName.equals(MOON)) {
            return PATH + "cloudy/" + MOON_URL;
        } else if (objName.equals(STAR)) {
            return PATH + "cloudy/" + STAR_URL;
        }
//        System.out.println("vaghti nulle objName = " + objName);
        return null;
    }

    public String getWormURL(String state, int direction, int numOfPlayer) {
        if (state.equals(NORMAL) && direction == -1)
            return PATH + WORM_PATH + numOfPlayer + "/" + WORM_LEFT_URL;
        else if (state.equals(NORMAL) && direction == 1)
            return PATH + WORM_PATH + numOfPlayer + "/" + WORM_RIGHT_URL;
        else if (state.equals(JUMP) && direction == -1)
            return PATH + WORM_PATH + numOfPlayer + "/" + WORM_LEFT_JUMP_URL;
        else if (state.equals(JUMP) && direction == 1)
            return PATH + WORM_PATH + numOfPlayer + "/" + WORM_RIGHT_JUMP_URL;
        else if (state.equals(DEAD) && direction == -1)
            return PATH + WORM_PATH + numOfPlayer + "/" + WORM_DEAD_LEFT_URL;
        else if (state.equals(DEAD) && direction == 1)
            return PATH + WORM_PATH + numOfPlayer + "/" + WORM_DEAD_RIGHT_URL;
        else if (state.equals(DRAWN))
            return PATH + WORM_PATH + numOfPlayer + "/" + WORM_DRAWN_URL;
        return PATH + WORM_PATH + numOfPlayer + "/" + WORM_LEFT_URL;
    }

    public String getBulletURL(int direction) {
        if (direction == -1)
            return PATH + BULLET_LEFT_URL;
        return PATH + BULLET_RIGHT_URL;
    }

    public String getGunURL(int direction) {
        if (direction == -1)
            return PATH + GUN_LEFT_URL;
        return PATH + GUN_RIGHT_URL;
    }

    public String getBazokaURL(int direction) {
        if (direction == -1)
            return PATH + BAZOKA_LEFT_URL;
        return PATH + BAZOKA_RIGHT_URL;
    }

    ///camellia
    public String getBazookaBulletURL(int direction) {
        if (direction == 1)
            return PATH + BAZOOKA_BULLET_RIGHT_URL;
        return PATH + BAZOOKA_BULLET_LEFT_URL;
    }
    //camellia

    public String getHoleURL(HoleType holeType) {
        if (holeType instanceof BombHole)
            return PATH + "cloudy/" + HOLE_BOMB_URL;
        else if (holeType instanceof BazookaHole)
            return PATH + "cloudy/" + HOLE_TUNNEL_URL;
/*
        else if (holeType instanceof BazookaHole)
            return HOLE_WELL_URL;
*/
        return null;
    }

    public static String getLifeImage(int life) {
        String imageName = String.valueOf(life) + ".gif";
        return PATH + "lives/" + imageName;
    }

    public static String getBombNoImage() {
        return PATH + "info/bomb.gif";
    }

    public static String getWallImage() {
        return PATH + "info/wall.gif";
    }

    public static String getBulletNoImage() {
        return PATH + "info/gun.gif";
    }

    public static String getBazookaBulletNoImage() {
        return PATH + "info/bazooka.gif";
    }

    public static String getSheepNoImage() {
        return PATH + "info/sheep.gif";
    }

    public String getLifeNoURL(int life) {
        String imageName = String.valueOf(life) + ".gif";
        return PATH + "lives/" + imageName;
    }

    public String getBombNoURL() {
        return PATH + "info/bomb.gif";
    }

    public String getWallNoURL() {
        return PATH + "info/wall.gif";
    }

    public String getBulletNoURL() {
        return PATH + "info/gun.gif";
    }

    public String getBazookaBulletNoURL() {
        return PATH + "info/bazooka.gif";
    }

    public String getSheepNoURL() {
        return PATH + "info/sheep.gif";
    }
}
