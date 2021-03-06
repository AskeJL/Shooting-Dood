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
            //Get parts
            PositionPart positionPart = player.getPart(PositionPart.class);
            PlayerMovingPart playerMovingPart = player.getPart(PlayerMovingPart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            SpritePart spritepart = player.getPart(SpritePart.class);
            
            playerMovingPart.setCurrentSpeed(playerMovingPart.getBaseSpeed() * (float) playerMovingPart.getSpeedModifier());

            //Change sprite
            playerMovingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                spritepart.setCurrentSprite(1);
            }
            playerMovingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                spritepart.setCurrentSprite(2);
            }
            playerMovingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                spritepart.setCurrentSprite(3);
            }
            playerMovingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));
            if (gameData.getKeys().isDown(GameKeys.DOWN)) {
                spritepart.setCurrentSprite(0);
            }

            //Shoot
            if (gameData.getKeys().isDown(GameKeys.LEFT) || gameData.getKeys().isDown(GameKeys.RIGHT) || gameData.getKeys().isDown(GameKeys.UP) || gameData.getKeys().isDown(GameKeys.DOWN)) {
                shootingPart.setIsShooting(true);
            } else {
                shootingPart.setIsShooting(false);
            }

            //Switch weapon
            shootingPart.setSwitchWeapon(false);
            newSpaceValue = gameData.getKeys().isDown(GameKeys.SPACE);
            if (newSpaceValue != oldSpaceValue && newSpaceValue) {
                shootingPart.setSwitchWeapon(true);
            }
            oldSpaceValue = newSpaceValue;

            //Movement
            playerMovingPart.setW(gameData.getKeys().isDown(GameKeys.W));
            playerMovingPart.setA(gameData.getKeys().isDown(GameKeys.A));
            playerMovingPart.setS(gameData.getKeys().isDown(GameKeys.S));
            playerMovingPart.setD(gameData.getKeys().isDown(GameKeys.D));

            playerMovingPart.process(gameData, player);
            positionPart.process(gameData, player);
            shootingPart.process(gameData, player);
            lifePart.process(gameData, player);
            
        }
    }
        }
