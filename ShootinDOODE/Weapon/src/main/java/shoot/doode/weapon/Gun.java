package shoot.doode.weapon;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.services.IBulletSpawner;
import shoot.doode.commonweapon.Weapon;

public class Gun extends Weapon {

    private String module = "Weapon";

    private String[] soundPaths;
    String[] spritePaths;

    public Gun() {

        spritePaths = new String[1];
        spritePaths[0] = "gun.png";
        
        soundPaths = new String[1];
        soundPaths[0] = "Gun_Fire.mp3";
        this.add(new SpritePart(module, spritePaths));
        this.add(new SoundPart(module, soundPaths));
        
        this.setReloadTime(1);
        this.setDamage(5);
        
    }

    @Override
    public void shoot(GameData gameData, World world,Entity shooter) {
        IBulletSpawner bulletSpawner = Weapon.getSpawner(world);
        
        
        SoundPart soundpart = this.getPart(SoundPart.class);
        PositionPart positionPart = this.getPart(PositionPart.class);
        ShootingPart shootingPart = shooter.getPart(ShootingPart.class);
        double dmgModifier = shootingPart.getDamageModifier();
        if(bulletSpawner != null)
        {
            bulletSpawner.spawnBullet(positionPart.getX(),positionPart.getY(), 4.5f, positionPart.getRotation(),5, this.getDamage()*dmgModifier,shooter.getID(), world);
        }
        
        soundpart.setPlay("Gun_Fire.mp3", true);
        
        
    }
}
