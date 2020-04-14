package shoot.doode.weapon;

import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
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
        for (Entity weapon : world.getEntities(Weapon.class)) {
            System.out.println("hello");
            PositionPart positionPart = weapon.getPart(PositionPart.class);
            MovingPart movingPart = weapon.getPart(MovingPart.class);

            float speed = (float) Math.random() * 10f + 40f;
            if (rnd.nextInt() < 8) {
                movingPart.setMaxSpeed(speed);
                movingPart.setUp(true);
            } else {
                movingPart.setLeft(true);
            }

            movingPart.process(gameData, weapon);
            positionPart.process(gameData, weapon);
            updateShape(weapon);
            movingPart.setLeft(false);
            movingPart.setUp(false);
        }
    }

    private void updateShape(Entity weapon) {
        PositionPart positionPart = weapon.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = 4;

        float[] shapex = new float[6];
        float[] shapey = new float[6];
        Weapon asWeapon = (Weapon) weapon;
        if (asWeapon.getType().equals("GUN")) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 26;
                shapey[i] = y + (float) Math.sin(angle + radians) * 26;
                angle += 2 * 3.1415f / numPoints;
            }
        }
        if (asWeapon.getType().equals("RIFLE")) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 16;
                shapey[i] = y + (float) Math.sin(angle + radians) * 16;
                angle += 2 * 3.1415f / numPoints;
            }
        }
        if (asWeapon.getType().equals("KNIFE")) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 8;
                shapey[i] = y + (float) Math.sin(angle + radians) * 8;
                angle += 2 * 3.1415f / numPoints;
            }
        }

        weapon.setShapeX(shapex);
        weapon.setShapeY(shapey);
    }
}
