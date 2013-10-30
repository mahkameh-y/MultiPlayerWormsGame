package client.GUI.config;


import shared.Configuration;
import shared.entity.*;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.StringTokenizer;

import client.GUI.ObjectNames;

/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Feb 7, 2006
 * Time: 10:34:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationManager implements ObjectNames {
    private static Configuration configuration = new Configuration();

    public static void loadMap(int mapNumber) {
        try {
            RandomAccessFile mapReader;
            String line, objectName, posX, posY, temp1, temp2;
            StringTokenizer commaTokenizer, dashTokenizer;

            mapReader = new RandomAccessFile("src\\client\\GUI\\config\\map" + mapNumber + ".txt", "rw");
            configuration.setEnvirment(mapReader.readLine());
            line = mapReader.readLine();
            while (line != null) {
                objectName = line.substring(0, line.indexOf(':'));
                temp1 = line.substring(line.indexOf(':') + 1);
                commaTokenizer = new StringTokenizer(temp1, ",");
                while (commaTokenizer.hasMoreTokens()) {
                    temp2 = commaTokenizer.nextToken();
                    dashTokenizer = new StringTokenizer(temp2, "-");
                    posX = dashTokenizer.nextToken();
                    posY = dashTokenizer.nextToken();
                    createObject(objectName, posX, posY);
                }
                line = mapReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createObject(String objectName, String posX, String posY) {
        int x = (Integer.valueOf(posX)).intValue();
        int y = (Integer.valueOf(posY)).intValue();

        IslandEntity entity = null;
        if (objectName.equals(BOTTLE)) {
            entity = new BottleEntity(x, y);
        } else if (objectName.equals(AQUA1)) {
            entity = new Aqua1Entity(x, y);
        } else if (objectName.equals(AQUA2)) {
            entity = new Aqua2Entity(x, y);
        } else if (objectName.equals(FROG)) {
            entity = new FrogEntity(x, y);
        } else if (objectName.equals(DIVER)) {
            entity = new DiverEntity(x, y);
        } else if (objectName.equals(CLOUD1)) {
            entity = new Cloud1Entity(x, y);
        } else if (objectName.equals(CLOUD2)) {
            entity = new Cloud2Entity(x, y);
        } else if (objectName.equals(BIRD)) {
            entity = new BirdEntity(x, y);
        } else if (objectName.equals(SKELTON)) {
            entity = new SkeltonEntity(x, y);
        } else if (objectName.equals(DANGER)) {
            entity = new DangerEntity(x, y);
        }else if (objectName.equals(SUN)) {
            entity = new SunEntity(x, y);
        }else if (objectName.equals(STAR)) {
            entity = new StarEntity(x,y);
        }else if (objectName.equals(MOON)) {
            entity = new MoonEntity(x, y);
        }
        configuration.addEntity(entity);
    }

    public static Configuration getConfiguration() {
        return configuration;
    }
}
