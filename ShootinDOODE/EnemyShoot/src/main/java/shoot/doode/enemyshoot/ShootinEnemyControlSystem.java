/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.enemyshoot;

import java.util.List;
import java.util.Random;
import java.util.UUID;
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
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.commonenemy.AStarAlgorithm;
import shoot.doode.commonenemy.Pathfinding;
import shoot.doode.commonenemy.Point;

/**
 *
 * @author askel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class ShootinEnemyControlSystem implements IEntityProcessingService, AI {

    private Pathfinding pathfinding = new Pathfinding();
    private long lastPathGeneration = 0;
    private int numberToSpawn = 0;
    
    
    @Override
    public void process(GameData gameData, World world) {
        // If new obstacles are loaded, this needs to be called again
        // Calling it here is not the most performant method...
        pathfinding.setup(world);
        
        if(world.getEntities(ShootinEnemy.class).size() < 1)
        {
            for(int i = 0; i < numberToSpawn;i++)
            {
                Entity enemy = createShootinEnemy(gameData);
                world.addEntity(enemy);
            }
            numberToSpawn++;
        }
        
        for (Entity enemy : world.getEntities(ShootinEnemy.class)) {
            ShootinEnemy shootinEnemy = (ShootinEnemy)enemy;
            PositionPart positionPart = shootinEnemy.getPart(PositionPart.class);
            MovingPart movingPart = shootinEnemy.getPart(MovingPart.class);
            LifePart lifePart = shootinEnemy.getPart(LifePart.class);
            ShootingPart shootingPart = shootinEnemy.getPart(ShootingPart.class);
            
            Random rand = new Random();
            
            float rng2 = rand.nextFloat();
            if(rng2 > 0.97f)
            {
                shootingPart.setIsShooting(true);
            }
            else
            {
                shootingPart.setIsShooting(false);
            }
            
            
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
                if(current - lastPathGeneration > 2000) {
                    System.out.println("laver path");
                    pathfinding.generatePath(new Point(positionPart.getX(), positionPart.getY()),
                            new Point(playerPositionPart.getX(), playerPositionPart.getY()));
                    lastPathGeneration = System.currentTimeMillis();
                }

                Point nextPoint = pathfinding.astar.getNextPoint();
                if(nextPoint.getX() != 0 && nextPoint.getY() != 0) {
                    if(Point.getDistance(nextPoint, new Point(positionPart.getX(), positionPart.getY())) < 5) {
                        //System.out.println("Point reached");
                        pathfinding.astar.pointReached();
                    }
                    else {                
                        //System.out.println("Moving towards: " + nextPoint.getX() + ", " + nextPoint.getY());
                        movingPart.setDestination(nextPoint.getX(), nextPoint.getY());
                    }
                }
            }

            if(shootingPart.isShooting())
            {
                shootinEnemy.shoot(world);
            }
            
            movingPart.process(gameData, shootinEnemy);
            positionPart.process(gameData, shootinEnemy);
            lifePart.process(gameData, shootinEnemy);

            movingPart.setRight(false);
            movingPart.setLeft(false);
            movingPart.setUp(false);
        }
    }


    @Override
    public void AI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Entity createShootinEnemy(GameData gameData) {
        
        float deacceleration = 10;
        float acceleration = 150;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        String module = "EnemyShoot";
        String[] spritePaths = new String[1];
        double ran = Math.random();
        
        if(ran > 0.50)
        {
            spritePaths[0] = "Enemy-front.png";
        }
        else
        {
            spritePaths[0] = "Enemy2-front.png";
        }

        
        CollidableEntity enemy = new ShootinEnemy(50);
        enemy.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemy.add(new PositionPart(x, y, radians));
        enemy.add(new LifePart(1));
        enemy.add(new SpritePart(module,spritePaths));
        enemy.add(new ShootingPart(false));
        enemy.setBoundaryWidth(20);
        enemy.setBoundaryHeight(20);
       
        return enemy;
    }
    
}
