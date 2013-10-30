package shared;

import java.util.Vector;
import java.io.Serializable;

import shared.entity.IslandEntity;

/**
 * Created by IntelliJ IDEA.
 * User: Camellia khanoom
 * Date: Feb 1, 2006
 * Time: 8:34:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Configuration implements Serializable {
    String envirment;
    private Vector entities = new Vector(); // az islandEntity

    public Vector getEntities() {
        return entities;
    }

    public void setEntities(Vector entities) {
        this.entities = entities;
    }

    public Configuration() {
    }

    public Configuration(Vector staticEntities, String enviroment) {
        this.entities = staticEntities;
        this.envirment = enviroment;
    }


    public Vector getStaticEntities() {
        //return staticEntities;
        return null;
    }

    public void setStaticEntities(Vector staticEntities) {
        //this.staticEntities = staticEntities;
    }

    public String getEnvirment() {
        return envirment;
    }

    public void setEnvirment(String envirment) {
        this.envirment = envirment;
    }

    public void addEntity(IslandEntity entity) {
        this.entities.add(entity);
    }
}
