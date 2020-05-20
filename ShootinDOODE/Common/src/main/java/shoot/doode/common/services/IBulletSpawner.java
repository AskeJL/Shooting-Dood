/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.common.services;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.World;

/**
 *
 * @author tobia
 */
public interface IBulletSpawner {
    public static IBulletSpawner getSpawner(World world)
    {
        IBulletSpawner bulletSpawner = null;
        for (Entity ibullet : world.getEntities()) {
            if (ibullet instanceof IBulletSpawner) {
                bulletSpawner = (IBulletSpawner) ibullet;
            }
        }
        return bulletSpawner;
    }
    public void spawnBullet(float xPos,float yPos,float bulletSpeed, float rotation,float timer , double damege, int toughness,World world);
    
}
