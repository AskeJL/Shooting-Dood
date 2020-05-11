/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.powerups;

import java.util.Random;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.GameKeys;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.commonpowerup.PowerUp;
import shoot.doode.commonpowerup.PowerUpType;

/**
 *
 * @author askel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class PowerUpControlSystem implements IEntityProcessingService{
    private double timer = 0;

    @Override
    public void process(GameData gameData, World world) {
        timer += gameData.getDelta();
        if(timer > 5)
        {
            for(Entity e : world.getEntities(PowerUp.class))
            {
                world.removeEntity(e);
            }
            Entity powerUp = PowerUp(gameData);
            world.addEntity(powerUp);
            timer = 0;
        }
        
        
    }
    
    private Entity PowerUp(GameData gameData) {
        
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;
        String module = "PowerUps";
        String[] spritePaths = new String[1];
        double ran = Math.random();
        
        int boundaryWidth = 20;
        int boundaryHeight = 20;
        CollidableEntity powerUp;
        if(ran > 0.75)
        {
            powerUp = new PowerUp(PowerUpType.DAMAGE);
            spritePaths[0] = "ham.png";
        }
        else if(ran > 0.50)
        {
            powerUp = new PowerUp(PowerUpType.INVULNERABILITY);
            spritePaths[0] = "heart.png";
        }
        else if(ran > 0.25)
        {
            powerUp = new PowerUp(PowerUpType.MOVEMENTSPEED);
            spritePaths[0] = "banana.png";
        }
        else
        {
            powerUp = new PowerUp(PowerUpType.REALOAD);
            spritePaths[0] = "apple.png";
        }
        
        
        powerUp.setRadius(8);
        powerUp.setColour(colour);
        powerUp.add(new PositionPart(x, y, radians));
        powerUp.add(new SpritePart(module,spritePaths));
        
        powerUp.setBoundaryWidth(boundaryWidth);
        powerUp.setBoundaryHeight(boundaryHeight);
       
        return powerUp;
    }
    
}
