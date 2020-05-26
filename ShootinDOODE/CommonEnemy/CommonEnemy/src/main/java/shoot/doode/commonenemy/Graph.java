package shoot.doode.commonenemy;

import java.util.ArrayList;

public class Graph {
    int vertex;
    ArrayList<ArrayList<Point>> adjList;

    public Graph() {
        adjList = new ArrayList<ArrayList<Point>>();
    }

    public void addEdge(Point p1, Point p2) {
        boolean p1NotFound = true;
        boolean p2NotFound = true;
        for (ArrayList<Point> arrList : adjList) {
            if(arrList.size() > 0){
                Point p = arrList.get(0);
                if(Point.comparePoints(p, p1)){
                    arrList.add(p2);
                    p1NotFound = false;
                }
                else if(Point.comparePoints(p, p2)){
                    arrList.add(p1);
                    p2NotFound = false;
                }
            }
        }
        if(p1NotFound) {					
            ArrayList<Point> a1 = new ArrayList<>();
            a1.add(p1);
            a1.add(p2);
            adjList.add(a1);			
        }
        if(p2NotFound) {
            ArrayList<Point> a2 = new ArrayList<>();
            a2.add(p2);
            a2.add(p1);
            adjList.add(a2);			
        }
    }
    
    public void removeFromSight(Point point) {
        for (ArrayList<Point> arrList : adjList) {
            if(arrList.size() > 0) {
                Point p = arrList.get(1);
                if(Point.comparePoints(p, point)){
                    adjList.remove(arrList);
                    break;
                }
            }
        }
    }

    public void printGraph() {
        System.out.println("Adjacency List:");
        for(int i = 0; i < adjList.size(); i++) {
            for (int j = 0; j < adjList.get(i).size(); j++) {
                System.out.print( "(" + adjList.get(i).get(j).getX() + ", " + adjList.get(i).get(j).getY() + ")");
                if(j < adjList.get(i).size() - 1){
                    System.out.print("<->");
                }
            }
            System.out.print("\n");
        }
    }

    public ArrayList<ArrayList<Point>> getGraph() {
        return adjList;
    }	
}
