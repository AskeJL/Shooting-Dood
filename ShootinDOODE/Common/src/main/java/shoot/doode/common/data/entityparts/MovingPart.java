/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.common.data.entityparts;

import com.badlogic.gdx.math.Vector2;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import static shoot.doode.common.data.GameKeys.LEFT;
import static shoot.doode.common.data.GameKeys.RIGHT;
import static shoot.doode.common.data.GameKeys.UP;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 *
 * @author Alexander
 */
public class MovingPart
        implements EntityPart {

    
    private float dx, dy;
    private float deceleration, acceleration;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up;
    private float destinationX, destinationY;
    private Vector2 movementVector = new Vector2();
    
    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setSpeed(float speed) {
        this.acceleration = speed;
        this.maxSpeed = speed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
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
            
            if(movementVector.len() < 3) {
                x = destinationX;
                y = destinationY;
            }
            else {
                movementVector.nor().scl(45 * dt);
                x += movementVector.x;
                y += movementVector.y;
            }
        }
        else {
            // turning
            if (left) {
                rotation += rotationSpeed * dt;
            }

            if (right) {
                rotation -= rotationSpeed * dt;
            }

            // accelerating            
            if (up) {
                dx += cos(rotation) * acceleration * dt;
                dy += sin(rotation) * acceleration * dt;
            }

            // deccelerating
            float vec = (float) sqrt(dx * dx + dy * dy);
            if (vec > 0) {
                dx -= (dx / vec) * deceleration * dt;
                dy -= (dy / vec) * deceleration * dt;
            }
            if (vec > maxSpeed) {
                dx = (dx / vec) * maxSpeed;
                dy = (dy / vec) * maxSpeed;
            }

            // set position
            x += dx * dt;
            if (x > gameData.getDisplayWidth()) {
                x = 0;
            } else if (x < 0) {
                x = gameData.getDisplayWidth();
            }

            y += dy * dt;
            if (y > gameData.getDisplayHeight()) {
                y = 0;
            } else if (y < 0) {
                y = gameData.getDisplayHeight();
            }
        }

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRotation(rotation);
    }

}
