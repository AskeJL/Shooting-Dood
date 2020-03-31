/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author tobia
 */
public class PlayerMovingPart implements EntityPart
{

    private float maxSpeed;
    private boolean left, right, up, down;
    private boolean W, A, S, D;

    public PlayerMovingPart(float maxSpeed)
    {
        this.maxSpeed = maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed)
    {
        this.maxSpeed = maxSpeed;
    }

    public float getMaxSpeed()
    {
        return maxSpeed;
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

    @Override
    public void process(GameData gameData, Entity entity)
    {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float rotation = positionPart.getRadians();

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
            x = x + maxSpeed;
            if (!left && !right && !up && !down)
            {
                rotation = 0;
            }
        } else if (A)
        {
            x = x - maxSpeed;
            if (!left && !right && !up && !down)
            {
                rotation = (float) Math.PI;
            }
        }

        if (W)
        {
            y = y + maxSpeed;
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
            y = y - maxSpeed;
        }

        //Border tjek
        if (x > gameData.getDisplayWidth())
        {
            x = 0;
        } else if (x < 0)
        {
            x = gameData.getDisplayWidth();
        }

        if (y > gameData.getDisplayHeight())
        {
            y = 0;
        } else if (y < 0)
        {
            y = gameData.getDisplayHeight();
        }

        positionPart.setX(x);
        positionPart.setY(y);
        positionPart.setRadians(rotation);

    }

}
