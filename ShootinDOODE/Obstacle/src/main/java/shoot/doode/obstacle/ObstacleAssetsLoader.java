package shoot.doode.obstacle;

import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.services.IAssetService;

/**
 *
 * @author sande
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IAssetService.class),})
public class ObstacleAssetsLoader implements IAssetService {

    String module = "Obstacle";

    private String[] images() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("BuickerB.png");
        paths.add("GalardB.png");
        paths.add("JeepB.png");
        paths.add("RamB.png");
        paths.add("SuperB.png");
        
        return IAssetService.arrayListToString(module, paths);
    }

    private String[] sounds() {
        ArrayList<String> paths = new ArrayList<>();

        return IAssetService.arrayListToString(module, paths);

    }

    @Override
    public String[] loadImages() {
        return images();
    }

    @Override
    public String[] unLoadImages() {
        return images();
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

