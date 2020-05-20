/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;

/**
 *
 * @author rasmu
 */
public class ProjectilePart implements EntityPart{

    private double damege;
    
    public ProjectilePart(double damege) {
        this.damege = damege;
    }
    
    public double getDamage(){
        return this.damege;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
}
