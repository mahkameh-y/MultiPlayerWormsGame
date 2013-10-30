package startupUI.button;

import com.golden.gamedev.GameObject;
import startupUI.pages.Engine;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 8:07:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateButton extends AnimatedButton {
    private GameObject owner;
    private final static int x = 150;
    private final static int y = 0;
    private final static int w = 150;
    private final static int h = 100;

    public CreateButton(GameObject owner, String text) {
        super(owner, text, x, y, w, h);
        setToolTipText("Create a new board. Either wait \n" +
                "for a competitor to show up, or \n" +
                "select computer as your enemy!");
        this.owner = owner;
    }

    public void doAction() {
        owner.parent.nextGameID = Engine.CREATE;
        owner.finish();
    }
}
