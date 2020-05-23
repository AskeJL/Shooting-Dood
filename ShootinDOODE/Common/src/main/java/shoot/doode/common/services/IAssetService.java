package shoot.doode.common.services;

import java.util.ArrayList;

/**
 *
 * @author tobia
 */
public interface IAssetService {

    String[] loadSprites();

    String[] unLoadSprites();
    
    String[] loadSounds();

    String[] unLoadSounds();
    
    public static String[] arrayListToString(String module, ArrayList<String> paths) {
        if (paths.size() >= 1) {
            for (int i = 0; i < paths.size(); i++) {
                paths.set(i, module + "!" + paths.get(i));
            }
        } else {
            return null;
        }

        return paths.toArray(new String[paths.size()]);
    }
    
    String[] loadMaps();

    String[] unLoadMaps();



    

}
