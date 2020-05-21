/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.enemyshoot;

import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.services.IBulletSpawner;
import shoot.doode.commonenemy.Enemy;

/**
 *
 * @author tobia
 */
public class ShootinEnemy extends Enemy {

    public ShootinEnemy(float score) {
        super(score);
    }

    public void shoot(World world) {
        IBulletSpawner bulletSpawner = IBulletSpawner.getSpawner(world);
        PositionPart positionPart = this.getPart(PositionPart.class);
        if (bulletSpawner != null) {
            for (int i = 0; i < 3; i++) {
                bulletSpawner.spawnBullet(positionPart.getX(), positionPart.getY(), 3f, positionPart.getRotation() + ((1 - i) * 0.6f), 3f, 2, this.getToughness(), world);
            }
       
        
    }
    
}}
