package shoot.doode.commonweapon;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;


public abstract class Weapon extends Entity {
    
    private double reloadTime = 2;
    private double damage = 2;

    public Weapon() {
    }
    
    abstract public void shoot(GameData gameData, World world);
    
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
