package startupUI.button;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.gui.toolkit.GraphicsUtil;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 11:33:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class RectanglePicturedButton extends PicturedButton {
    private int x;
    private int y;
    private int w;
    private int h;

    public RectanglePicturedButton(GameObject owner, String text, String imageURL, int x, int y, int w, int h) {
        super(owner, text, imageURL, x, y, w, h);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

    }

    protected void createCustomUI(int w, int h) {
        // creates 2 ui images -> for normal and pressed state
        ui = GraphicsUtil.createImage(2, w, h, Transparency.BITMASK);

        GeneralPath path = new GeneralPath();
        path.moveTo(0, 0);
        path.lineTo(0, h);
        path.lineTo(w, h);
        path.lineTo(w, 0);
        path.closePath();

        
    }

    public boolean intersects(int x, int y) {
        if ( (this.x <= x && x <= this.x + this.w) &&
                (this.y <= y && y <= this.y + this.h))
            return true;
        return false;
    }
}
