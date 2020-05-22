package shoot.doode.commonenemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author sander
 */
public class AStarAlgorithm {

    private ArrayList<Point> finalPath = new ArrayList<>();
    private Point lastGoal = new Point(0, 0);

    public Point getNextPoint() {
        if (finalPath.isEmpty()) {

            return new Point(0, 0);
        }
        return finalPath.get(finalPath.size() - 1);
    }

    public void pointReached() {
        if (finalPath.isEmpty()) {
            return;
        }
        finalPath.remove(finalPath.size() - 1);
    }

    public ArrayList<Point> getFinalPath() {
        return finalPath;
    }

    public void setFinalPath(ArrayList<Point> path) {
        finalPath = path;
    }

    private int getDistance(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }
//
    public ArrayList<Point> getNeighbors(ArrayList<ArrayList<Point>> adjList, Point curPoint) {
        for (int i = 0; i < adjList.size(); i++) {
            if (Point.comparePoints(adjList.get(i).get(0), curPoint)) {
                return adjList.get(i);
            }
        }
        return null;
    }
//Generates an optimal path from the enemy object to the player object. 
    public int generatePath(ArrayList<ArrayList<Point>> adjList, Point start, Point goal) {
        //Check if the destination of the last generated path is the same as the destination of this call, dont run again
        if (Point.comparePoints(goal, lastGoal)) {
            return 0;
        }
        lastGoal = goal;
        //We use a priority queue to store the neighbors of the explored points since 
        //it will sort the lowest element to the front of the queue on adding
        PriorityQueue<Point> openPoints = new PriorityQueue<>();
        HashMap<Point, Double> costSoFar = new HashMap<Point, Double>();
        HashMap<Point, Point> cameFrom = new HashMap<Point, Point>();

        openPoints.add(start);
        costSoFar.put(start, 0.0);
        cameFrom.put(start, start);
        
        //As long as there are open points, get the first one in the queue.
        while (!openPoints.isEmpty()) {
            Point curPoint = openPoints.poll();
            // If this point is the same as the goal, then the full path has been found
            if (Point.comparePoints(curPoint, goal)) {
                finalPath.clear();
                finalPath.add(curPoint);
                //Run through the cameFrom map and add points to generate the path
                while (cameFrom.containsKey(curPoint)) {
                    curPoint = cameFrom.get(curPoint);
                    finalPath.add(curPoint);
                    if (Point.comparePoints(curPoint, start)) {
                        break;
                    }
                }
                break;
            }
            //Find points that are neighbouring to the current point and loop through these points
            ArrayList<Point> neighbors = getNeighbors(adjList, curPoint);
            for (int i = 0; i < neighbors.size(); i++) {
                Point neighbor = neighbors.get(i);
                //For each point, check if it is truly a neighbor, then calculate cost from the current point to the neighbor
                if (!Point.comparePoints(curPoint, neighbor)) {
                    double heuristic = costSoFar.get(curPoint) + Point.getDistance(curPoint, neighbor);
                    //Check if there is no cost for the neighbouring point or if the cost is lower, if so add the cost
                    if (!costSoFar.containsKey(neighbor) || heuristic < costSoFar.get(neighbor)) {
                        costSoFar.put(neighbor, heuristic);
                        //Set new heuristic cost for the neighbor
                        double priority = heuristic + getDistance(neighbor, goal);
                        neighbor.setHeuristicValue(priority);
                        //Put the neighbor into the PQ of explored points
                        openPoints.add(neighbor);
                        //Map the neighbor to the current point, so the steps can be retraced
                        cameFrom.put(neighbor, curPoint);
                    }
                }
            }
        }

        return 0;
    }
}
