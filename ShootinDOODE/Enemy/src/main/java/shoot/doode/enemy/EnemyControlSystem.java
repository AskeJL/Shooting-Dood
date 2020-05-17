/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.enemy;

import java.util.List;
import java.util.Random;
import shoot.doode.commonenemy.Enemy;
import shoot.doode.commonenemy.AI;
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
import shoot.doode.commonenemy.Pathfinding;
import shoot.doode.commonenemy.Point;

/**
 *
 * @author askel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class EnemyControlSystem implements IEntityProcessingService, AI {

    private Pathfinding pathfinding = new Pathfinding();
    
    @Override
    public void process(GameData gameData, World world) {
        // If new obstacles are loaded, this needs to be called again
        // Calling it here is not the most performant method...
        pathfinding.setup(world);
        
        if(world.getEntities(Enemy.class).size() == 0)
        {
            Entity enemy = createEnemy(gameData);
            world.addEntity(enemy);
        }
        
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            Random rand = new Random();

            float rng = rand.nextFloat();

            if (rng > 0.1f && rng < 0.9f) {
                movingPart.setUp(true);
            }

            if (rng < 0.2f) {
                movingPart.setLeft(true);
            }

            if (rng > 0.8f) {
                movingPart.setRight(true);
            }
            List<Point> path = pathfinding.generatePath(new Point(positionPart.getX(), positionPart.getY()), new Point(10, 10));
            //movingPart.setDestination(path.get(0).x, path.get(0).y);

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);

            updateShape(enemy);

            movingPart.setRight(false);
            movingPart.setLeft(false);
            movingPart.setUp(false);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRotation();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    @Override
    public void AI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Entity createEnemy(GameData gameData) {
        
        float deacceleration = 10;
        float acceleration = 150;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;
        String module = "Enemy";
        String[] spritePaths = new String[1];
        double ran = Math.random();
        
        int boundaryWidth = 20;
        int boundaryHeight = 20;
        if(ran > 0.75)
        {
            spritePaths[0] = "Enemy-front.png";
        }
        else if(ran > 0.50)
        {
            spritePaths[0] = "Enemy2-front.png";
        }
        else if(ran > 0.25)
        {
            spritePaths[0] = "Green_Virus.png";
            boundaryWidth = 45;
            boundaryHeight = 45;
        }
        else
        {
            spritePaths[0] = "Green_Virus.png"; //"Red_virus.png"; (file not found exception)
            boundaryWidth = 45;
            boundaryHeight = 45;
        }
        
        CollidableEntity enemy = new Enemy();
        enemy.setRadius(8);
        enemy.setColour(colour);
        enemy.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new LifePart(1));
        enemy.add(new SpritePart(module,spritePaths));
        
        enemy.setBoundaryWidth(boundaryWidth);
        enemy.setBoundaryHeight(boundaryHeight);
       
        return enemy;
    }
    
}
