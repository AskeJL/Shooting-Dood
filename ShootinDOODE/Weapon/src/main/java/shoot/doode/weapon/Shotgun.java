/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.weapon;

import shoot.doode.commonweapon.Weapon;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.commonweapon.Bullet;
import java.util.ArrayList;

/**
 *
 * @author tobia
 */
public class Shotgun extends Weapon{
    private String module = "Weapon";

    private String[] soundPaths;
    String[] spritePaths;

    public Shotgun() {

        spritePaths = new String[1];
        spritePaths[0] = "Shotgun.png";
        
        soundPaths = new String[1];
        soundPaths[0] = "Gun_Fire.mp3";
        this.add(new SpritePart(module, spritePaths));
        this.add(new SoundPart(module, soundPaths));
        
        this.setReloadTime(2.5);
        this.setDamage(1);
        
    }

    @Override
    public void shoot(GameData gameData, World world,ShootingPart shootingPart) {
        SoundPart soundpart = this.getPart(SoundPart.class);
        PositionPart positionPart = this.getPart(PositionPart.class);
        ArrayList<Entity> bulletList = new ArrayList<>();
        double dmgModifier = shootingPart.getDamageModifier();
        float bulletSpeed = 4.2f;
        for(int i = 0; i < 17; i++)
        {
            Entity bullet = Bullet.createBullet(this,bulletSpeed + (float)((Math.random()-0.5)*1.3),positionPart.getRotation() + (float)((Math.random()-0.5)*1.25),dmgModifier);
            bulletList.add(bullet);
        }
        
        for(Entity bullet : bulletList)
        {
            world.addEntity(bullet);
        }
        
        soundpart.setPlay("Gun_Fire.mp3", true);       
    }
    
}
