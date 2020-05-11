/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;

/**
 *
 * @author Phillip Olsen
 */
public class LifePart implements EntityPart {

    private boolean invulnerable;
    private double life;

    public LifePart(double life) {
        this.life = life;
    }

    public double getLife() {
        return life;
    }
    
    public boolean isInvulnerable()
    {
        return invulnerable;
    }
    
    public void setInvulnerable(boolean invulnerable)
    {
        this.invulnerable = invulnerable;
    }

    public void setLife(double life) {
        this.life = life;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
