package shoot.doode.powerupreloadspeed;

import shoot.doode.common.services.IAssetService;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IAssetService.class),})

public class PowerUpReloadSpeedAssetsLoader implements IAssetService {

    String module = "PowerUpReloadSpeed";

    private String[] sprites() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("apple.png");

        return IAssetService.arrayListToString(module, paths);
    }

    private String[] sounds() {
        ArrayList<String> paths = new ArrayList<>();

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
    public String[] loadMaps()
    {
        return null;
    }
    
    @Override
    public String[] unLoadMaps()
    {
        return null;
    }
    
}
