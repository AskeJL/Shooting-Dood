package shoot.doode.weapon;

import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.GameKeys;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PlayerPositionPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.services.IEntityProcessingService;
import shoot.doode.commonweapon.Weapon;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class WeaponControlSystem implements IEntityProcessingService {

    int numPoints = 4;
    Random rnd = new Random(10);
    float angle = 90;

    @Override
    public void process(GameData gameData, World world) {
        Entity player;
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(PlayerPositionPart.class) != null) {
                player = entity;
                
                for (Entity weapon : world.getEntities(Weapon.class)) {
                    PositionPart positionPart = weapon.getPart(PositionPart.class);
                    ShootingPart shootingPart = weapon.getPart(ShootingPart.class);
                    SoundPart soundPart = weapon.getPart(SoundPart.class);
                    
                    // Will give a nullPointerExeption
                    shootingPart.setIsShooting(gameData.getKeys().isDown(GameKeys.SPACE));
                    
                    if (gameData.getKeys().isDown(GameKeys.SPACE)) {
                        //The sound will play every frame they key is down like this
                        //Which is why we should tie it to the weapon or bullet module and have it play when a bullet gets spawned
                        soundPart.setPlay("Gun_Fire.mp3", true);
                    }

                    positionPart.process(gameData, weapon);
                    shootingPart.process(gameData, player);

                    updateShape(player, weapon);
                }
            }
        }
    }

    private void updateShape(Entity player, Entity weapon) {
        PlayerPositionPart playerPositionPart = player.getPart(PlayerPositionPart.class);

        float[] shapex = new float[4];
        float[] shapey = new float[4];
        float x = playerPositionPart.getX() + 40;
        float y = playerPositionPart.getY();
        float rotation = playerPositionPart.getRotation();

        Weapon asWeapon = (Weapon) weapon;
        if (asWeapon.getType().equals("GUN")) {
            shapex[0] = (float) (x + Math.cos(rotation) * 5);
            shapey[0] = (float) (y + Math.sin(rotation) * 15);

            shapex[1] = (float) (x + Math.cos(rotation - 4 * 3.1415f / 5) * weapon.getRadius());
            shapey[1] = (float) (y + Math.sin(rotation - 4 * 3.1145f / 5) * weapon.getRadius());

            shapex[2] = (float) (x + Math.cos(rotation + 3.1415f) * weapon.getRadius());
            shapey[2] = (float) (y + Math.sin(rotation + 3.1415f) * weapon.getRadius());

            shapex[3] = (float) (x + Math.cos(rotation + 4 * 3.1415f / 5) * weapon.getRadius());
            shapey[3] = (float) (y + Math.sin(rotation + 4 * 3.1415f / 5) * weapon.getRadius());
            /*
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = (x-10) + (float) Math.cos(angle + radians) * 5;
                shapey[i] = y + (float) Math.sin(angle + radians) * 15;
                angle += 2 * 3.1415f /numPoints;
            }*/
        }
        /*
        if (asWeapon.getType().equals("RIFLE")) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 8;
                shapey[i] = y + (float) Math.sin(angle + radians) * 18;
                angle += 2 * 3.1415f / numPoints;
            }
        }
        if (asWeapon.getType().equals("KNIFE")) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 3;
                shapey[i] = y + (float) Math.sin(angle + radians) * 12;
                angle += 2 * 3.1415f / numPoints;
            }
        }*/
        weapon.setShapeX(shapex);
        weapon.setShapeY(shapey);
    }
}
