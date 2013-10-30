package shared;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 10, 2006
 * Time: 6:30:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameResult implements Serializable {
    private String gameName;
    private int worm1Score;
    private int worm2Score;

    public GameResult(String gameName, int worm1Score, int worm2Score) {
        this.gameName = gameName;
        this.worm1Score = worm1Score;
        this.worm2Score = worm2Score;
    }

    public int getWorm1Score() {
        return worm1Score;
    }

    public void setWorm1Score(int worm1Score) {
        this.worm1Score = worm1Score;
    }

    public int getWorm2Score() {
        return worm2Score;
    }

    public void setWorm2Score(int worm2Score) {
        this.worm2Score = worm2Score;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String toString() {
        return "1: " + worm1Score + " - 2:" + worm2Score;
    }
}
