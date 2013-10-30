package startupUI.pages;

import com.golden.gamedev.GameObject;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.gui.toolkit.FrameWork;
import com.golden.gamedev.gui.TLabel;
import com.golden.gamedev.gui.TTextField;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import startupUI.button.*;
import networkManager.TCPManagerClient;
import shared.GameInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 8:51:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreatePage extends GameObject implements Path {
    private FrameWork frame;
    private GameInfo gameInfo = new GameInfo();
    private TTextField nameField;

    public CreatePage(GameEngine gameEngine) {
        super(gameEngine);
    }

    public void initResources() {
        setMaskColor(Color.GREEN);

        // creates the gui frame work
        frame = new FrameWork(bsInput, getWidth(), getHeight());

        this.bsGraphics.setWindowIcon(getImage("src/resources/Ladybug.gif"));
        this.bsGraphics.setWindowTitle("Worms MACT ");

        TLabel nameLabel = new TLabel("NAME", 20, 20 + 250, 100, 20);
        nameField = new TTextField("", 200, 20 + 250, 200, 20);

        GameFont f = fontManager.getFont(getImage(filePath + "bitmapfont.png"));

        nameLabel.UIResource().put("Text Font", f);
        nameLabel.UIResource().put("Text Over Font", f);
        nameLabel.UIResource().put("Text Pressed Font", f);

        TLabel levelLabel = new TLabel("LEVEL", 20, 70 + 250, 100, 20);
        levelLabel.UIResource().put("Text Font", f);
        levelLabel.UIResource().put("Text Over Font", f);
        levelLabel.UIResource().put("Text Pressed Font", f);

        PicturedButton easyButton = new RectanglePicturedButton(this, "", "easy.png", 200, 50 + 250, 100, 70) {
            public void doAction() {
                gameInfo.setLevel(GameInfo.EASY);
            }
        };

        PicturedButton hardButton = new RectanglePicturedButton(this, "", "hard.png", 350, 50 + 250, 100, 70) {
            public void doAction() {
                gameInfo.setLevel(GameInfo.HARD);
            }
        };

        TLabel mapLabel = new TLabel("MAP", 20, 410, 100, 20);
        mapLabel.UIResource().put("Text Font", f);
        mapLabel.UIResource().put("Text Over Font", f);
        mapLabel.UIResource().put("Text Pressed Font", f);
        PicturedButton sunnyButton = new PicturedButton(this, "SUNNY", "sunny.jpg", 200, 380, 80, 100) {
            public void doAction() {
                gameInfo.setConfig(GameInfo.SUNNY);
            }
        };

        PicturedButton cloudyButton = new PicturedButton(this, "CLOUDY", "cloudy.jpg", 350, 380, 80, 100) {
            public void doAction() {
                gameInfo.setConfig(GameInfo.CLOUDY);
            }
        };

        TLabel playerLabel = new TLabel("COMPETITOR", 20, 520, 150, 20);
        playerLabel.UIResource().put("Text Font", f);
        playerLabel.UIResource().put("Text Over Font", f);
        playerLabel.UIResource().put("Text Pressed Font", f);
        PicturedButton onePlayerButton = new PicturedButton(this, "COMPUTER", "one.png", 200, 490, 150, 100);

        PicturedButton twoPlayerButton = new PicturedButton(this, "PLAYER2", "multi.png", 350, 490, 150, 100);

        PicturedButton okButton = new PicturedButton(this, "CREATE", "ok.png", 200, 590, 150, 100) {
            public void doAction() {
                gameInfo.setName(nameField.getText());
                try {
                    gameInfo.setIP(InetAddress.getLocalHost());
                } catch (UnknownHostException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                int clientPort = TCPManagerClient.sendToGameServer(gameInfo);
                gameInfo.setPort(clientPort);

//                System.out.println("game info sent = " + gameInfo);
                parent.nextGameID = Engine.WELCOME;
                finish();

                new Thread() {
                    public void run() {
                        server.gameEngine.GameEngine.main(gameInfo);
                    }
                }.start();
//                System.out.println("between");
                client.networkManager.TCPManagerClient.main(gameInfo);


            }
        };
        PicturedButton cancelButton = new PicturedButton(this, "CANCEL", "cancel.png", 350, 590, 150, 100) {
            public void doAction() {
                parent.nextGameID = Engine.WELCOME;
                finish();
            }
        };


        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(levelLabel);
        frame.add(mapLabel);
        frame.add(playerLabel);

        frame.add(easyButton);
        frame.add(hardButton);
        frame.add(sunnyButton);
        frame.add(cloudyButton);
        frame.add(okButton);
        frame.add(onePlayerButton);
        frame.add(twoPlayerButton);
        frame.add(cancelButton);

        this.showCursor();
    }

    public void update(long l) {
        frame.update();
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(150, 120, 255));
        g.drawImage(getImage(filePath + "create.png"), 0, 0, null);
        frame.render(g);
    }
}
