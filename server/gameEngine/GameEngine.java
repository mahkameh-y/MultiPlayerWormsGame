package server.gameEngine;

import server.events.DeathEvent;
import server.events.PrizeEvent;
import server.events.SinkEvent;
import server.state.GameState;
import server.config.ConfigurationManager;
import server.entity.Island;
import server.networkManager.TCPManagerServer;
import shared.GameInfo;
import shared.GameResult;
import shared.GameStatePacket;

import java.io.IOException;
import java.util.Vector;
import java.util.Random;

import networkManager.TCPManagerClient;

/**
 * Created by IntelliJ IDEA.
 * User: a.moallem
 * Date: Jan 27, 2006
 * Time: 11:55:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameEngine extends Thread {
    private GameState gameState;
    private GameLogic gameLogic;
    private static Vector actionsOrEvents;
    private GameTimer gameTimer;
    private static boolean player1Started;
    private static boolean player2Started;
    private static int mapNumber;
    public static byte level;
    private static String gameName;

    public GameEngine(byte level) {
        //Azin
        GameEngine.level = level;
        gameState = new GameState();
        gameLogic = new GameLogic();
        actionsOrEvents = new Vector();
        gameTimer = new GameTimer();
        player1Started = false;
        player2Started = false;
        gameTimer.setSendingGoodElapse(5000);
        setLevelConfiguration();

    }

    private void setLevelConfiguration() {
        if (level==GameInfo.HARD){
            gameLogic.setNumOfPrizesEachTime(1);
            gameLogic.setNumOfDangersEachTime(4);
            gameTimer.setSinkingElapse(5000);
        }else{
            gameLogic.setNumOfPrizesEachTime(4);
            gameLogic.setNumOfDangersEachTime(2);
            gameTimer.setSinkingElapse(20000);
        }
    }



    public boolean isPlayer1Started() {
        return player1Started;
    }

    public static void setPlayerStart(int numOfPlayer) {
        if (numOfPlayer == 1)
            player1Started = true;
        else if (numOfPlayer == 2)
            player2Started = true;
    }

    public boolean isGameStarted() {
        return (player1Started && player2Started);
    }

    public boolean isPlayer2Started() {
        return player2Started;
    }

    synchronized public Object readActionOrEvent() {
        if (!actionsOrEvents.isEmpty())
            return (Object) actionsOrEvents.remove(0);
        return null;
    }

    synchronized static public void addActionOrEvent(Object actionOrEvent) {
        actionsOrEvents.add(actionOrEvent);
    }

    private boolean anyActionOrEvent() {
        if (!actionsOrEvents.isEmpty())
            return true;
        return false;
    }

    public void applyActionOrEvent() {
        updateState(readActionOrEvent());
    }

    private void updateState(Object actionOrEvent) {
        gameState = gameLogic.nextState(gameState, actionOrEvent);
    }

    private void loadGame() {
        ConfigurationManager.loadIsland(mapNumber);
        Island.initIsland();
        gameState.getWorm1().moveToIslandSurface();
        gameState.getWorm2().moveToIslandSurface();
        GameStatePacket gameStatePacket = null;
        gameStatePacket = new GameStatePacket(gameState);
        try {
            TCPManagerServer.sendObject(gameStatePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (!isGameStarted()) ;
        System.out.println("********* game was started *********");
        loadGame();
        setGameTime();

        while (!gameState.gameIsFinished()) {
            if (gameState.inEvolution() || anyActionOrEvent()) {
                if (!anyActionOrEvent())
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                applyActionOrEvent();
                GameStatePacket gameStatePacket = new GameStatePacket(gameState);
                try {
                    TCPManagerServer.sendObject(gameStatePacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        gameState.getWorm1().setFinalScore();
        gameState.getWorm2().setFinalScore();
        GameResult gameResult = new GameResult(gameName, gameState.getWorm1().getScore(), gameState.getWorm2().getScore());
        TCPManagerClient.sendToGameServer(gameResult);
        TCPManagerServer.close();
    }

    public class GameTimer extends Thread {

        private int sendingGoodElapseTime; //fasele zamani ke bayad jayeze ferestade beshe
        private int sinkingElapseTime; // fasele zamani ke bayad badesh jazire ye kam paieen bere
        private Random random = new Random();

        public void run() {
            long startTime = System.currentTimeMillis();
            long lastTime = System.currentTimeMillis() - startTime;
            long newTime = 0;

            while (true) {
                newTime = System.currentTimeMillis() - startTime;
                if ((newTime % sendingGoodElapseTime < 5) && (newTime - lastTime > 5)) {
                    if (random.nextInt(2) == 0) {
                        addActionOrEvent(new PrizeEvent());
                    } else {
                        addActionOrEvent(new DeathEvent());
                    }
                }
                if ((newTime % sinkingElapseTime < 5) && (newTime - lastTime >= 5)) {
                    addActionOrEvent(new SinkEvent());
                }
                lastTime = newTime;
            }
        }

        public void setSendingGoodElapse(int sendingGoodElapse) {
            this.sendingGoodElapseTime = sendingGoodElapse;
        }

        public void setSinkingElapse(int sinkingElapse) {
            this.sinkingElapseTime = sinkingElapse;
        }

        public void startTimer() {
            this.start();
        }
    }

    private void setGameTime() {
        gameTimer.startTimer();
    }

    public static void main(GameInfo gameInfo) {
        final GameEngine gameEngin = new GameEngine(gameInfo.getLevel());
        mapNumber = gameInfo.getConfig();
        gameName = gameInfo.getName();
        TCPManagerServer.acceptConnection(gameInfo.getPort());
        gameEngin.start();
    }

}

