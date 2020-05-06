/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.enemy;

import java.util.Random;
import shoot.doode.commonenemy.Enemy;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.services.IGamePluginService;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.entityparts.SpritePart;

/**
 *
 * @author askel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class EnemyPlugin implements IGamePluginService {
     private Entity enemy = new Enemy();

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        Entity enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        
        float deacceleration = 10;
        float acceleration = 150;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;

        String module = "Enemy";
        String[] spritePaths = new String[1];
        double ran = Math.random();
        
        int boundaryWidth = 20;
        int boundaryHeight = 20;
        if(ran > 0.75)
        {
            spritePaths[0] = "Enemy-front.png";
        }
        else if(ran > 0.50)
        {
            spritePaths[0] = "Enemy2-front.png";
        }
        else if(ran > 0.25)
        {
            spritePaths[0] = "Green_Virus.png";
            boundaryWidth = 45;
            boundaryHeight = 45;
        }
        else
        {
            spritePaths[0] = "Red_Virus.png";
            boundaryWidth = 45;
            boundaryHeight = 45;
        }
        
        CollidableEntity enemy = new Enemy();
        enemy.setRadius(8);
        enemy.setColour(colour);
        enemy.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new LifePart(1));
        enemy.add(new SpritePart(module,spritePaths));
        
        enemy.setBoundaryWidth(boundaryWidth);
        enemy.setBoundaryHeight(boundaryHeight);
       
        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(int i = 0; i < world.getEntities(Enemy.class).size();i++)
        {
            world.removeEntity(world.getEntities(Enemy.class).get(i));
        }
    }

}
