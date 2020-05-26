package shoot.doode.commonenemy;

public class Rectangle {

    float x;
    float y;
    float width;
    float height;

    public Rectangle() {

    }

    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean containsPoint(int xValue, int yValue) {

        return xValue >= x - (width / 2)
                && xValue <= x - (width / 2) + width
                && yValue >= y - (height / 2)
                && yValue <= y - (height / 2) + height;

    }
}
