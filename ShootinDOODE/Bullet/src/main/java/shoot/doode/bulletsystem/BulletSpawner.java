package shoot.doode.bulletsystem;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.World;
import shoot.doode.common.services.IBulletSpawner;

public class BulletSpawner extends Entity implements IBulletSpawner{

    @Override
    public void spawnBullet(float xPos,float yPos,float bulletSpeed, float rotation,float timer , double damage, int toughness,World world) {
        Entity bullet = new Bullet(xPos,yPos,bulletSpeed, rotation,timer , damage, toughness);
        
        world.addEntity(bullet);
    }
    
    
}
