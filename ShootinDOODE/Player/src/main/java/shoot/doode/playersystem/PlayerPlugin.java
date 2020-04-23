package shoot.doode.playersystem;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.services.IGamePluginService;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.CollidableEntity;

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

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        String module = "Player";
        String[] spritePaths = new String[1];
        spritePaths[0] = "Doode-still.png";
        
        String[] soundPaths = new String[1];
        soundPaths[0] = "Gun_Fire.mp3";
        
        UUID uuid = UUID.randomUUID();
        
        
        CollidableEntity playerShip = new Player();
        playerShip.setRadius(8);
        playerShip.setColour(colour);
        playerShip.add(new PlayerMovingPart(maxSpeed));
        playerShip.add(new PositionPart(x, y, radians));
        playerShip.add(new LifePart(1));
        playerShip.add(new SpritePart(module, spritePaths));
        playerShip.add(new SoundPart(module, soundPaths));
        playerShip.add(new ShootingPart(uuid.toString()));
        
        
        
        playerShip.setBoundaryWidth(50);
        playerShip.setBoundaryHeight(50);

        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

}
