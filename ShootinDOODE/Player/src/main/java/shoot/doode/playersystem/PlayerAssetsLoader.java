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
    @ServiceProvider(service = IAssetService.class),
    })

public class PlayerAssetsLoader implements IAssetService{
    
    
    private String[] images()
    {
        String module = "Player";
        
        ArrayList<String> paths = new ArrayList<>();
        paths.add("Red_Virus.png");
        
        
        
        
        for(int i = 0; i < paths.size(); i++)
        {
            paths.set(i, module+"!"+paths.get(i));
        }
                        
        String[] arrayPaths = paths.toArray(new String[paths.size()]);
        for(String s : arrayPaths)
        {
            System.out.println(s);
        }
        return arrayPaths;
    }
    
    
    
     @Override
    public String[] loadImages()
    {
        return images();
    }
    
    @Override
    public String[] unLoadImages()
    {
        return images();
    }
    
    @Override
    public String[] loadSounds()
    {
        return null;
    }
    
    @Override
    public String[] unLoadSounds()
    {
        return null;
    }
    
}
