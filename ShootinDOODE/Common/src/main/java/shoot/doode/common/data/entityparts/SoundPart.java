package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import java.util.HashMap;
import java.util.ArrayList;
/**
 *
 * @author tobia
 */
public class SoundPart implements EntityPart{
    
    String module;
    HashMap<String,Boolean> sounds = new HashMap<>();
      
    public SoundPart(String module, String[] soundPaths)
    {
        this.module = module;
        for(String path : soundPaths)
        {
            sounds.put(path, false);
        }
    }
    
    public String[] getSoundPaths()
    {
        ArrayList<String> soundPaths = new ArrayList<>();
        for(String path : sounds.keySet())
        {
            if(sounds.get(path))
            {
                soundPaths.add(path);
            }
        }
        
        return soundPaths.toArray(new String[soundPaths.size()]);
        
    }
    
    public String getModule()
    {
        return module;
    }
    
    public void setPlay(String soundPath,Boolean play)
    {
        sounds.replace(soundPath,play);
    }
    
    public void process(GameData gameData, Entity entity) {
    }
    
    
    
    
    
}
