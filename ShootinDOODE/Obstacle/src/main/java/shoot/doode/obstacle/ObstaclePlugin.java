package shoot.doode.obstacle;

import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.services.IGamePluginService;

/**
 *
 * @author sande
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class)})
public class ObstaclePlugin implements IGamePluginService {

    private String module = "Obstacle";
    
    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i<7; i++){
        Entity obstacle = createObstacle(gameData);
        world.addEntity(obstacle);}
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(int i = 0; i < world.getEntities(Obstacles.class).size();i++)
        {
            world.removeEntity(world.getEntities(Obstacles.class).get(i));
        }
        
    }
    
    private Entity createObstacle(GameData gameData) {

        float x = new Random().nextFloat() * (38*35);
        float y = new Random().nextFloat() * (38*35);
        float radians = 3.1415f / 2;

        String[] spritePaths = new String[1];
        double ran = Math.random();
        
        int boundaryWidth;
        int boundaryHeight;
        if(ran > 0.80)
        {
            spritePaths[0] = "JeepB.png";
            boundaryWidth = 42;
            boundaryHeight = 83;
        }
        else if(ran > 0.60)
        {
            spritePaths[0] = "GalardB.png";
            boundaryWidth = 35;
            boundaryHeight = 63;
        }
        else if(ran > 0.40)
        {
            spritePaths[0] = "BuickerB.png";
            boundaryWidth = 33;
            boundaryHeight = 74;
        }
        else if(ran > 0.20)
        {
            spritePaths[0] = "RamB.png";
            boundaryWidth = 39;
            boundaryHeight = 87;
        }
        else {
            spritePaths[0] = "SuperB.png";
            boundaryWidth = 35;
            boundaryHeight = 65;
        }
        
        CollidableEntity obstacle = new Obstacles();
        obstacle.add(new PositionPart(x, y, radians));
        obstacle.add(new SpritePart(module,spritePaths));
        
        obstacle.setBoundaryWidth(boundaryWidth);
        obstacle.setBoundaryHeight(boundaryHeight);
        obstacle.setIsStatic(true);
       
        return obstacle;
    }
}
