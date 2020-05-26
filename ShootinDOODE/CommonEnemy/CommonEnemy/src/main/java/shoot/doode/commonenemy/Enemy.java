package shoot.doode.commonenemy;

import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.services.IScoreGiver;

public abstract class Enemy extends CollidableEntity implements IScoreGiver{
    private float score;
    
    public Enemy(float score)
    {
        this.score = score;
        this.setToughness(10);
    }
    
    @Override
    public float getScore()
    {
        return score;
    }

    public void setScore(float score)
    {
        this.score = score;
    }
    
}
