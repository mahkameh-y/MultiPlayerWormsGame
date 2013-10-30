package startupUI.button;

import com.golden.gamedev.GameObject;
import startupUI.pages.Engine;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 8:08:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class HelpButton extends AnimatedButton {
    private GameObject owner;
    private final static int x = 150;
    private final static int y = 200;
    private final static int w = 150;
    private final static int h = 100;

    public HelpButton(GameObject owner, String text) {
        super(owner, text, x, y, w, h);
        this.owner = owner;
        setToolTipText("Click if you need help about the game");
    }

    public void doAction() {
        owner.parent.nextGameID = Engine.HELP;
        owner.finish();
    }
}
