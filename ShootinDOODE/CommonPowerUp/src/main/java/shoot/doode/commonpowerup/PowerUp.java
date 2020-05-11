/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.commonpowerup;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.LifePart;

/**
 *
 * @author tobia
 */
public class PowerUp extends CollidableEntity{
    PowerUpType e;
    
    public PowerUp(PowerUpType e)
    {
        this.e = e;
    }
    
    public PowerUpType getPowerUpType()
    {
        return e;
    }
    
    public void applyPowerUp(Entity player){
        ShootingPart playerShootingPart = player.getPart(ShootingPart.class);
        PlayerMovingPart playerMovementPart = player.getPart(PlayerMovingPart.class);
        LifePart playerLifePart = player.getPart(LifePart.class);
        
        switch(e){
            case INVULNERABILITY:
                playerLifePart.setLife(999999999999999999999999999999999d);
                break;
            case MOVEMENTSPEED:
                playerMovementPart.setSpeedModifier(playerMovementPart.getSpeedModifier()+0.1);
                break;
            case REALOAD:
                playerShootingPart.setReloadModifier(playerShootingPart.getReloadModifier()+0.1);
                break;
            case DAMAGE:
                playerShootingPart.setDamageModifier(playerShootingPart.getDamageModifier()+0.1);
                break;           
        }
    }
    
    
}
