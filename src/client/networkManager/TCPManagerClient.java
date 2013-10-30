package client.networkManager;

import client.GUI.GUI;

import java.net.InetAddress;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import shared.GameStatePacket;
import shared.PlayerInfo;
import shared.GameInfo;
import shared.events.SinkEvent;


/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Jan 27, 2006
 * Time: 1:21:24 AM
 */
public class TCPManagerClient implements GlobalData {

    private static Socket TCPSocket;
    private static ObjectOutputStream outputObject;
    private static ObjectInputStream inputObject;
    private static InetAddress gameServerIP;
    private static boolean running = false;
    public static GUI GUIInstance = new GUI();

    public static void close() {
        running = false;
        System.out.println("client stopped");
    }

    public static void sendObject(Object object) throws IOException {
        outputObject.writeObject(object);
        outputObject.flush();
    }

    public static void receivePacket() {
        Object received;
        try {
            while (running) {
                received = inputObject.readObject();
                if (received instanceof GameStatePacket) {
                    GUIInstance.updateState((GameStatePacket) received);
                } else if (received instanceof PlayerInfo) {
                    GUIInstance.setNumOfPlayer(((PlayerInfo) received).getPlayerNumber());

                } else if (received instanceof SinkEvent) {
                    GUIInstance.seaUpdate();
                }
            }
            TCPSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setGameServerIP(GameInfo gameInfo) {
        try {

            TCPSocket = new Socket(gameInfo.getIP(), gameInfo.getPort());
            outputObject = new ObjectOutputStream(TCPSocket.getOutputStream());
            inputObject = new ObjectInputStream(TCPSocket.getInputStream());
            running = true;
            Thread runServer = new Thread() {
                public void run() {
                    receivePacket();
                }
            };
            runServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(GameInfo gameInfo) {
        TCPManagerClient.GUIInstance.main(TCPManagerClient.GUIInstance, gameInfo);

    }
}
