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

        points.clear();
        obstacles.clear();
        graph.getGraph().clear();

        for (Entity entity : world.getEntities()) {
            // Get only collidable entities
            if (!CollidableEntity.class.isAssignableFrom(entity.getClass())) {
                continue;
            }

            CollidableEntity cEntity = (CollidableEntity) entity;
            if (!cEntity.getIsStatic()) {
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

        for (Point point : points) {
            addLineOfSight(point);
        }
    }

    public List<Point> generatePath(Point current, Point destination) {
        ArrayList<Point> pathList = new ArrayList<>();

        if (hasLineOfSight(current, destination)) {

            pathList.add(destination);
            astar.setFinalPath(pathList);

            return pathList;
        }
        //System.out.println("No line of sight!");
        // 1. add current, destination to line of sight map
        // 2. pathList = call A* algorithm on map
        // 3. remove current, destination from line of sight map

        addLineOfSight(current);
        addLineOfSightToPoint(current);

        addLineOfSight(destination);
        addLineOfSightToPoint(destination);

        try {
            for (Rectangle obstacle : obstacles) {
                if (obstacle.containsPoint(current.x, current.y)) {
                    current.setX((int) (obstacle.x - obstacle.width));
                    current.setY((int) (obstacle.y - obstacle.height));
                }
                if (obstacle.containsPoint(destination.x, destination.y)) {
                    destination.setX((int) (obstacle.x - obstacle.width));
                    destination.setY((int) (obstacle.y - obstacle.height));
                }
            }
            astar.generatePath(graph.getGraph(), current, destination);

        } catch (Exception ex) {
            System.out.println("exception på generatePath" + ex);
        }

        removeLineOfSightToPoint(current);
        removeLineOfSightToPoint(destination);

        return pathList;
    }

    private void addLineOfSight(Point pathPoint) {
        // Build map with each point that has the given point in line of sight
        // The map can then be given directly to an A* algorithm to find path from A to B

        for (Point point : points) {
            if (point == pathPoint) {
                continue;
            }

            if (hasLineOfSight(point, pathPoint)) {
                String pointName = getPointName(point);
                int cost = distanceBetweenPoints(point, pathPoint);

                //System.out.println("Add line of sight: " + pathPointName + " -> " + pointName);
                graph.addEdge(pathPoint, point);
            }
        }
    }

    private void addLineOfSightToPoint(Point pointToAdd) {
        for (Point point : points) {
            if (point == pointToAdd) {
                continue;
            }

            if (hasLineOfSight(point, pointToAdd)) {
                String pointName = getPointName(point);
                int cost = distanceBetweenPoints(point, pointToAdd);

                //System.out.println("Adding line of sight to point: " + pointName + " -> " + pathPointName);
                graph.addEdge(point, pointToAdd);
            }
        }
    }

    private void removeLineOfSightToPoint(Point pointToRemove) {
        String pointToRemoveName = getPointName(pointToRemove);

        for (Point point : points) {
            if (point == pointToRemove) {
                continue;
            }

            String pointName = getPointName(point);
            graph.removeFromSight(pointToRemove);
        }
    }

    private boolean hasLineOfSight(Point point1, Point point2) {
        for (Rectangle obstacle : obstacles) {
            if (hasPointRectCollision(point1.x, point1.y, point2.x, point2.y,
                    obstacle.x - (obstacle.width / 2), obstacle.y - (obstacle.height / 2),
                    obstacle.x + obstacle.width, obstacle.y + obstacle.height)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasPointRectCollision(float x1, float y1, float x2, float y2, float minX, float minY, float maxX, float maxY) {
        if (x1 < x2 && hasLineCollision(x1, y1, x2, y2, minX, minY, minX, maxY)) {
            return true;
        } else if (x1 > x2 && hasLineCollision(x1, y1, x2, y2, maxX, minY, maxX, maxY)) {
            return true;
        } else if (y1 < y2 && hasLineCollision(x1, y1, x2, y2, minX, minY, maxX, minY)) {
            return true;
        } else {
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
        return (int) Math.sqrt((point2.y - point1.y) * (point2.y - point1.y)
                + (point2.x - point1.x) * (point2.x - point1.x));
    }

    private String getPointName(Point point) {
        return String.valueOf((int) point.x) + "_" + String.valueOf((int) point.y);
    }
}
