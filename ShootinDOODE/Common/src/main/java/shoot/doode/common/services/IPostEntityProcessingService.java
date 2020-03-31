package shoot.doode.common.services;

import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}
