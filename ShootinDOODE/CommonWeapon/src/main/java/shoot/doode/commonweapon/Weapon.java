package shoot.doode.commonweapon;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;


public abstract class Weapon extends Entity {
    
    private double currentTime;
    private double reloadTime;
    private double damage;

    public Weapon() {
    }
    
    abstract public void shoot(GameData gameData, World world,double dmgModifer);
    
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
