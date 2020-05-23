package shoot.doode.playersystem;

import java.util.UUID;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.entityparts.PositionPart;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),
    })
public class PlayerPlugin implements IGamePluginService {

    private Entity player;
    
    
    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        float maxSpeed = 3;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        String module = "Player";
        String[] spritePaths = new String[4];
        spritePaths[0] = "Doode-still.png";
        spritePaths[1] = "Doode-left.png";
        spritePaths[2] = "Doode-right.png";
        spritePaths[3] = "Doode-back.png";
       
        CollidableEntity playerShip = new Player();
        playerShip.add(new SpritePart(module, spritePaths));
        UUID id = UUID.randomUUID();
        playerShip.setID(id.toString());
        playerShip.add(new PlayerMovingPart(maxSpeed));
        playerShip.add(new PositionPart(x, y, radians));
        playerShip.add(new LifePart(1));
        
        playerShip.add(new ShootingPart(true));
        
        playerShip.setToughness(2);
        playerShip.setBoundaryWidth(30);
        playerShip.setBoundaryHeight(30);

        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

}
