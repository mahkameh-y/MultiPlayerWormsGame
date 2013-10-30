package networkManager;


import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Vector;

import shared.GameInfo;
import shared.GameResult;


/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Jan 27, 2006
 * Time: 1:21:24 AM
 */
public class TCPManagerClient implements GlobalData {

    private static Vector boardsInfo;
    private static Vector recordsInfo;

    public static int sendToGameServer(Object object) {
        Socket gameServerSocket = null;
        try {
            gameServerSocket = new Socket(InetAddress.getByName(GlobalData.GameServerIP), GlobalData.GameServerPort);

            ObjectOutputStream gameServerOutputObject = new ObjectOutputStream(gameServerSocket.getOutputStream());
            ObjectInputStream gameServerInputObject = new ObjectInputStream(gameServerSocket.getInputStream());
            gameServerOutputObject.writeObject(object);
            gameServerOutputObject.flush();

            if (object instanceof GameInfo) {

            } else if (object.equals("QUERY")) {
                boardsInfo = (Vector) gameServerInputObject.readObject();
            } else if (object.equals("RECORDS")) {
                recordsInfo = (Vector) gameServerInputObject.readObject();
            } else if (object instanceof GameResult) {

            }
            gameServerSocket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return gameServerSocket.getLocalPort();
    }

    public static Vector getBoardsInfo() {
        return boardsInfo;
    }

    public static Vector getRecordsInfo() {
        return recordsInfo;
    }
}
