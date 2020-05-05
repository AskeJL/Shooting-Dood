package shoot.doode.weapon;

import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.SoundPart;
import shoot.doode.common.data.entityparts.SpritePart;
import shoot.doode.common.services.IGamePluginService;
import shoot.doode.commonweapon.Weapon;
import static shoot.doode.commonweapon.WeaponType.GUN;
import shoot.doode.common.data.CollidableEntity;

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
        // for (Entity weapon : world.getEntities(Weapon.class)) {
            world.removeEntity(weapon);
        // }
    }

    private Weapon createWeapon(GameData gameData) {
        
        float x = gameData.getDisplayWidth() / 2 + 40;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2 ;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        String module = "Weapon";
        String[] spritePaths = new String[1];
        spritePaths[0] = "knife.png";
        
        String[] soundPaths = new String[1];
        soundPaths[0] = "Gun_Fire.mp3";
        
        UUID uuid = UUID.randomUUID();

        Entity weapon = new Weapon(GUN);
        weapon.add(new PositionPart(x, y, radians));
        weapon.add(new ShootingPart(uuid.toString()));
        // weapon.add(new SpritePart(module, spritePaths));
        weapon.add(new SoundPart(module, soundPaths));
        weapon.setColour(colour);
        weapon.setRadius(5);

        return (Weapon) weapon;
    }
}
