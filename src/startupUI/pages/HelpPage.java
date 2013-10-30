package startupUI.pages;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.gui.toolkit.FrameWork;
import com.golden.gamedev.gui.TButton;
import com.golden.gamedev.gui.TLabel;
import startupUI.button.Path;
import startupUI.button.PicturedButton;

import java.util.Vector;
import java.awt.*;

import networkManager.TCPManagerClient;
import shared.GameResult;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 10, 2006
 * Time: 6:49:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class HelpPage extends GameObject implements Path {
    private FrameWork frame;
    private final static String right = "RIGHT ARROW KEY";
    private final static String left = "LEFT ARROW KEY";
    private final static String fire = "CTRL";
    private final static String escape = "ESC";
    private final static String jump = "SPACE";
    private final static String changeWeapon = "ALT";


    public HelpPage(GameEngine gameEngine) {
        super(gameEngine);
    }

    public void initResources() {
        setMaskColor(Color.GREEN);

        // creates the gui frame work
        frame = new FrameWork(bsInput, getWidth(), getHeight());

        this.bsGraphics.setWindowIcon(getImage("src/resources/Ladybug.gif"));
        this.bsGraphics.setWindowTitle("Worms MACT ");
        TLabel label;

        label = new TLabel("IN ORDER TO:", 20, 270, 200, 20);
        GameFont f = fontManager.getFont(getImage(filePath + "bitmapfont.png"));
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel("MOVE RIGHT", 70, 300, 200, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel("PRESS", 350, 300, 100, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel(right, 550, 300, 300, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        /******************left*****/
        label = new TLabel("MOVE LEFT", 70, 330, 200, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel("PRESS", 350, 330, 100, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel(left, 550, 330, 300, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        /*****************jump*****/
        label = new TLabel("JUMP", 70, 360, 200, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel("PRESS", 350, 360, 100, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel(jump, 550, 360, 300, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        /********************shoot**********************/
        label = new TLabel("SHOOT", 70, 390, 200, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel("PRESS", 350, 390, 100, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel(fire, 550, 390, 300, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        /********************wall***************************/
        label = new TLabel("EXIT GAME", 70, 420, 200, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel("PRESS", 350, 420, 100, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel(escape, 550, 420, 300, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        /*****************change weapon********/
        label = new TLabel("CHANGE WEAPON", 70, 450, 200, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel("PRESS", 350, 450, 100, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        label = new TLabel(changeWeapon, 550, 450, 300, 20);
        label.UIResource().put("Text Font", f);
        label.UIResource().put("Text Over Font", f);
        label.UIResource().put("Text Pressed Font", f);
        frame.add(label);

        PicturedButton cancelButton = new PicturedButton(this, "CANCEL", "cancel.png", 350, 590, 150, 100) {
            public void doAction() {
                parent.nextGameID = Engine.WELCOME;
                finish();
            }
        };
        frame.add(cancelButton);

        this.showCursor();
    }

    public void update(long l) {
        frame.update();
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(150, 120, 255));
        g.drawImage(getImage(filePath + "help.png"), 0, 0, null);
        frame.render(g);
    }
}
