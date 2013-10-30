package shared.entity;

import shared.entity.holes.HoleType;

/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Jan 31, 2006
 * Time: 3:01:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class HoleEntity extends IslandEntity {

    private HoleType type;

    public HoleEntity(int x, int y, HoleType holeType) {
        super(x-holeType.getWidth()/2, y-holeType.getHeight()/2);
/*
        System.out.println("dakhele hole ba in x,y");
        System.out.println("x = " + x);
        System.out.println("y = " + y);
*/
        this.type = holeType;
    }
    public HoleType getType() {
        return type;
    }

    public void setType(HoleType type) {
        this.type = type;
    }
}
