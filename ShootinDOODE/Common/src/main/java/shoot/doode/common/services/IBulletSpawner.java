/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.common.services;

import shoot.doode.common.data.World;

/**
 *
 * @author tobia
 */
public interface IBulletSpawner {
    public void spawnBullet(float xPos,float yPos,float bulletSpeed, float rotation,float timer , double damege, String shooterID,World world);
    
}
