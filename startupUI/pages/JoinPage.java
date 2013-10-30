package startupUI.pages;

import com.golden.gamedev.gui.toolkit.FrameWork;
import com.golden.gamedev.gui.TButton;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;

import java.awt.*;
import java.util.Vector;

import startupUI.button.PicturedButton;
import startupUI.button.Path;
import networkManager.TCPManagerClient;
import shared.GameInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 11:12:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class JoinPage extends GameObject implements Path {
    private FrameWork frame;
    private Vector boards = new Vector();
    private Vector boardList = new Vector();
    private int selectedRow = -1;

    public JoinPage(GameEngine gameEngine) {
        super(gameEngine);
    }

    public void initResources() {

        TCPManagerClient.sendToGameServer("QUERY");
        boards = TCPManagerClient.getBoardsInfo();

        setMaskColor(Color.GREEN);

        // creates the gui frame work
        frame = new FrameWork(bsInput, getWidth(), getHeight());

        this.bsGraphics.setWindowIcon(getImage("../../resources/Ladybug.gif"));
        this.bsGraphics.setWindowTitle("Worms MACT ");

        PicturedButton requestButton = new PicturedButton(this, "JOIN", "ok.png", 200, 590, 150, 100) {
            public void doAction() {
                if (selectedRow == -1) {
                    System.out.println("no row selected");
                } else {
                    parent.nextGameID = Engine.WELCOME;
                    finish();
                    System.out.println("selected board =" + boards.elementAt(selectedRow));
                    client.networkManager.TCPManagerClient.main((GameInfo) boards.elementAt(selectedRow));
                }
            }
        };
        PicturedButton cancelButton = new PicturedButton(this, "CANCEL", "cancel.png", 350, 590, 150, 100) {
            public void doAction() {
                parent.nextGameID = Engine.WELCOME;
                finish();
            }
        };
        frame.add(requestButton);
        frame.add(cancelButton);

        requestButton.setToolTipText("Click to request game owner to participate");

        TButton levelTitleButton = new TButton("LEVEL", 150, 330 , 100, 20);
        TButton configTitleButton = new TButton("MAP", 250, 330, 100, 20);
        TButton IPTitleButton = new TButton("IP", 350, 330, 150, 20);
        TButton portTitleButton = new TButton("PORT", 500, 330, 100, 20);
        TButton nameTitleButton = new TButton("NAME", 50, 330, 100, 20);
        levelTitleButton.setEnabled(false);
        configTitleButton.setEnabled(false);
        IPTitleButton.setEnabled(false);
        portTitleButton.setEnabled(false);
        nameTitleButton.setEnabled(false);

        frame.add(levelTitleButton);
        frame.add(configTitleButton);
        frame.add(IPTitleButton);
        frame.add(portTitleButton);
        frame.add(nameTitleButton);
        for (int i = 0; i < boards.size(); i++) {
            TButton levelButton = new TButton(((GameInfo) boards.elementAt(i)).getLevelValue(), 150, 350 + 20 * i, 100, 20);
            TButton configButton = new TButton(((GameInfo) boards.elementAt(i)).getConfigValue(), 250, 350 + 20 * i, 100, 20);
            TButton IPButton = new TButton(((GameInfo) boards.elementAt(i)).getIP().getHostAddress(), 350, 350 + 20 * i, 150, 20);
            TButton portButton = new TButton(String.valueOf(((GameInfo) boards.elementAt(i)).getPort()), 500, 350 + 20 * i, 100, 20);
            TButton nameButton = new TButton(((GameInfo) boards.elementAt(i)).getName(), 50, 350 + 20 * i, 100, 20) {
                public void doAction() {
                    for (int k = 0; k < boardList.size(); k++)
                        ((TButton) boardList.elementAt(k)).setEnabled(true);
                    this.setEnabled(false);
                    selectedRow = boardList.indexOf(this);
                }
            };
            levelButton.setEnabled(false);
            configButton.setEnabled(false);
            IPButton.setEnabled(false);
            portButton.setEnabled(false);

            frame.add(levelButton);
            frame.add(configButton);
            frame.add(IPButton);
            frame.add(portButton);
            frame.add(nameButton);

            boardList.add(nameButton);
        }

        this.showCursor();
    }

    public void update(long l) {
        frame.update();
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(150, 120, 255));
        g.drawImage(getImage(filePath + "join.png"), 0, 0, null);
        frame.render(g);
    }
}
