package shared;

import java.net.InetAddress;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Mahkameh
 * Date: Mar 5, 2006
 * Time: 10:50:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameInfo implements Serializable {
    public static final byte HARD = 1;
    public static final byte EASY = 2;

    public static final byte SUNNY = 1;
    public static final byte CLOUDY = 2;

    private String name;
    private byte level;
    private byte config;
    private InetAddress IP;
    private int port;

    public GameInfo() {
    }

    public GameInfo(String name, byte level, byte config) {
        this.name = name;
        this.level = level;
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public String getLevelValue() {
        if (level == HARD)
            return "HARD";
        if (level == EASY)
            return "EASY";
        if (level == 0)
            return "EASY";
        return "";
    }

    public String getConfigValue() {
        if (config == SUNNY)
            return "SUNNY";
        if (config == CLOUDY)
            return "CLOUDY";
        if (config == 0)
            return "SUNNY";
        return "";
    }

    public byte getLevel() {
        return (level == 0) ? EASY : level;
    }

    public byte getConfig() {
        return (config == 0) ? SUNNY : config;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public void setConfig(byte config) {
        this.config = config;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIP(InetAddress IP) {
        this.IP = IP;
    }

    public InetAddress getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }

    public String toString() {
        return name + "             " + IP + "          " + port;
    }
}
