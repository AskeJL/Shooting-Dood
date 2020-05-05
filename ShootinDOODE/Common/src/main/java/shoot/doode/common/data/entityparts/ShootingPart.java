package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;


public class ShootingPart implements EntityPart{
    
    private Entity weapon;
    private boolean isSchooting;
    private String ID;

    public ShootingPart(String ID) {
        this.ID = ID;
    }
    
    public Entity getWeapon() {
        return weapon;
    }
    
    public void setWeapon(Entity weapon) {
        this.weapon = weapon;
    }
    
    public boolean isShooting(){
        return this.isSchooting;
    }
    
    public void setIsShooting(boolean b){
        this.isSchooting = b;
        //System.out.println("is shooting" + b);
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
