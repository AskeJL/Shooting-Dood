package shoot.doode.weapon;

import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.GameKeys;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.services.IEntityProcessingService;
import shoot.doode.commonweapon.Weapon;


@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class WeaponControlSystem implements IEntityProcessingService {
    
    int numPoints = 4;
    Random rnd = new Random(10);
    float angle = 0;

    @Override
    public void process(GameData gameData, World world) {
        Entity player;
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null) {
                player = entity;
                for (Entity weapon : world.getEntities(Weapon.class)) {
                    PositionPart positionPart = weapon.getPart(PositionPart.class);
                    positionPart.process(gameData, weapon);

                    updateShape(player, weapon);
                }
            }
        }
    }

    private void updateShape(Entity player, Entity weapon) {
        PositionPart playerPositionPart = player.getPart(PositionPart.class);
        
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        float x = playerPositionPart.getX()+40;
        float y = playerPositionPart.getY();
        float radians = playerPositionPart.getRotation();


        Weapon asWeapon = (Weapon) weapon;
        if (asWeapon.getType().equals("GUN")) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = (x-10) + (float) Math.cos(angle + radians) * 5;
                shapey[i] = y + (float) Math.sin(angle + radians) * 15;
                angle += 2 * 3.1415f /numPoints;
            }
        }
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
        }
        weapon.setShapeX(shapex);
        weapon.setShapeY(shapey);
    }
}
