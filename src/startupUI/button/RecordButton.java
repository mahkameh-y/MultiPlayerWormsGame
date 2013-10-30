package startupUI.button;

import com.golden.gamedev.GameObject;
import startupUI.pages.Engine;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 8:08:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class RecordButton extends AnimatedButton {
    private GameObject owner;
    private final static int x = 300;
    private final static int y = 100;
    private final static int w = 170;
    private final static int h = 100;

    public RecordButton(GameObject owner, String text) {
        super(owner, text, x, y, w, h);
        this.owner = owner;
        setToolTipText("See the recent records for this game");
    }

    public void doAction() {
        owner.parent.nextGameID = Engine.RECORDS;
        owner.finish();
    }
}
