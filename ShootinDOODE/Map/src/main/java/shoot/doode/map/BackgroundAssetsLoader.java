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
    
    private String[] maps() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("map.tmx");
        return IAssetService.arrayListToString(module, paths);}

    @Override
    public String[] loadImages() {
        return maps();    }

    @Override
    public String[] unLoadImages() {
        return maps();
    }

    @Override
    public String[] loadSounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] unLoadSounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
