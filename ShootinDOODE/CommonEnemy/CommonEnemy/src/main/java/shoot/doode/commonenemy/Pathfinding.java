package shoot.doode.commonenemy;

import com.badlogic.gdx.math.Intersector;
import java.util.ArrayList;
import java.util.List;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.SpritePart;

/**
 *
 * @author sande
 */
public class Pathfinding {
    private List<Point> points = new ArrayList<>();
    private List<Rectangle> obstacles = new ArrayList<>();
    public AStarAlgorithm astar = new AStarAlgorithm();
    
    Graph graph = new Graph();
    
    private int lastEntityCount = 0;
    
    /*
     * Example of path map setup for A* algorithm (1 = available location, 2 = weight/cost)
     * ["1_1"] = [{"10_10", 10}, {"20_25", 12}],
     * ["10_10"] = [{"50_50", 21}],
     * ["20_25"] = [{"50_50", 14}]
     */
    
    public void setup(World world) {
        if(world.getEntities().size() == lastEntityCount) {
            //System.out.println("samme mængde entities som sidste tjek");
            return;
        }
        else {
            //System.out.println("ny mængde entities");
            lastEntityCount = world.getEntities().size();
        }
        
        System.out.println("Setting up pathfinding");
        
        points.clear();
        obstacles.clear();
        graph.getGraph().clear();
        
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
            int cornerRange = 30;
            Point topLeft = new Point(posPart.getX() - cEntity.getBoundaryWidth() - cornerRange, posPart.getY() + 30 + cornerRange);
            Point topRight = new Point(posPart.getX() + cEntity.getBoundaryWidth() + cornerRange, posPart.getY() + 30 + cornerRange);
            Point bottomLeft = new Point(posPart.getX() - cEntity.getBoundaryWidth() - cornerRange, posPart.getY() - cEntity.getBoundaryHeight() - cornerRange);
            Point bottomRight = new Point(posPart.getX() + cEntity.getBoundaryWidth() + cornerRange, posPart.getY() - cEntity.getBoundaryHeight() - cornerRange);
            
            //world.addEntity(createEnemy(topLeft.x, topLeft.y));
            //world.addEntity(createEnemy(topRight.x, topRight.y));
            //world.addEntity(createEnemy(bottomLeft.x, bottomLeft.y));
            //world.addEntity(createEnemy(bottomRight.x, bottomRight.y));
            
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
        ArrayList<Point> pathList = new ArrayList<>();
        
        /*if(hasLineOfSight(current, destination)) {
            System.out.println("Has line of sight!");
            
            pathList.add(destination);
            astar.setFinalPath(pathList);
            
            return pathList;
        }*/
        
        //System.out.println("No line of sight!");
        
        // 1. add current, destination to line of sight map
        // 2. pathList = call A* algorithm on map
        // 3. remove current, destination from line of sight map
        int closestCurrent = -1;
        Point bestCurrent = null;
        
        int closestDestination = -1;
        Point bestDestination = null;
        
        for(Point point : points) {
            int distanceCurrent = (int)Point.getDistance(current, point);
            int distanceDestination = (int)Point.getDistance(destination, point);
            
            if(closestCurrent == -1 || distanceCurrent < closestCurrent) {
                closestCurrent = distanceCurrent;
                bestCurrent = point;
            }
            if(closestDestination == -1 || distanceDestination < closestDestination) {
                closestDestination = distanceDestination;
                bestDestination = point;
            }
        }
        current = bestCurrent;
        destination = bestDestination;
        
        hasRectCollision(current);
        
        addLineOfSight(current);
        addLineOfSightToPoint(current);
        
        addLineOfSight(destination);
        addLineOfSightToPoint(destination);
        
        // TODO: Fix so current/destination cannot be inside of obstacle
        try {
            astar.aStarCostCalc(graph.getGraph(), current, destination);
        }
        catch(Exception ex) {
            System.out.println("exception på aStarCostCalc" + ex);
        }
        
        removeLineOfSightToPoint(current);
        removeLineOfSightToPoint(destination);
        
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
                String pointName = getPointName(point);
                int cost = distanceBetweenPoints(point, pathPoint);
                
                //System.out.println("Add line of sight: " + pathPointName + " -> " + pointName);
                
                graph.addEdge(pathPoint, point);
            }
        }
    }
    
    private void addLineOfSightToPoint(Point pointToAdd) {
        for(Point point : points) {
            if(point == pointToAdd) {
                continue;
            }
            
            if(hasLineOfSight(point, pointToAdd)) {
                String pointName = getPointName(point);
                int cost = distanceBetweenPoints(point, pointToAdd);
                
                //System.out.println("Adding line of sight to point: " + pointName + " -> " + pathPointName);
                
                graph.addEdge(point, pointToAdd);
            }
        }
    }
    
    private void removeLineOfSightToPoint(Point pointToRemove) {
        String pointToRemoveName = getPointName(pointToRemove);
        
        for(Point point : points) {
            if(point == pointToRemove) {
                continue;
            }
            
            String pointName = getPointName(point);
            graph.removeFromSight(pointToRemove);
        }
    }
    
    private boolean hasLineOfSight(Point point1, Point point2) {
        for(Rectangle obstacle : obstacles) {
            if(hasPointRectCollision(point1.x, point1.y, point2.x, point2.y,
                    obstacle.x - (obstacle.width / 2), obstacle.y - (obstacle.height / 2),
                    obstacle.x + obstacle.width, obstacle.y + obstacle.height)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean hasRectCollision(Point point) {
        Rectangle pointRect = new Rectangle(point.x - 1, point.y - 1, 2, 2);
        for(Rectangle obstacle : obstacles) {
            com.badlogic.gdx.math.Rectangle intersectRect = new com.badlogic.gdx.math.Rectangle();
            if(Intersector.intersectRectangles(new com.badlogic.gdx.math.Rectangle(pointRect.x, pointRect.y, pointRect.width, pointRect.height),
                    new com.badlogic.gdx.math.Rectangle(obstacle.x, obstacle.y, obstacle.width, obstacle.height), intersectRect)) {
                System.out.println("Intersection!");
            }
        }
        return false;
    }
    
    private boolean hasPointRectCollision(float x1, float y1, float x2, float y2, float minX, float minY, float maxX, float maxY) {
        if(x1 < x2 && hasLineCollision(x1, y1, x2, y2, minX, minY, minX, maxY)) {
            return true;
        }
        else if(x1 > x2 && hasLineCollision(x1, y1, x2, y2, maxX, minY, maxX, maxY)) {
            return true;
        }
        else if(y1 < y2 && hasLineCollision(x1, y1, x2, y2, minX, minY, maxX, minY)) {
            return true;
        }
        else {
            return y1 > y2 && hasLineCollision(x1, y1, x2, y2, minX, maxY, maxX, maxY);
        }
    }
    
    private boolean hasLineCollision(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        float v = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        float uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / v;
        float uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / v;
        
        return uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1;
    }
    
    private int distanceBetweenPoints(Point point1, Point point2) {
        return (int)Math.sqrt((point2.y - point1.y) * (point2.y - point1.y) +
                (point2.x - point1.x) * (point2.x - point1.x));
    }
    
    private String getPointName(Point point) {
        return String.valueOf((int)point.x) + "_" + String.valueOf((int)point.y);
    }
}
