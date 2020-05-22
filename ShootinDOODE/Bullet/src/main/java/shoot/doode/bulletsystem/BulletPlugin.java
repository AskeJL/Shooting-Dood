package shoot.doode.bulletsystem;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class BulletPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        world.addEntity(new BulletSpawner());
    }

    @Override
    public void stop(GameData gameData, World world) {
        
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }
        
        for (Entity e : world.getEntities()) {
            if (e.getClass() == BulletSpawner.class) {
                world.removeEntity(e);
            }
        }
        
    }

}
