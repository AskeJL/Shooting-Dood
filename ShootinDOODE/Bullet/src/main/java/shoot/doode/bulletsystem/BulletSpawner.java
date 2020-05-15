/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.bulletsystem;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.World;
import shoot.doode.common.services.IBulletSpawner;

/**
 *
 * @author tobia
 */
public class BulletSpawner extends Entity implements IBulletSpawner{

    @Override
    public void spawnBullet(Entity shooter, float bulletSpeed, float rotation, double damege,World world) {
        Entity bullet = Bullet.createBullet(shooter, bulletSpeed, rotation, damege);
        world.addEntity(bullet);
    }
    
    
}
