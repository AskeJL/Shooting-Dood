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

    String ID;

    public ProjectilePart(String ID) {
        this.ID = ID;
    }
    
    public String getID(){
        return ID;
    }
    
    public void setID(String ID){
        this.ID = ID;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
}