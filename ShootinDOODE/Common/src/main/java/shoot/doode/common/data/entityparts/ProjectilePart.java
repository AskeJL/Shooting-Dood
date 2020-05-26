package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;

public class ProjectilePart implements EntityPart{

    private double damage;
    
    public ProjectilePart(double damege) {
        this.damage = damege;
    }
    
    public double getDamage(){
        return this.damage;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
}
