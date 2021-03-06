package shoot.doode.weapon;

import shoot.doode.common.services.IAssetService;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


@ServiceProviders(value = {
    @ServiceProvider(service = IAssetService.class),})
public class WeaponAssetsLoader implements IAssetService {

    String module = "Weapon";

    private String[] sprites() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("knife.png");
        paths.add("gun.png");
        paths.add("Shotgun.png");

        return IAssetService.arrayListToString(module, paths);
    }

    private String[] sounds() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("Gun_Fire.mp3");

        return IAssetService.arrayListToString(module, paths);

    }

    @Override
    public String[] loadSprites() {
        return sprites();
    }

    @Override
    public String[] unLoadSprites() {
        return sprites();
    }

    
    @Override
    public String[] loadSounds() {
        return sounds();
    }

    @Override
    public String[] unLoadSounds() {
        return sounds();
    }

    @Override
    public String[] loadMaps() {
        return null;
    }

    @Override
    public String[] unLoadMaps() {
        return null;
    }

}
