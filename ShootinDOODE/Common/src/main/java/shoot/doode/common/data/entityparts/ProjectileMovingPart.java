package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;

public class ProjectileMovingPart implements EntityPart{
    
    private float currentSpeed;

    public ProjectileMovingPart(float currentSpeed)
    {
        this.currentSpeed = currentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed)
    {
        this.currentSpeed = currentSpeed;
    }
    
    public float getCurrentSpeed()
    {
        return currentSpeed;
    }

    public float getX(Entity entity){
        PositionPart PositionPart = entity.getPart(PositionPart.class);
        return PositionPart.getX();
    }
    
    public float getY(Entity entity){
        PositionPart PositionPart = entity.getPart(PositionPart.class);
        return PositionPart.getY();
    }

    @Override
    public void process(GameData gameData, Entity entity)
    {
        PositionPart PositionPart = entity.getPart(PositionPart.class);
        float x = PositionPart.getX();
        float y = PositionPart.getY();
        float rotation = PositionPart.getRotation();

        //Rotation
        float deltaX = this.currentSpeed * (float)Math.cos(rotation);
        float deltaY = this.currentSpeed * (float)Math.sin(rotation);
        
        x += deltaX;
        y += deltaY;

        PositionPart.setX(x);
        PositionPart.setY(y);
        PositionPart.setRotation(rotation);
    }

}
    
