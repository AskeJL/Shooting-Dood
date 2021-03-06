package shoot.doode.enemy;


import java.util.Random;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.commonenemy.Pathfinding;
import shoot.doode.commonenemy.Point;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class EnemyControlSystem implements IEntityProcessingService{

    private Pathfinding pathfinding = new Pathfinding();
    private long lastPathGeneration = 0;
    private int numberToSpawn= 0;
    
    @Override
    public void process(GameData gameData, World world) {
        // If new obstacles are loaded, this needs to be called again
        // Calling it here is not the most performant method...
        
        
        if(world.getEntities(NormalEnemy.class).size() < 1)
        {
            for(int i = 0; i < numberToSpawn;i++)
            {
                Entity enemy = createNormalEnemy(gameData);
                world.addEntity(enemy);
            }
            numberToSpawn++;
        }
        
        for (Entity enemy : world.getEntities(NormalEnemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            
            // Get the player entity
            Entity playerEntity = null;
            for(Entity entity : world.getEntities()) {
                if(entity.getClass().getName().contains(".Player")) {
                    playerEntity = entity;
                    break;
                }
            }
            
            if(playerEntity != null) {
                PositionPart playerPositionPart = playerEntity.getPart(PositionPart.class);

                long current = System.currentTimeMillis();
                if(current - lastPathGeneration > 200) {
                    pathfinding.setup(world);
                    pathfinding.generatePath(new Point(positionPart.getX(), positionPart.getY()),
                            new Point(playerPositionPart.getX(), playerPositionPart.getY()));
                    lastPathGeneration = System.currentTimeMillis();
                }

                Point nextPoint = pathfinding.astar.getNextPoint();
                if(nextPoint.getX() != 0 && nextPoint.getY() != 0) {
                    if(Point.getDistance(nextPoint, new Point(positionPart.getX(), positionPart.getY())) < 5) {
                        pathfinding.astar.pointReached();
                    }
                    else {                
                        movingPart.setDestination(nextPoint.getX(), nextPoint.getY());
                    }
                }
            }

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);
        }
    }

    private Entity createNormalEnemy(GameData gameData) {
        
        float maxSpeed = 100;
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        String module = "Enemy";
        String[] spritePaths = new String[1];
        double ran = Math.random();
        
        if(ran > 0.50)
        {
            spritePaths[0] = "Green_Virus.png";
        }
        else
        {
            spritePaths[0] = "Red_Virus.png"; //"Red_virus.png"; (file not found exception)
        }
        
        CollidableEntity enemy = new NormalEnemy(50);
        enemy.add(new MovingPart(maxSpeed));
        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new LifePart(1));
        enemy.add(new SpritePart(module,spritePaths));
        enemy.setBoundaryWidth(40);
        enemy.setBoundaryHeight(40);
       
        return enemy;
    }
    
}
