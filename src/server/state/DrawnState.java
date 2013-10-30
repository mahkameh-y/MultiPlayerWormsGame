package server.state;

import shared.WormState;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 4, 2006
 * Time: 11:55:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class DrawnState implements server.state.WormState{
    public String toString() {
        return WormState.DRAWN;
    }
}
