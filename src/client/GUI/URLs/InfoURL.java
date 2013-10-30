package client.GUI.URLs;

/**
 * Created by IntelliJ IDEA.
 * User: Camellia khanoom
 * Date: Feb 16, 2006
 * Time: 6:07:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class InfoURL {
    private static String path = "../../resources/";

    public static String getLifeImage(int life) {
        String imageName = String.valueOf(life) + ".gif";
        // System.out.println("imageName = " + imageName);
        return path + "lives/" + imageName;
        //  return "../../resources/lives/000005.bmp";
    }

    public static String getBombNoImage(int numOfBombs) {
        String imageName = String.valueOf(numOfBombs) + ".gif";
        return path + "bomb/" + imageName;
    }

    public static String getBulletNoImage(int numOfBullets) {
        String imageName = String.valueOf(numOfBullets) + ".gif";
        return path + "bullet/" + imageName;
    }

    public static String getEnergyImage(int energy) {
        String imageName = String.valueOf(energy) + ".gif";
        return path + "energy/" + imageName;
    }


}

