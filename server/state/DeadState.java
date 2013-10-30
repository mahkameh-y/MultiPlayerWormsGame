package server.state;
import shared.WormState;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 5, 2006
 * Time: 12:03:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeadState implements server.state.WormState{
        public String toString() {
        return WormState.DEAD;
    }
    
}
