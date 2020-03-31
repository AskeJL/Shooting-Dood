package shootin.doode.common.services;

import shootin.doode.common.data.GameData;
import shootin.doode.common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
