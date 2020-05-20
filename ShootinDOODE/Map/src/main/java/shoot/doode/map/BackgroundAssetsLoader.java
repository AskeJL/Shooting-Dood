/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    private String[] images() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("brick_wall_red.png");
        paths.add("brick_wall_white.png");
        paths.add("brick_wall_blue.png");
        paths.add("rsz_oa_bench.png");
        paths.add("wall.png");

        return IAssetService.arrayListToString(module, paths);
    }

    private String[] maps() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("map.tmx");
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
