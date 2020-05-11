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
import shoot.doode.commonweapon.Bullet;

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
        
        this.setReloadTime(1);
        this.setDamage(2);
        
    }

    @Override
    public void shoot(GameData gameData, World world,double dmgModifier) {
        SoundPart soundpart = this.getPart(SoundPart.class);
        PositionPart positionPart = this.getPart(PositionPart.class);
        
        Entity bullet1 = Bullet.createBullet(this, positionPart.getRotation(),this.getDamage()*dmgModifier);
        Entity bullet2 = Bullet.createBullet(this, positionPart.getRotation()- ((float)Math.PI/4),this.getDamage()*dmgModifier);
        Entity bullet3 = Bullet.createBullet(this, positionPart.getRotation() + ((float)Math.PI/4),this.getDamage()*dmgModifier);
        
        
        
        world.addEntity(bullet1);
        world.addEntity(bullet2);
        world.addEntity(bullet3);
        soundpart.setPlay("Gun_Fire.mp3", true);
        
        
    }
    
}
