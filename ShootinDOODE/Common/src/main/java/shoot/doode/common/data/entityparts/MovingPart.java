package shoot.doode.common.data.entityparts;

import com.badlogic.gdx.math.Vector2;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;

public class MovingPart
        implements EntityPart {

    private float destinationX, destinationY;
    private float maxSpeed;
    private Vector2 movementVector = new Vector2();
    
    public MovingPart(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    
    public void setDestination(float x, float y) {
        this.destinationX = x;
        this.destinationY = y;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float rotation = positionPart.getRotation();
        float dt = gameData.getDelta();

        if(destinationX != 0 && destinationY != 0) {
            movementVector.set(destinationX, destinationY).sub(x, y);
            
            if(movementVector.len() < maxSpeed*dt) {
                float oldX = x;
                float oldY = y;
                x = destinationX;
                y = destinationY;
                
                rotation = (float)Math.atan2(y-oldY, x-oldX);
                
                
            }
            else {
                movementVector.nor().scl(maxSpeed * dt);
                float oldX = x;
                float oldY = y;
                x += movementVector.x;
                y += movementVector.y;
                rotation = (float)Math.atan2(y-oldY, x-oldX);
            }
        }
        
        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRotation(rotation);
    }
}