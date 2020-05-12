/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.map;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.MapPart;
import shoot.doode.common.services.IGamePluginService;

/**
 *
 * @author askel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class BackgroundPlugin implements IGamePluginService  {
    Entity background = new Background();

    @Override
    public void start(GameData gameData, World world) {
        Entity background = createBackground(gameData);
        world.addEntity(background);
    }
    
    private Entity createBackground(GameData gameData) {
        String module = "Map";
        String[] spritePaths = new String[1];
        spritePaths[0] = "map.tmx";
        
        Entity map = new Background();
        map.add(new MapPart(module, spritePaths));
        
        return map;
    }

    @Override
    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
