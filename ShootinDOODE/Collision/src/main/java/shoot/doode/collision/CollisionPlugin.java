package shoot.doode.collision;

import java.util.ArrayList;
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

    private String module = "Collision";
    private ArrayList<Entity> obstacles;
//    private Entity obstacle1;
//    private Entity obstacle2;
//    private Entity obstacle3;
//    private Entity obstacle4;
//    private Entity obstacle5;

    @Override
    public void start(GameData gameData, World world) {
        obstacles = new ArrayList<Entity>();
        for (int i = 0; i < 10; i++) {
            obstacles.add(i, createObstacle(gameData));
        }
        for (Entity obstacle : obstacles) {
            world.addEntity(obstacle);
        };
        
        

    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    private Entity createObstacle(GameData gameData) {

        float x = (float) Math.random() * 700;
        float y = (float) Math.random() * 500;
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        String[] spritePaths = new String[1];
        spritePaths[0] = "rsz_oa_bench.png";

        CollidableEntity entity = new CollidableEntity();
        entity.setRadius(15);
        entity.setColour(colour);
        entity.add(new PositionPart(x, y, radians));
        entity.add(new SpritePart(module, spritePaths));

        entity.setBoundaryWidth(30);
        entity.setBoundaryHeight(30);
        entity.setIsStatic(true);

        return entity;
    }
}
