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

    private String ID;
    private double damege;
    
    public ProjectilePart(String ID,double damege) {
        this.ID = ID;
        this.damege = damege;
    }
    
    public String getID(){
        return ID;
    }
    
    public void setID(String ID){
        this.ID = ID;
    }
    
    public double getDamage(){
        return this.damege;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
}
