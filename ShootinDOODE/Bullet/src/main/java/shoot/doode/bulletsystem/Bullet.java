package shoot.doode.bulletsystem;

import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ProjectileMovingPart;
import shoot.doode.common.data.entityparts.ProjectilePart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.data.entityparts.TimerPart;

public class Bullet extends CollidableEntity {

    public static Entity createBullet(float xPos,float yPos,float bulletSpeed, float rotation,float timer , double damege, int toughness) {

        CollidableEntity bullet = new Bullet();
        
        String module = "Bullet";
        String[] spritePaths = new String[1];
        spritePaths[0] = "bullet.png";
        
        
        bullet.add(new PositionPart(xPos, yPos, rotation));
        bullet.add(new ProjectileMovingPart(bulletSpeed));
        bullet.add(new TimerPart(timer));
        bullet.add(new LifePart(1));
        bullet.add(new SpritePart(module,spritePaths));  
        bullet.add(new ProjectilePart(damege));
        bullet.setToughness(toughness);
        bullet.setBoundaryWidth(5);
        bullet.setBoundaryHeight(5);

        return bullet;
    }
    
}
