package shootin.doode.common.services;

import shootin.doode.common.data.GameData;
import shootin.doode.common.data.World;

public interface IGamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
