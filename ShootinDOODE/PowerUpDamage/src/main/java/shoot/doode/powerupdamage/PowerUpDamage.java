/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.powerupdamage;

import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.services.IGamePluginService;
import shoot.doode.common.services.IPowerUp;

/**
 *
 * @author tobia
 */
@ServiceProviders(value={
    @ServiceProvider(service=IPowerUp.class),
    @ServiceProvider(service=IGamePluginService.class)}
)
public class PowerUpDamage extends CollidableEntity implements IPowerUp, IGamePluginService{
      
    @Override
    public void spawnPowerUp(GameData gameData, World world) {
        Entity powerUp = createPowerUp(gameData);
        world.addEntity(powerUp);
    }

    @Override
    public void applyPowerUp(Entity entity) {
        ShootingPart shootingPart = entity.getPart(ShootingPart.class);
        shootingPart.setDamageModifier(shootingPart.getDamageModifier()+0.2);
        
    }
    
    private Entity createPowerUp(GameData gameData) {
        
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 0;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;
        String module = "PowerUpDamage";
        String[] spritePaths = new String[1];
        spritePaths[0] = "ham.png";
        
        int boundaryWidth = 20;
        int boundaryHeight = 20;
        CollidableEntity powerUp = new PowerUpDamage();
        
        powerUp.setRadius(8);
        powerUp.add(new PositionPart(x, y, radians));
        powerUp.add(new SpritePart(module,spritePaths));
        
        powerUp.setBoundaryWidth(boundaryWidth);
        powerUp.setBoundaryHeight(boundaryHeight);
       
        return powerUp;
    }

    @Override
    public void start(GameData gameData, World world) {
        
    }

    @Override
    public void stop(GameData gameData, World world) {
        removePowerUps(gameData, world);
    }

    @Override
    public void removePowerUps(GameData gameData, World world) {
        for(Entity e : world.getEntities(PowerUpDamage.class))
        {
            world.removeEntity(e);
        }
    }
    
}