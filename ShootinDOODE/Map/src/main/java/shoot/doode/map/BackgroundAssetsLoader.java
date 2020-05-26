package shoot.doode.map;

import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.services.IAssetService;

/**
 *
 * @author askel
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IAssetService.class),})
public class BackgroundAssetsLoader implements IAssetService {

    String module = "Map";

    private String[] maps() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("map.tmx");
        return IAssetService.arrayListToString(module, paths);
    }

    @Override
    public String[] loadSprites() {
        return null;
    }

    @Override
    public String[] unLoadSprites() {
        return null;
    }

    @Override
    public String[] loadSounds() {
        return null;
    }

    @Override
    public String[] unLoadSounds() {
        return null;
    }

    @Override
    public String[] loadMaps() {
        return maps();
    }

    @Override
    public String[] unLoadMaps() {
        return maps();
    }

}
