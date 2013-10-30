package shared.ClientActions;

import shared.Weapon;

/**
 * Created by IntelliJ IDEA.
 * User: soft
 * Date: Mar 11, 2006
 * Time: 5:53:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChangeWeaponAction extends Action{
    Weapon weapon;

    public ChangeWeaponAction(shared.Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(shared.Weapon weapon) {
        this.weapon = weapon;
    }
}
