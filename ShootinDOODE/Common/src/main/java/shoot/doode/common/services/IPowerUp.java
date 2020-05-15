/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.common.services;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;

/**
 *
 * @author tobia
 */
public interface IPowerUp{
    public void removePowerUps(GameData gameData,World world);
    public void spawnPowerUp(GameData gameData,World world);
    public void applyPowerUp(Entity entity);
    
}
