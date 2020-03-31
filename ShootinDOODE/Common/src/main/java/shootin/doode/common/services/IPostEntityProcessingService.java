package shootin.doode.common.services;

import shootin.doode.common.data.GameData;
import shootin.doode.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}
