package shoot.doode.bulletsystem;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.TimerPart;
import shoot.doode.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.entityparts.ProjectileMovingPart;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class BulletControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            
            PositionPart position = bullet.getPart(PositionPart.class);
            ProjectileMovingPart moving = bullet.getPart(ProjectileMovingPart.class);
            TimerPart time = bullet.getPart(TimerPart.class);
            
            time.reduceExpiration(gameData.getDelta());
            LifePart life = bullet.getPart(LifePart.class);
            
            if (time.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
           
            position.process(gameData, bullet);
            moving.process(gameData, bullet);
            time.process(gameData, bullet);
            life.process(gameData, bullet);

        }
    }
    

}
