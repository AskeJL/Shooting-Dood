package shoot.doode.common.data.entityparts;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;

/**
 *
 * @author Alexander
 */
public interface EntityPart {

    void process(GameData gameData, Entity entity);
}
