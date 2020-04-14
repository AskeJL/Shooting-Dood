package shoot.doode.weapon;

import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.services.IGamePluginService;
import shoot.doode.commonweapon.Weapon;
import static shoot.doode.commonweapon.WeaponType.GUN;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),
    })
public class WeaponPlugin implements IGamePluginService{
    private Entity weapon;

    @Override
    public void start(GameData gameData, World world) {
        weapon = createWeapon(gameData);
        world.addEntity(weapon);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities(Weapon.class)) {
            world.removeEntity(e);
        }
    }

    private Weapon createWeapon(GameData gameData) {
        float speed = (float) Math.random() * 10f + 40f;
        float radians = 3.1415f / 2 + (float) Math.random();
        float x = gameData.getDisplayWidth() / 2 + 100;
        float y = gameData.getDisplayHeight() / 2 + 50;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        Entity weapon = new Weapon(GUN);
        weapon.add(new MovingPart(0, speed, speed, 0));
        weapon.add(new PositionPart(x, y, radians));
        weapon.setColour(colour);
        UUID uuid = UUID.randomUUID();
        weapon.setRadius(15);

        return (Weapon) weapon;
    }
}
