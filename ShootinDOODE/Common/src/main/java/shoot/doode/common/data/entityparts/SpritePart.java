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
 * @author tobia
 */
public class SpritePart implements EntityPart {
    String module;
    String[] spritePaths;
    int currentSprite = 0;
    //**Creates an SpritePart, which alows the entity to have images showed that represent it
    public SpritePart(String module,String[] spritePaths)
    {
        this.module = module;
        this.spritePaths = spritePaths;
    }
    
    //** Sets which asset that should be showed by the draw method 
    public void setCurrentSprite(int currentSprite)
    {
        this.currentSprite = currentSprite;
    }
    
    public String[] getSpritePaths()
    {
        return spritePaths;
    }
    
    public String getSpritePath()    
    {
        return spritePaths[currentSprite];
    }
    
    public String getModule()
    {
        return module;
    }
    
    
    public void process(GameData gameData, Entity entity) {
    }
}
