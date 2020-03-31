package shoot.doode.common.services;

import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
