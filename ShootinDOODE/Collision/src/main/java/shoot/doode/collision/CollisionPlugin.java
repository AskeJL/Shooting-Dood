package shoot.doode.collision;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.services.IGamePluginService;

/**
 *
 * @author sande
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class)})
public class CollisionPlugin implements IGamePluginService {

    private Entity obstacle;
    
    @Override
    public void start(GameData gameData, World world) {
        obstacle = createObstacle(gameData);
        world.addEntity(obstacle);
    }

    @Override
    public void stop(GameData gameData, World world) {
        
    }
    
    private Entity createObstacle(GameData gameData) {

        float x = 100;
        float y = 100;
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        String[] spritePaths = new String[1];
        spritePaths[0] = "Red_Virus.png";
        
        CollidableEntity entity = new CollidableEntity();
        entity.setRadius(30);
        entity.setColour(colour);
        entity.add(new PositionPart(x, y, radians));
        entity.add(new SpritePart("Player", spritePaths));
        
        entity.setBoundaryWidth(50);
        entity.setBoundaryHeight(50);
        entity.setIsStatic(true);

        return entity;
    }
}
