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
    public Bullet(float xPos,float yPos,float bulletSpeed, float rotation,float timer , double damege, int toughness)
    {
        String module = "Bullet";
        String[] spritePaths = new String[1];
        spritePaths[0] = "bullet.png";
        
        this.add(new PositionPart(xPos, yPos, rotation));
        this.add(new ProjectileMovingPart(bulletSpeed));
        this.add(new TimerPart(timer));
        this.add(new LifePart(1));
        this.add(new SpritePart(module,spritePaths));  
        this.add(new ProjectilePart(damege));
        this.setToughness(toughness);
        this.setBoundaryWidth(5);
        this.setBoundaryHeight(5);
    }
}
