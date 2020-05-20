package shoot.doode.commonenemy;

/**
 *
 * @author sande
 */
public class Point implements Comparable<Point> {
    int x;
    int y;
    double fcost;

    public Point(int x, int y, double costSoFar) {
        this.x = x;
        this.y = y;
        this.fcost = costSoFar;
    }
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.fcost = 0.0;
    }
    
    public Point(float x, float y) {
        this.x = (int)x;
        this.y = (int)y;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public double getFcost() {
        return fcost;
    }
    public void setFcost(double priority) {
        this.fcost = priority;
    }

    @Override
    public int compareTo(Point p1) {
        return (int)(this.fcost - p1.getFcost());
    }	

    public static boolean comparePoints (Point p1, Point p2) {
        if(p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static double getDistance(Point p1, Point p2) {
        return Math.hypot(p1.getX() - p2.getX(), p1.getY() - p2.getY());
    }
}
