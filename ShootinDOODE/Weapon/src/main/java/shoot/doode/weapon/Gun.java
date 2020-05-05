package shoot.doode.weapon;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.commonweapon.Bullet;
import shoot.doode.commonweapon.Weapon;


public class Gun extends Weapon {
    
    public Gun() {
        
    }

    @Override
    public void shoot(GameData gameData, World world) {
        Entity bullet = Bullet.createBullet(this);
        world.addEntity(bullet);
    }
}
