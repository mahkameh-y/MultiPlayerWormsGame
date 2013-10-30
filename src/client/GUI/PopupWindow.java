package client.GUI;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.gui.toolkit.FrameWork;
import com.golden.gamedev.gui.TLabel;
import com.golden.gamedev.gui.TButton;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 26, 2006
 * Time: 6:58:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class PopupWindow extends Game {
    private FrameWork frame;
    public boolean OK = false;
    public boolean CANCEL = false;
    public boolean PRESSED = false;
    public boolean showFPS = true;

    {
        distribute = true;
    }

    public PopupWindow() {
        GameLoader game = new GameLoader();

        game.setup(this, new Dimension(500, 200), false);
        game.start();
    }


    public void initResources() {
        setMaskColor(Color.GREEN);

        // creates the gui frame work
        frame = new FrameWork(bsInput, getWidth(), getHeight());

        this.bsGraphics.setWindowIcon(getImage("../../resources/Ladybug.gif"));
        this.bsGraphics.setWindowTitle("Worms MACT ");
        TLabel label;

        label = new TLabel("ARE YOU SURE YOU WANT TO QUIT?", 20, 20, 400, 20);
        GameFont f = fontManager.getFont(getImage("src\\resources\\images\\bitmapfont.png"));
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        TButton okButton = new TButton("OK", 100, 100, 50, 50) {
            public void doAction() {
                PRESSED = true;
                OK = true;
                notifyExit();
            }
        };
        frame.add(okButton);

        TButton cancelButton = new TButton("CANCEL", 250, 100, 80, 50) {
            public void doAction() {
                PRESSED = true;
                CANCEL = true;
                notifyExit();
            }
        };
        frame.add(cancelButton);

        this.showCursor();
    }

    public void update(long l) {
        frame.update();
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(50, 220, 255));
        g.drawImage(getImage("src\\resources\\images\\popup.png"), 0, 0, null);
        frame.render(g);
    }

    protected void notifyExit() {

    }

    public boolean isOK() {
        return OK;
    }

    public boolean isCANCEL() {
        return CANCEL;
    }

    public boolean isPRESSED() {
        return PRESSED;
    }

}
