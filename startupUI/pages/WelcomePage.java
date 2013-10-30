package startupUI.pages;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.gui.toolkit.FrameWork;

import java.awt.*;

import startupUI.button.*;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 7:13:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class WelcomePage extends GameObject implements Path {
    private FrameWork frame;

    public WelcomePage(GameEngine gameEngine) {
        super(gameEngine);
    }

    public void initResources() {
        setMaskColor(Color.GREEN);

        // creates the gui frame work
        frame = new FrameWork(bsInput, getWidth(), getHeight());

        this.bsGraphics.setWindowIcon(getImage("../../resources/Ladybug.gif"));
        this.bsGraphics.setWindowTitle("Worms MACT ");

        //create startupUI.button
        CreateButton createBtn = new CreateButton(this, "CREATE");

        //select startupUI.button
        JoinButton joinBtn = new JoinButton(this, "JOIN");

        //records startupUI.button
        RecordButton recordBtn = new RecordButton(this, "RECORDS");

        // help
        HelpButton helpBtn = new HelpButton(this, "HELP");

        frame.add(createBtn);
        frame.add(joinBtn);
        frame.add(recordBtn);
        frame.add(helpBtn);

        this.showCursor();
    }

    public void update(long l) {
        frame.update();
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(255, 128, 64));
        g.drawImage(getImage(filePath + "backgnd.png"), 0, 0, null);
        frame.render(g);
    }


}
