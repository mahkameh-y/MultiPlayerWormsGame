package server.state;
import shared.WormState;
/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Jan 31, 2006
 * Time: 4:55:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class FallingState implements server.state.WormState{
        public String toString(){
        return WormState.FALLING;
    }
}
