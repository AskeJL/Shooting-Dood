package shoot.doode.commonenemy;

import java.util.ArrayList;
import java.util.List;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PositionPart;

/**
 *
 * @author sande
 */
public class Pathfinding {
    private List<Point> points = new ArrayList<>();
    private List<Rectangle> obstacles = new ArrayList<>();
    
    public void setup(World world) {        
        //System.out.println("Setting up pathfinding");
        
        points.clear();
        obstacles.clear();
        
        for(Entity entity : world.getEntities()) {
            // Get only collidable entities
            if (!CollidableEntity.class.isAssignableFrom(entity.getClass())) {
                continue;
            }
            
            CollidableEntity cEntity = (CollidableEntity)entity;
            if(!cEntity.getIsStatic()) {
                continue;
            }
            
            //System.out.println("Collidable: " + cEntity);
            
            PositionPart posPart = cEntity.getPart(PositionPart.class);
            
            Rectangle obstacle = new Rectangle(posPart.getX(), posPart.getY(), cEntity.getBoundaryWidth(), cEntity.getBoundaryHeight());
            obstacles.add(obstacle);
            
            // Build list of corners around obstacles
            int cornerRange = 10;
            Point topLeft = new Point(posPart.getX() - cornerRange, posPart.getY() - cornerRange);
            Point topRight = new Point(posPart.getX() + cEntity.getBoundaryWidth() + cornerRange, posPart.getY() - cornerRange);
            Point bottomLeft = new Point(posPart.getX() - cornerRange, posPart.getY() + cEntity.getBoundaryHeight() + cornerRange);
            Point bottomRight = new Point(posPart.getX() + cEntity.getBoundaryWidth() + cornerRange, posPart.getY() + cEntity.getBoundaryHeight() + cornerRange);
            
            points.add(topLeft);
            points.add(topRight);
            points.add(bottomLeft);
            points.add(bottomRight);
        }
        
        for(Point point : points) {
            addLineOfSight(point);
        }
    }
    
    public List<Point> generatePath(Point current, Point destination) {
        List<Point> pathList = new ArrayList<>();
        
        if(hasLineOfSight(current, destination)) {
            pathList.add(destination);
            return pathList;
        }
        
        // 1. add current, destination to line of sight map
        // 2. pathList = call A* algorithm on map
        // 3. remove current, destination from line of sight map
        
        return pathList;
    }
    
    private void addLineOfSight(Point pathPoint) {
        // Build map with each point that has the given point in line of sight
        // The map can then be given directly to an A* algorithm to find path from A to B
        
        for(Point point : points) {
            if(point == pathPoint) {
                continue;
            }
            
            if(hasLineOfSight(point, pathPoint)) {
                // Insert into map, sightMap[point].add(pathPoint)
            }
        }
    }
    
    private boolean hasLineOfSight(Point point1, Point point2) {
        for(Rectangle obstacle : obstacles) {
            // if(Perform Line / Rectangle collision detection) {
            //  return false;
            // }
        }
        return true;
    }
}
