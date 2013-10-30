package startupUI.button;

import com.golden.gamedev.gui.TButton;
import com.golden.gamedev.gui.toolkit.GraphicsUtil;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.Sprite;

import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 9:37:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class PicturedButton extends TButton implements Path {
    private GameObject owner;

    // some animation -> we want a nice custom startupUI.button right :)
    private Sprite object;

    private GameFont font;
    private BufferedImage bigImg;
    private BufferedImage img;

    public PicturedButton(GameObject owner, String text, String imageURL, int x, int y, int w, int h) {
        super(text, x, y, w, h);
        this.owner = owner;

        // turn on custom rendering flag
        // in this way, the component is fully responsible of its own rendering
        customRendering = true;

        // font for this startupUI.button
        font = owner.fontManager.getFont(owner.getImage(Path.filePath + "bitmapfont.png"));

        img = owner.getImage(Path.filePath + imageURL);
        object = new Sprite(img, 0, 0);
        bigImg = ImageUtil.resize(img,
                (int) ((double) img.getWidth() * 1.1),
                (int) ((double) img.getHeight() * 1.1));


    }

    public void update() {
        object.update(10);

        if (isMousePressed()) {
            object.setImage(bigImg);
        } else {
            object.setImage(img);
        }
    }

    protected void processMousePressed() {
        super.processMousePressed();

        if (bsInput.getMousePressed() == MouseEvent.BUTTON1) {
            // play sound every time the mouse clicking this startupUI.button
            owner.playSound(Path.filePath + "click.wav");
        }
    }

    // creates the background ui
    protected void createCustomUI(int w, int h) {
        // creates 2 ui images -> for normal and pressed state
        ui = GraphicsUtil.createImage(2, w, h, Transparency.BITMASK);

        // draw the startupUI.button, hexagonal shape???
        GeneralPath path = new GeneralPath();
        path.moveTo(0, h / 2);
        path.lineTo((w / 2) - (w / 4), 0);
        path.lineTo((w / 2) + (w / 4), 0);
        path.lineTo(w, h / 2);
        path.lineTo((w / 2) + (w / 4), h);
        path.lineTo((w / 2) - (w / 4), h);
        path.closePath();

        
    }

    // processing the ui -> drawing text and other accessory
    protected void processCustomUI() {
        for (int i = 0; i < ui.length; i++) {
            Graphics2D g = ui[i].createGraphics();

            // draw the startupUI.button text exactly at the bottom of the object
            font.drawString(g, getText(), GameFont.CENTER,
                    0, (getHeight() / 3) - (object.getHeight() / 2) + (bigImg.getHeight()) + 2, getWidth());
            /*font.drawString(g, getText(), GameFont.CENTER,
         0, 0 , 0);     */

            g.dispose();
        }
    }

    // render the ui to screen
    protected void renderCustomUI(Graphics2D g, int x, int y, int w, int h) {
        g.drawImage((isMousePressed()) ? ui[1] : ui[0], x, y, null);

        // mouse over the startupUI.button, draw a BIG object
        if (!isMousePressed() && isMouseOver()) {
            g.drawImage(bigImg,
                    x + (w / 2) - (bigImg.getWidth() / 2),
                    y + (h / 3) - (object.getHeight() / 2),
                    null);

        } else {
            // render normal object
            object.render(g, x + (w / 2) - (object.getWidth() / 2),
                    y + (h / 3) - (object.getHeight() / 2));
        }
    }

    // since the startupUI.button is not rectangular, we use pixel scanning here
    public boolean intersects(int x, int y) {
        if (super.intersects(x, y)) {
            // pixel checking
            boolean b = false;

            try {
                b = (ui[0].getRGB(x - getScreenX(), y - getScreenY()) != 0);
            } catch (Exception e) {
                // bugs or not? :
                // width = 50, height = 50
                // getRGB(50, 50) throws out of bounds exception
                b = (ui[0].getRGB(x - getScreenX() - 1, y - getScreenY() - 1) != 0);
            }

            return b;

        } else return false;
    }

}
