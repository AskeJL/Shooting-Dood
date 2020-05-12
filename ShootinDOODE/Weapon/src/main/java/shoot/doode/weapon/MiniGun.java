/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.weapon;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.commonweapon.Bullet;
import shoot.doode.commonweapon.Weapon;

/**
 *
 * @author tobia
 */
public class MiniGun extends Weapon {

    private String module = "Weapon";

    private String[] soundPaths;
    String[] spritePaths;

    public MiniGun() {

        spritePaths = new String[1];
        spritePaths[0] = "gun.png";
        
        soundPaths = new String[1];
        soundPaths[0] = "Gun_Fire.mp3";
        this.add(new SpritePart(module, spritePaths));
        this.add(new SoundPart(module, soundPaths));
        
        this.setReloadTime(0.1);
        this.setDamage(0.5);
        
    }

    @Override
    public void shoot(GameData gameData, World world,ShootingPart shootingPart) {
        SoundPart soundpart = this.getPart(SoundPart.class);
        PositionPart positionPart = this.getPart(PositionPart.class);
        double dmgModifier = shootingPart.getDamageModifier();
        Entity bullet = Bullet.createBullet(this,4.5f,positionPart.getRotation()+(float)((Math.random()-0.5)*0.3),this.getDamage()*dmgModifier);
        world.addEntity(bullet);
        
        soundpart.setPlay("Gun_Fire.mp3", true);
        
        
    }
}
