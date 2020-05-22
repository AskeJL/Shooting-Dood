/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.enemyshoot;

import java.util.Random;
import shoot.doode.commonenemy.Enemy;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.services.IGamePluginService;
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
public class ShootinEnemyPlugin implements IGamePluginService {

    public ShootinEnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

    }
 
    @Override
    public void stop(GameData gameData, World world) {
        for(Entity e : world.getEntities(ShootinEnemy.class))
        {
            world.removeEntity(e);
        }
    }

}
