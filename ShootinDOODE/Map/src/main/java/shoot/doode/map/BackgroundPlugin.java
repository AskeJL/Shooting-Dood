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
import java.util.ArrayList;

/**
 *
 * @author askel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class BackgroundPlugin implements IGamePluginService {

    private String module = "Map";
    Entity background = new Background();
    private ArrayList<Entity> boundaryList = new ArrayList<>();
    private ArrayList<Entity> obstacleList = new ArrayList<>();

    @Override
    public void start(GameData gameData, World world) {
        background = createBackground(gameData);
        world.addEntity(background);

        float x;
        float y;
        int width;
        int height;
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                x = 35;
                y = 0;
                width = (40 * 35) * 2;
                height = 40;
                Entity boundary = createBoundary(gameData, x, y, width, height);
                boundaryList.add(boundary);
                world.addEntity(boundary); // works
                System.out.println("1 created");
            } else if (i == 1) {
                x = 0;
                y = 0;
                width = 40;
                height = (40 * 35) * 2;
                Entity boundary = createBoundary(gameData, x, y, width, height);
                boundaryList.add(boundary);
                world.addEntity(boundary); // Works

                System.out.println("2 created");
            } else if (i == 2) {
                x = 35;
                y = (36 * 35);
                width = (40 * 35) * 2;
                height = 60;
                Entity boundary = createBoundary(gameData, x, y, width, height);
                boundaryList.add(boundary);
                world.addEntity(boundary);
                System.out.println("3 created");
            } else if (i == 3) {
                x = (36 * 35);
                y = 0;
                width = 60;
                height = (40 * 35) * 2;
                Entity boundary = createBoundary(gameData, x, y, width, height);
                boundaryList.add(boundary);
                world.addEntity(boundary);
                System.out.println("4 created");
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

        CollidableEntity entity = new CollidableEntity();
        entity.add(new PositionPart(x, y, radians));

        entity.setBoundaryWidth(width);
        entity.setBoundaryHeight(height);
        entity.setIsStatic(true);

        return entity;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(background);
        for(Entity e : boundaryList)
        {
            world.removeEntity(e);
        }
    }
}
