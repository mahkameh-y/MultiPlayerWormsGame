package server.state;

import shared.WormState;

/**
 * Created by IntelliJ IDEA.
 * User: tannaz
 * Date: Feb 1, 2006
 * Time: 8:16:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class NormalState implements server.state.WormState {
    public String toString() {
        return WormState.NORMAL;
    }
}
