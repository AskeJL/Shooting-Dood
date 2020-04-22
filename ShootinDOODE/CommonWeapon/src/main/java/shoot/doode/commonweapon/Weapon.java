package shoot.doode.commonweapon;

import shoot.doode.common.data.Entity;


public class Weapon extends Entity {
    
    private WeaponType type;

    public Weapon(WeaponType type) {
        this.type = type;
    }

    public String getType() {
        return type.getType();
    }
}
