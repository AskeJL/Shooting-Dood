/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.map;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.MapPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.services.IGamePluginService;

/**
 *
 * @author askel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class BackgroundPlugin implements IGamePluginService  {
    Entity background = new Background();
    private Entity boundary;

    @Override
    public void start(GameData gameData, World world) {
        background = createBackground(gameData);
        world.addEntity(background);
        float x;
        float y;
        int width;
        int height;
        for(int i = 0; i<4; i++){
            switch (i){
                case 0:
                    x = 35;
                    y = 0;
                    width = (40*35)*2;
                    height = 0;
                    boundary = createBoundary(gameData, x, y, width, height);
                    world.addEntity(boundary); // works
                case 1:
                    x = 35;
                    y = 0;
                    width = 0;
                    height = (40*35)*2;
                    boundary = createBoundary(gameData, x, y, width, height);
                    world.addEntity(boundary); // Works
                case 2:
//                    x = 35;
//                    y = (40*35)*2;
//                    width = (40*35)*2;
//                    height = 0;
//                    boundary = createBoundary(gameData, x, y, width, height);
//                    world.addEntity(boundary);
                case 3:
//                    x = (40*35)*2;
//                    y = 0;
//                    width = 0;
//                    height = (40*35)*2;
//                    boundary = createBoundary(gameData, x, y, width, height);
//                    world.addEntity(boundary);
            }
        }
    }
    
    private Entity createBackground(GameData gameData) {
        String module = "Map";
        String[] mapPaths = new String[1];
        mapPaths[0] = "map.tmx";
        
        Entity map = new Background();
        map.add(new MapPart(module, mapPaths));
        
        return map;
    }
    private Entity createBoundary(GameData gameData, float x, float y, int width, int height) {
        
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;
        
        CollidableEntity entity = new CollidableEntity();
        entity.setRadius(1);
        entity.setColour(colour);
        entity.add(new PositionPart(x, y, radians));
        
        entity.setBoundaryWidth(width);
        entity.setBoundaryHeight(height);
        entity.setIsStatic(true);

        return entity;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(background);
    }  
}
