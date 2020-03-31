package shoot.doode.common.services;

import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;

public interface IGamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
