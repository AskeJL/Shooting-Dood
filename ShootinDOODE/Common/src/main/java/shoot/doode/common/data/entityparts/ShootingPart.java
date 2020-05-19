package shoot.doode.common.data.entityparts;

import java.util.ArrayList;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;


public class ShootingPart implements EntityPart{
    
    private boolean usesWeapons;
    private double damageModifier = 1;
    private double reloadModifier = 1;
    private boolean switchWeapon = false;
    private ArrayList<Entity> weapons = new ArrayList<>();
    private int wepaonEquiped = 0;
    private boolean isSchooting;
    private String ID;

    public ShootingPart(String ID,boolean usesWeapons) {
        this.ID = ID;
        this.usesWeapons = usesWeapons;
    }
    
    public int getWeaponAmount()
    {
       return weapons.size();
    }
    
    public boolean isUsesWeapons()
    {
        return usesWeapons;
    }
    
    public Entity getWeapon() {
        return weapons.get(wepaonEquiped);
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
    
    public void addWeapon(Entity weapon) {
        weapons.add(weapon);
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
    
    public void emptyWeapons()
    {
        weapons.clear();
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        if(switchWeapon && !weapons.isEmpty())
        {
            int size = weapons.size();
            wepaonEquiped++;
            wepaonEquiped = wepaonEquiped%size;
        }
        for(int i = 0; i < weapons.size(); i++)
        {
            Entity e = weapons.get(i);
            SpritePart spritePart = e.getPart(SpritePart.class);
            if(wepaonEquiped == i)
            {
                spritePart.setInvis(false);
            }
            else
            {
                spritePart.setInvis(true);
            }
        }
        
    }
    
    
}
