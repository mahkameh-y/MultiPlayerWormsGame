package server.networkManager;


import server.gameEngine.GameEngine;
import server.actions.*;
import shared.PlayerInfo;
import shared.events.StartEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Jan 27, 2006
 * Time: 1:21:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class TCPManagerServer implements GlobalData {

    private static Socket player1Socket;
    private static Socket player2Socket;
    private static ObjectOutputStream player1OutputObject;
    private static ObjectOutputStream player2OutputObject;
    private static ObjectInputStream player1InputObject;
    private static ObjectInputStream player2InputObject;
    private static Thread player1;
    private static Thread player2;
    private static ServerSocket welcomeSocket;
    private static int numberOfPlayers = 0;
    private static boolean running = true;

    public static void close() {
        running = false;
        System.out.println("server stopped");
    }

    public static void sendObject(Object object) throws IOException {
        player1OutputObject.writeObject(object);
        player1OutputObject.flush();
        player2OutputObject.writeObject(object);
        player2OutputObject.flush();
    }

    public static void receivePacket(int playerNumber) {
        try {
            while (running) {
                Object received = null;
                if (playerNumber == 1) {
                    received = player1InputObject.readObject();
                }
                if (playerNumber == 2) {
                    received = player2InputObject.readObject();
                }
                if (received != null) {

                    Action action = null;
                    if (received instanceof shared.ClientActions.Action) {

                        if (received instanceof shared.ClientActions.JumpAction) {
                            action = new JumpAction();
                        }

                        else if (received instanceof shared.ClientActions.MoveLeftAction) {
                            action = new MoveLeftAction();

                        } else if (received instanceof shared.ClientActions.MoveRightAction) {
                            action = new MoveRightAction();

                        } else if (received instanceof shared.ClientActions.ShootAction) {
                            action = new ShootAction();
                        } else if (received instanceof shared.ClientActions.ExplodeAction) {
                            action = new ExplodeAction();
                        } else if (received instanceof shared.ClientActions.BuildingWallAction) {
                            action = new BuildingWallAction();
                            //    System.out.println("too net e server action = " + action);
                        } else if (received instanceof shared.ClientActions.ChangeWeaponAction) {
                            action = new ChangeWeaponAction(((shared.ClientActions.ChangeWeaponAction) received).getWeapon());
                            System.out.println("change");
                        } else if (received instanceof shared.ClientActions.FlyingBombAction) {
                            action = new FlyingBobmAction();
                        } else if (received instanceof shared.ClientActions.BazookaAction) {
                            action = new BazookaAction();
                        }

                        if (playerNumber == 1)
                            action.setPlayer(server.networkManager.PlayerType.worm1);
                        else if (playerNumber == 2)
                            action.setPlayer(server.networkManager.PlayerType.worm2);
                        GameEngine.addActionOrEvent(action);
                    } else if (received instanceof StartEvent) {
                        GameEngine.setPlayerStart(playerNumber);
                    }
                }
            }
            player1Socket.close();
            player2Socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void acceptConnection(int serverPort) {
        welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(serverPort);
            while (numberOfPlayers <= 1) {
                {
                    final Socket connectionSocket = welcomeSocket.accept();
                    try {
                        numberOfPlayers++;
                        if (numberOfPlayers == 1) {
                            player1Socket = connectionSocket;
                            player1OutputObject = new ObjectOutputStream(player1Socket.getOutputStream());
                            player1InputObject = new ObjectInputStream(player1Socket.getInputStream());
                            sendGameInfo(player1OutputObject);
                            player1 = new Thread() {
                                public void run() {
                                    receivePacket(1);
                                }
                            };
                            player1.start();
                        } else {
                            if (numberOfPlayers == 2) {
                                player2Socket = connectionSocket;
                                player2InputObject = new ObjectInputStream(player2Socket.getInputStream());
                                player2OutputObject = new ObjectOutputStream(player2Socket.getOutputStream());
                                sendGameInfo(player2OutputObject);
                                player2 = new Thread() {
                                    public void run() {
                                        receivePacket(2);
                                    }
                                };
                                player2.start();
                            } else {
                                System.out.println("The server capacity if full");
                                return;
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            welcomeSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendGameInfo(ObjectOutputStream playerOutputObject) throws IOException {
        playerOutputObject.writeObject(new PlayerInfo(numberOfPlayers));
        playerOutputObject.flush();
    }
}
