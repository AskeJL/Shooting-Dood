package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;


public class ShootingPart implements EntityPart{
    
    private double damageModifier = 1;
    private double reloadModifier = 1;
    private boolean switchWeapon = false;
    private Entity weapon;
    private boolean isSchooting;
    private String ID;

    public ShootingPart(String ID) {
        this.ID = ID;
    }
    
    public Entity getWeapon() {
        return weapon;
    }
    
    public void setDamageModifier(double damageModifier)
    {
        this.damageModifier = damageModifier;
    }
    
    public void setReloadModifier(double reloadModifier)
    {
        this.reloadModifier = reloadModifier;
    }
    
    public double getReloadModifier()
    {
        return reloadModifier;
    }
    
    public double getDamageModifier()
    {
        return damageModifier;
    }
    
    public void setSwitchWeapon(boolean switchWeapon)
    {
        this.switchWeapon = switchWeapon;
    }
    
    public boolean isSwitchWeapon()
    {
        return switchWeapon;
    }
    
    public void setWeapon(Entity weapon) {
        this.weapon = weapon;
    }
    
    public boolean isShooting(){
        return this.isSchooting;
    }
    
    public void setIsShooting(boolean b){
        this.isSchooting = b;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
    
}
