package shoot.doode.enemyshoot;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

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
