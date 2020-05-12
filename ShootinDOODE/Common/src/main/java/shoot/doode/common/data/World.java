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

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
     private final Map<String, Menu> menuMap = new ConcurrentHashMap<>();

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
    public String addMenu(Menu menu) {
        menuMap.put(menu.getID(), menu);
        return menu.getID();
    }

    public void removeMenu(String menuID) {
        menuMap.remove(menuID);
        System.out.println("Removed menu");
    }
    
    public void removeMenu(Menu menu) {
        menuMap.remove(menu.getID());
        System.out.println("Removed menu");
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }
    
     public Collection<Menu> getMenus() {
        return menuMap.values();
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
    
    public <M extends Menu> List<Menu> getMenus(Class<M>... menuTypes) {
        List<Menu> r = new ArrayList<>();
        for (Menu m : getMenus()) {
            for (Class<M> menuType : menuTypes) {
                if (menuType.equals(m.getClass())) {
                    r.add(m);
                }
            }
        }
        return r;
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }
    
    public Menu getMenu(String ID) {
        return menuMap.get(ID);
    }
}
