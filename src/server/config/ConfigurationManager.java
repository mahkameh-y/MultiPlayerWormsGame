package server.config;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Feb 7, 2006
 * Time: 10:34:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationManager implements ObjectNames {
    private static byte[][] islandRects;

    public static void config(int mapNumber) {
        loadIsland(mapNumber);
        loadWorm();
        loadTree();
        loadBullet();
    }


    public static void loadIsland(int mapNumber) {

        islandRects = new byte[200][50];
        try {
            RandomAccessFile reader = new RandomAccessFile("src\\server\\config\\island.txt", "rw");
            String line;
            StringTokenizer tokenizer;
            for (int i = 0; i < 200; i++) {
                line = reader.readLine();
                tokenizer = new StringTokenizer(line, ",");
                for (int k = 0; k < 18; k++) {
//                for (int k = 0; k < 23; k++) {
                    islandRects[i][k] = 0;
                }
//                for (int j = 23; j < 50; j++) {
                for (int j = 18; j < 48; j++) {
                    String token = tokenizer.nextToken();
                    char cTemp = token.charAt(1);
                    islandRects[i][j] = (byte) (cTemp - 48);
                }
                for (int l=48;l<50;l++){
                    islandRects[i][l]=1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

/*
        for (int k=35 ; k<50 ; k++){
            System.out.println("k = " + k);
            System.out.println("islandRects = " + islandRects[10][k]);
        }
*/

    }


    public static void loadWorm() {

    }

    public static void loadTree() {

    }

    public static void loadBullet() {

    }

    public static byte[][] getIslandRects() {
        return islandRects;
    }


}
