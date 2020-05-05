package shoot.doode.commonweapon;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ProjectilePart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.TimerPart;

public class Bullet extends Entity {

    //Could potentially do some shenanigans with differing colours for differing sources.
    public static Entity createBullet(Entity weapon, float rotation, double damege) {
        PositionPart weaponPosition = weapon.getPart(PositionPart.class);
        ShootingPart shoot = weapon.getPart(ShootingPart.class);
        Entity bullet = new Bullet();
        
        if (rotation > Math.PI * 2) {
            rotation = rotation - ((float) Math.PI * 2);
        }

        if (rotation < 0) {
            rotation = (float)(Math.PI*2)+rotation;
        }

        for (int i = 0; i < 20; i++) {
            System.out.println(rotation);
        }
        bullet.add(new PositionPart(weaponPosition.getX(), weaponPosition.getY(), rotation));
        bullet.add(new PlayerMovingPart((float) 4.5));
        bullet.add(new TimerPart(3));
        bullet.add(new LifePart(1));

        // Projectile Part only used for better collision detection     
        bullet.add(new ProjectilePart(weapon.getID(), damege));
        bullet.setRadius(2);

        float[] colour = new float[4];
        colour[0] = 0.2f;
        colour[1] = 0.5f;
        colour[2] = 0.7f;
        colour[3] = 1.0f;

        bullet.setColour(colour);

        return bullet;
    }
}
