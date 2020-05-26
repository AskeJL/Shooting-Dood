package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;

/**
 *
 * @author Alexander
 */
public class PositionPart implements EntityPart {

    private float x;
    private float y;
    private float rotation;

    public PositionPart(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setX(float newX) {
        this.x = newX;
    }

    public void setY(float newY) {
        this.y = newY;
    }

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }

}
