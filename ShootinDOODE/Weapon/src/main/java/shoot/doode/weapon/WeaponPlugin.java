package shoot.doode.weapon;

import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.services.IGamePluginService;
import shoot.doode.commonweapon.Weapon;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),
    })

public class WeaponPlugin implements IGamePluginService{

    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity weapon : world.getEntities(Weapon.class)) {
            world.removeEntity(weapon);
        }
    }
}
