package shoot.doode.enemy;

import shoot.doode.common.services.IAssetService;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IAssetService.class),})

public class EnemyAssetsLoader implements IAssetService {

    String module = "Enemy";

    private String[] sprites() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("Green_Virus.png");
        paths.add("Red_Virus.png");

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
    public String[] loadMaps() {
        return null;
    }

    @Override
    public String[] unLoadMaps() {
        return null;
    }
}
