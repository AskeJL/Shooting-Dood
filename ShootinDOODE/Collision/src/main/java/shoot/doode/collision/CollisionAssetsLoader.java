package shoot.doode.collision;

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
public class CollisionAssetsLoader implements IAssetService {

    String module = "Collision";

    private String[] images() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("brick_wall_red.png");
        paths.add("brick_wall_white.png");
        paths.add("brick_wall_blue.png");
        
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
        ArrayList<String> paths = new ArrayList<>();

        return IAssetService.arrayListToString(module, paths);
    }
    
    @Override
    public String[] loadSounds() {
        return sounds();
    }

    @Override
    public String[] unLoadSounds() {
        return sounds();
    }
}

