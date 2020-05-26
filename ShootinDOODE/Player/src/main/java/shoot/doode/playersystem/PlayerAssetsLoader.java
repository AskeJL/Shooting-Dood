package shoot.doode.playersystem;

import shoot.doode.common.services.IAssetService;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IAssetService.class),})

public class PlayerAssetsLoader implements IAssetService {

    String module = "Player";

    private String[] Sprites() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("Doode-still.png");
        paths.add("Doode-left.png");
        paths.add("Doode-right.png");
        paths.add("Doode-back.png");

        return IAssetService.arrayListToString(module, paths);
    }

    private String[] sounds() {
        ArrayList<String> paths = new ArrayList<>();

        return IAssetService.arrayListToString(module, paths);

    }

    @Override
    public String[] loadSprites() {
        return Sprites();
    }

    @Override
    public String[] unLoadSprites() {
        return Sprites();
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
