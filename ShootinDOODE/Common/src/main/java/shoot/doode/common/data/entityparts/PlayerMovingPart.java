package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;

public class PlayerMovingPart implements EntityPart
{
    private double speedModifier = 1;
    private float currentSpeed;
    private float baseSpeed;
    private boolean left, right, up, down;
    private boolean W, A, S, D;

    public PlayerMovingPart(float baseSpeed)
    {
        this.baseSpeed = baseSpeed;
        this.currentSpeed = this.baseSpeed;
    }

    public void setCurrentSpeed(float currentSpeed)
    {
        this.currentSpeed = currentSpeed;
    }
    
    public float getCurrentSpeed()
    {
        return currentSpeed;
    }
    
    public void setBaseSpeed(float baseSpeed)
    {
        this.baseSpeed = baseSpeed;
    }
    
    public void setSpeedModifier(double speedModifier)
    {
        this.speedModifier = speedModifier;
    }
    
    public double getSpeedModifier()
    {
        return speedModifier;
    }
    public float getBaseSpeed()
    {
        return baseSpeed;
    }

    public void setW(boolean W)
    {
        this.W = W;
    }

    public void setA(boolean A)
    {
        this.A = A;
    }

    public void setS(boolean S)
    {
        this.S = S;
    }

    public void setD(boolean D)
    {
        this.D = D;
    }

    public void setLeft(boolean left)
    {
        this.left = left;
    }

    public void setRight(boolean right)
    {
        this.right = right;
    }

    public void setUp(boolean up)
    {
        this.up = up;
    }

    public void setDown(boolean down)
    {
        this.down = down;
    }

    public boolean getW()
    {
        return W;
    }

    public boolean getA()
    {
        return A;
    }

    public boolean getS()
    {
        return S;
    }

    public boolean getD()
    {
        return D;
    }

    public boolean getLeft()
    {
        return left;
    }

    public boolean getRight()
    {
        return right;
    }

    public boolean getUp()
    {
        return up;
    }

    public boolean getDown()
    {
        return down;
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
        if (left)
        {
            rotation = (float) Math.PI;
        } else if (right)
        {
            rotation = 0;
        }

        if (up)
        {
            rotation = (float) Math.PI / 2;
        } else if (down)
        {
            rotation = (float) Math.PI + (float) Math.PI / 2;
        }

        if (up && left)
        {
            rotation = (float) Math.PI * 3 / 4;
        }

        if (up && right)
        {
            rotation = (float) Math.PI / 4;
        }

        if (down && right)
        {
            rotation = (float) Math.PI + (float) Math.PI * 3 / 4;
        }

        if (down && left)
        {
            rotation = (float) Math.PI + (float) Math.PI / 4;
        }

        //Movement
        if (D)
        {
            x = x + currentSpeed;
            if (!left && !right && !up && !down)
            {
                rotation = 0;
            }
        } else if (A)
        {
            x = x - currentSpeed;
            if (!left && !right && !up && !down)
            {
                rotation = (float) Math.PI;
            }
        }

        if (W)
        {
            y = y + currentSpeed;
            if (!left && !right && !up && !down)
            {
                rotation = (float) Math.PI / 2;
                if (W && A)
                {
                    rotation = (float) Math.PI * 3 / 4;
                }

                if (W && D)
                {
                    rotation = (float) Math.PI / 4;
                }

            }
        } else if (S)
        {
            if (!left && !right && !up && !down)
            {
                rotation = (float) Math.PI + (float) Math.PI / 2;
                if (S && D)
                {
                    rotation = (float) Math.PI + (float) Math.PI * 3 / 4;
                }

                if (S && A)
                {
                    rotation = (float) Math.PI + (float) Math.PI / 4;
                }

            }
            y = y - currentSpeed;
        }
        PositionPart.setX(x);
        PositionPart.setY(y);
        PositionPart.setRotation(rotation);
    }

}
