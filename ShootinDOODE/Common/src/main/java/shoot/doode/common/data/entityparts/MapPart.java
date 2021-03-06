package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;

public class MapPart implements EntityPart {
    String module;
    String[] MapPaths;
    int currentMap = 0;
    
    //Creates an SpritePart, which alows the entity to have images showed that represent it
    public MapPart(String module,String[] MapPaths)
    {
        this.module = module;
        this.MapPaths = MapPaths;
    }
    
    //Sets which asset that should be showed by the draw method 
    public void setCurrentMap(int currentMap)
    {
        this.currentMap = currentMap;
    }
    
    public String[] getMapPaths()
    {
        return MapPaths;
    }
    
    public String getMapPath()    
    {
        return MapPaths[currentMap];
    }
    
    public String getModule()
    {
        return module;
    }
    
    
    public void process(GameData gameData, Entity entity) {
    }
}
    

