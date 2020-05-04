package shoot.doode.collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.services.IEntityProcessingService;

/**
 *
 * @author sande
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class CollisionControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            // Get only Collidable entities
            if(!CollidableEntity.class.isAssignableFrom(e.getClass())) {
                continue;
            }
            
            for (Entity f : world.getEntities()) {
                // Get only Collidable entities
                if(!CollidableEntity.class.isAssignableFrom(f.getClass())) {
                    continue;
                }
                
                if (e.getID().equals(f.getID())) {
                    continue;
                }
                
                if(rectangleCollision((CollidableEntity)f, (CollidableEntity)e)) {
                    handleCollisionOverlap((CollidableEntity)f, (CollidableEntity)e);
                }
            }
        }
    }
    
    private boolean rectangleCollision(CollidableEntity e, CollidableEntity f) {
        PositionPart ep = e.getPart(PositionPart.class);
        PositionPart fp = f.getPart(PositionPart.class);
        
        Rectangle rec1 = new Rectangle(ep.getX() - (e.getBoundaryWidth() / 2),
                ep.getY() - (e.getBoundaryHeight() / 2),
                e.getBoundaryWidth(),
                e.getBoundaryHeight());
        
        Rectangle rec2 = new Rectangle(fp.getX() - (f.getBoundaryWidth() / 2),
                fp.getY() - (f.getBoundaryHeight() / 2),
                f.getBoundaryWidth(),
                f.getBoundaryHeight());
        
        return Intersector.overlaps(rec1, rec2);
    }
    
    private void handleCollisionOverlap(CollidableEntity e, CollidableEntity f) {
        PositionPart ep = e.getPart(PositionPart.class);
        PositionPart fp = f.getPart(PositionPart.class);
        
        Rectangle rec1 = new Rectangle(ep.getX(),
                ep.getY(),
                e.getBoundaryWidth(),
                e.getBoundaryHeight());
        
        Rectangle rec2 = new Rectangle(fp.getX(),
                fp.getY(),
                f.getBoundaryWidth(),
                f.getBoundaryHeight());
        
        Rectangle intersection = new Rectangle();
        if(Intersector.intersectRectangles(rec1, rec2, intersection)) {
            if(e.getIsStatic()) {
                return;
            }
            
            if(intersection.getHeight() < intersection.getWidth()) {
                if(intersection.getY() == ep.getY()) {
                    ep.setY(intersection.getY() + intersection.getHeight());
                    System.out.println("Collision");
                }
                if(intersection.getY() > ep.getY()) {
                    ep.setY(intersection.getY() - e.getBoundaryHeight());
                    System.out.println("Collision1");
                }
            }
            else if(intersection.getWidth() < intersection.getHeight()) {
                if(intersection.getX() == ep.getX()) {
                    ep.setX(intersection.getX() + intersection.getWidth());
                    System.out.println("Collision2");
                }
                if(intersection.getX() > ep.getX()) {
                    ep.setX(intersection.getX() - e.getBoundaryWidth());
                    System.out.println("Collision3");
                }
            }
        }
    }
}
