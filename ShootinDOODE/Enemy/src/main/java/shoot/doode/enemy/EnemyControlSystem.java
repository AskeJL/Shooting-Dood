/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.enemy;

import shoot.doode.commonenemy.Enemy;
import shoot.doode.commonenemy.AI;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.GameKeys;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author askel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class EnemyControlSystem implements IEntityProcessingService, AI {

    @Override
    public void process(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void AI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
