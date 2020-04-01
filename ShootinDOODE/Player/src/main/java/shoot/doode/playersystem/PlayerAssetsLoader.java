/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.playersystem;

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

public class PlayerAssetsLoader implements IAssetService {

    String module = "Player";

    private String[] images() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("Red_Virus.png");

        return arrayListToString(paths);
    }

    private String[] sounds() {
        ArrayList<String> paths = new ArrayList<>();
        //paths.add("sound.mp3");

        return arrayListToString(paths);

    }

    private String[] arrayListToString(ArrayList<String> paths) {
        if (paths.size() >= 1) {
            for (int i = 0; i < paths.size(); i++) {
                paths.set(i, module + "!" + paths.get(i));
            }
        } else {
            return null;
        }

        return paths.toArray(new String[paths.size()]);
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

}
