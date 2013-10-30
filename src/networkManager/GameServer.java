package networkManager;


import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

import shared.GameInfo;
import shared.GameResult;

/**
 * User: GrifenDoors
 * Date: Mar 6, 2006
 * Time: 10:31:48 AM
 */
public class GameServer extends Thread implements GlobalData {
    private Vector boards = new Vector();    //<game info>
    private Vector records = new Vector(); //<game result>

    public void run() {
        ServerSocket welcomeSocket;
        try {
            welcomeSocket = new ServerSocket(GameServerPort);
            while (true) {
                final Socket connectionSocket = welcomeSocket.accept();
                try {

                    ObjectInputStream inputObject = new ObjectInputStream(connectionSocket.getInputStream());
                    ObjectOutputStream outputObject = new ObjectOutputStream(connectionSocket.getOutputStream());
                    Object received = inputObject.readObject();
                    if (received instanceof GameInfo) {
                        ((GameInfo) received).setPort(connectionSocket.getPort());
                        boards.add(received);
                    } else if (received.equals("QUERY")) {
                        outputObject.writeObject(boards);
                        outputObject.flush();
                    } else if (received.equals("RECORDS")) {
                        outputObject.writeObject(records);
                        outputObject.flush();
                    } else if (received instanceof GameResult) {
                        records.add(received);
                        removeBoard((GameResult)received, connectionSocket.getInetAddress(), connectionSocket.getPort());
                    }
                    connectionSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void removeBoard(GameResult received, InetAddress IP, int port) {
        for (int i = 0; i < boards.size(); i++) {
            GameInfo gameInfo = (GameInfo) boards.elementAt(i);
            if (gameInfo.getName().equals(received.getGameName()))
                boards.remove(gameInfo);
        }
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.start();
    }
}
