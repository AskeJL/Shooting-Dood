package shoot.doode.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author jcs
 */
public class World {
    
    private int TILESIZE;
    private int[][] blockedMap;

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
        System.out.println("Removed entity");
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
        System.out.println("Removed entity");
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    public int getTILESIZE() {
        return TILESIZE;
    }

    public void setTILESIZE(int TILESIZE) {
        this.TILESIZE = TILESIZE;
    }

    public int[][] getBlockedMap() {
        return blockedMap;
    }

    public void setBlockedMap(int[][] blockedMap) {
        this.blockedMap = blockedMap;
    }
    
    
    
    

}
