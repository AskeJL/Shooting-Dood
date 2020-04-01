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
public class AssetPart implements EntityPart {
    String module;
    String[] assetPaths;
    int currentAsset = 0;
    //**Creates an AssetPart, which alows the entity to have images showed that represent it
    public AssetPart(String module,String[] assetPaths)
    {
        this.module = module;
        this.assetPaths = assetPaths;
    }
    
    //** Sets which asset that should be showed by the draw method 
    public void setCurrentAsset(int currentAsset)
    {
        this.currentAsset = currentAsset;
    }
    
    public String[] getAssetPaths()
    {
        return assetPaths;
    }
    
    public String getAssetPath()    
    {
        return assetPaths[currentAsset];
    }
    
    public String getModule()
    {
        return module;
    }
    
    
    public void process(GameData gameData, Entity entity) {
    }
}
