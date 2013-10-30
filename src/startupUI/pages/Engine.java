package startupUI.pages;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.GameLoader;

import java.awt.*;


/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 8:17:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Engine extends GameEngine {

    public static final int WELCOME = 0;
    public static final int CREATE = 1;
    public static final int JOIN = 2;
    public static final int RECORDS = 3;
    public static final int HELP = 4;
    public static final int PLAY = 5;

    public boolean showFPS = false;

    {
        distribute = true;
    }

    public static void main(String[] args) {
        GameLoader game = new GameLoader();
        Engine engine = new Engine();
        engine.init(game);
    }

    private void init(GameLoader game) {

        game.setup(new Engine(), new Dimension(940, 680), false);
        game.start();
    }


    public void initResources() {
        nextGameID = WELCOME;
    }


    public GameObject getGame(int gameID) {
        GameObject nextGame = null;

        switch (gameID) {
            case WELCOME:
                nextGame = new WelcomePage(this);
                break;
            case CREATE:
                nextGame = new CreatePage(this);
                break;
            case JOIN:
                nextGame = new JoinPage(this);
                break;
            case RECORDS:
                nextGame = new RecordPage(this);
                break;
            case HELP:
                nextGame = new HelpPage(this);
                break;
            case PLAY:
                break;
        }
        return nextGame;
    }
}
