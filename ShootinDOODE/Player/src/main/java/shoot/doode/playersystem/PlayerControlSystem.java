package shoot.doode.playersystem;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.GameKeys;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SpritePart;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class PlayerControlSystem implements IEntityProcessingService {
    private boolean oldSpaceValue;
    private boolean newSpaceValue;
    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            PlayerMovingPart playerMovingPart = player.getPart(PlayerMovingPart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            SpritePart spritepart = player.getPart(SpritePart.class);
            System.out.println(lifePart.getLife());
            playerMovingPart.setCurrentSpeed(playerMovingPart.getBaseSpeed() * (float)playerMovingPart.getSpeedModifier());
                    
            playerMovingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            if (gameData.getKeys().isDown(GameKeys.LEFT)){
            spritepart.setCurrentSprite(1);
            }
            playerMovingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            if (gameData.getKeys().isDown(GameKeys.RIGHT)){
            spritepart.setCurrentSprite(2);
            }
            playerMovingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            if (gameData.getKeys().isDown(GameKeys.UP)){
            spritepart.setCurrentSprite(3);
            }
            playerMovingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));
            if (gameData.getKeys().isDown(GameKeys.DOWN)){
            spritepart.setCurrentSprite(0);
            }
            
            if(gameData.getKeys().isDown(GameKeys.LEFT) || gameData.getKeys().isDown(GameKeys.RIGHT) || gameData.getKeys().isDown(GameKeys.UP) || gameData.getKeys().isDown(GameKeys.DOWN))
            {
                shootingPart.setIsShooting(true);
            }
            else{
                shootingPart.setIsShooting(false);
            }
            
            shootingPart.setSwitchWeapon(false);
            newSpaceValue = gameData.getKeys().isDown(GameKeys.SPACE);            
            if(newSpaceValue != oldSpaceValue && newSpaceValue)
            {
                shootingPart.setSwitchWeapon(true);
            }           
            oldSpaceValue = newSpaceValue;
   
            playerMovingPart.setW(gameData.getKeys().isDown(GameKeys.W));
            playerMovingPart.setA(gameData.getKeys().isDown(GameKeys.A));
            playerMovingPart.setS(gameData.getKeys().isDown(GameKeys.S));
            playerMovingPart.setD(gameData.getKeys().isDown(GameKeys.D));        
            
            playerMovingPart.process(gameData, player);
            positionPart.process(gameData, player);
            shootingPart.process(gameData, player);
            lifePart.process(gameData, player);

            updateShape(player);
        }
    }
    

    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float rotation = positionPart.getRotation();

        shapex[0] = (float) (x + Math.cos(rotation) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(rotation) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(rotation - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(rotation - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(rotation + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(rotation + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(rotation + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(rotation + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
