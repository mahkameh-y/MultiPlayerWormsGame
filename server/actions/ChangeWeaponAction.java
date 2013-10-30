package server.actions;

import shared.Weapon;

/**
 * Created by IntelliJ IDEA.
 * User: soft
 * Date: Mar 11, 2006
 * Time: 5:56:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChangeWeaponAction extends Action{
    shared.Weapon weapon;

    public ChangeWeaponAction(shared.Weapon weapon) {
        this.weapon = weapon;
    }

    public shared.Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
