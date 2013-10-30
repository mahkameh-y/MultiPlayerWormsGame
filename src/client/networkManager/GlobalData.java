package client.networkManager;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Jan 27, 2006
 * Time: 1:29:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GlobalData {
    public static int UDPPacketSize = 1024;
    public static int broadcastServerPort = 1000;
    public static int broadcastClientPort = 2000;
    public static int broadcastTimeout = 5000;
    public static int TCP_P2PServerPort = 3000;
    public static int TCP_GameServerPort = 4000;
}
