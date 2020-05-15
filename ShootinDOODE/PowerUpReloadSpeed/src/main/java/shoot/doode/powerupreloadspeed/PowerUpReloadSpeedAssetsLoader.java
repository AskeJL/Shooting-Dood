/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.powerupreloadspeed;

import shoot.doode.common.services.IAssetService;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author tobia
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IAssetService.class),})

public class PowerUpReloadSpeedAssetsLoader implements IAssetService {

    String module = "PowerUpReloadSpeed";

    private String[] images() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("apple.png");

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
