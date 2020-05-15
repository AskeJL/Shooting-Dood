package shoot.doode.commonweapon;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.services.IBulletSpawner;


public abstract class Weapon extends Entity {
    
    private double currentTime;
    private double reloadTime;
    private double damage;

    public Weapon() {
    }
    
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
    
    abstract public void shoot(GameData gameData, World world,Entity shooter);
    
    public double getCurrentTime() {
        return currentTime;
    }
    
    public void SetCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }
    
    public double getRealoadTime() {
        return reloadTime;
    }
    
    public double getDamage() {
        return damage;
    }
    
    public void setReloadTime(double reloadTime) {
        this.reloadTime = reloadTime;
    }
    
    public void setDamage(double damage) {
        this.damage = damage;
    }
    
    
}
