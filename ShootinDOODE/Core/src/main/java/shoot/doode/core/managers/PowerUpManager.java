package shoot.doode.core.managers;

import java.util.Collection;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.services.IPowerUp;

public class PowerUpManager {
    
    private static PowerUpManager single_instance = null;
    private double timer = 0;
    private PowerUpManager()
    {
        
    }
    
    public static PowerUpManager getInstance()
    {
        if(single_instance == null)
        {
             single_instance = new PowerUpManager();
        }
        return single_instance;
    }
    
    public void process(GameData gameData,World world ,Collection<? extends IPowerUp> IPowerUps)
    {
        int amount = IPowerUps.size();
        timer += gameData.getDelta();
        if(timer > 5)
        {
            int random = (int)(Math.random() * (amount+1));
            int j =0;
            for(IPowerUp i : IPowerUps)
            {                
                i.removePowerUps(gameData, world);
                if(j == random)
                {
                    i.spawnPowerUp(gameData, world);
                }
                j++;
            }
            timer = 0;
            
        }
    }
    
}
