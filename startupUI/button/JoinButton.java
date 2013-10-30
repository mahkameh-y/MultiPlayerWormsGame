package startupUI.button;

import com.golden.gamedev.GameObject;
import startupUI.pages.Engine;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 8:07:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class JoinButton extends AnimatedButton {
    private GameObject owner;
    private final static int x = 0;
    private final static int y = 100;
    private final static int w = 150;
    private final static int h = 100;

    public JoinButton(GameObject owner, String text) {
        super(owner, text, x, y, w, h);
        setToolTipText("Join an existing game. \n" +
                "You must select from a list of games \n" +
                "currently waiting for another competitor.");
        this.owner = owner;
    }

    public void doAction() {
        owner.parent.nextGameID = Engine.JOIN;
        owner.finish();
    }
}
