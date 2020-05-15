package shoot.doode.commonweapon;

import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ProjectileMovingPart;
import shoot.doode.common.data.entityparts.ProjectilePart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.data.entityparts.TimerPart;

public class Bullet extends CollidableEntity {

    //Could potentially do some shenanigans with differing colours for differing sources.
    public static Entity createBullet(Entity weapon,float bulletSpeed, float rotation, double damege) {
        PositionPart weaponPosition = weapon.getPart(PositionPart.class);
        ShootingPart shoot = weapon.getPart(ShootingPart.class);
        CollidableEntity bullet = new Bullet();
        
        String module = "CommonWeapon";
        String[] spritePaths = new String[1];
        spritePaths[0] = "bullet.png";
        
        
        bullet.add(new PositionPart(weaponPosition.getX(), weaponPosition.getY(), rotation));
        bullet.add(new ProjectileMovingPart(bulletSpeed));
        bullet.add(new TimerPart(3));
        bullet.add(new LifePart(1));
        bullet.add(new SpritePart(module,spritePaths));
        // Projectile Part only used for better collision detection     
        bullet.add(new ProjectilePart(weapon.getID(), damege));
        bullet.setRadius(2);

        bullet.setBoundaryWidth(10);
        bullet.setBoundaryHeight(10);

        return bullet;
    }
}
