package startupUI.pages;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.gui.toolkit.FrameWork;
import com.golden.gamedev.gui.TButton;
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
 * Time: 6:33:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class RecordPage extends GameObject implements Path {
    private FrameWork frame;
    private Vector records = new Vector();

    public RecordPage(GameEngine gameEngine) {
        super(gameEngine);
    }

    public void initResources() {

        TCPManagerClient.sendToGameServer("RECORDS");
        records = TCPManagerClient.getRecordsInfo();

        setMaskColor(Color.GREEN);

        // creates the gui frame work
        frame = new FrameWork(bsInput, getWidth(), getHeight());

        this.bsGraphics.setWindowIcon(getImage("src/resources/Ladybug.gif"));
        this.bsGraphics.setWindowTitle("Worms MACT ");

        PicturedButton cancelButton = new PicturedButton(this, "CANCEL", "cancel.png", 350, 500, 150, 100) {
            public void doAction() {
                parent.nextGameID = Engine.WELCOME;
                finish();
            }
        };
        frame.add(cancelButton);



        TButton nameTitleButton = new TButton("NAME", 150, 300, 100, 20);
        TButton worm1TitleButton = new TButton("WORM1", 250, 300, 100, 20);
        TButton worm2TitleButton = new TButton("WORM2", 350, 300, 150, 20);
        nameTitleButton.setEnabled(false);
        worm1TitleButton.setEnabled(false);
        worm2TitleButton.setEnabled(false);

        frame.add(nameTitleButton);
        frame.add(worm1TitleButton);
        frame.add(worm2TitleButton);
        for (int i = 0; i < records.size(); i++) {
            TButton nameButton = new TButton(((GameResult) records.elementAt(i)).getGameName(), 150, 320 + 20 * i, 100, 20);
            TButton worm1Button = new TButton(String.valueOf(((GameResult) records.elementAt(i)).getWorm1Score()), 250, 320 + 20 * i, 100, 20);
            TButton worm2Button = new TButton(String.valueOf(((GameResult) records.elementAt(i)).getWorm2Score()), 350, 320 + 20 * i, 150, 20);
            nameButton.setEnabled(false);
            worm1Button.setEnabled(false);
            worm2Button.setEnabled(false);

            frame.add(nameButton);
            frame.add(worm1Button);
            frame.add(worm2Button);

        }

        this.showCursor();
    }

    public void update(long l) {
        frame.update();
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(150, 120, 255));
        g.drawImage(getImage(filePath + "records.png"), 0, 0, null);
        frame.render(g);
    }
}
