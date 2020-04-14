package shoot.doode.collision;

import com.badlogic.gdx.math.Circle;
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
        
        /*for (Entity obstacle : world.getEntities(CollidableEntity.class)) {
            updateShape(obstacle);
        }*/
    }
    
    /*private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        
        shapex[0] = x - 10;
        shapey[0] = y + 10;

        shapex[1] = x + 10;
        shapey[1] = y + 10;

        shapex[2] = x + 10;
        shapey[2] = y + 50;

        shapex[3] = x - 10;
        shapey[3] = y + 50;

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }*/
    
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
        
        Rectangle rec1 = new Rectangle(ep.getX() - (e.getBoundaryWidth() / 2),
                ep.getY() - (e.getBoundaryHeight() / 2),
                e.getBoundaryWidth(),
                e.getBoundaryHeight());
        
        Rectangle rec2 = new Rectangle(fp.getX() - (f.getBoundaryWidth() / 2),
                fp.getY() - (f.getBoundaryHeight() / 2),
                f.getBoundaryWidth(),
                f.getBoundaryHeight());
        
        Rectangle intersection = new Rectangle();
        if(Intersector.intersectRectangles(rec1, rec2, intersection)) {
            if(e.getIsStatic()) {
                return;
            }
            
            if(intersection.x > rec1.x) { //Intersects with right side
                System.out.println("Right side");
                ep.setX(intersection.x - intersection.width - 5);
            }
            if(intersection.y > rec1.y) { //Intersects with top side
                System.out.println("Top side");
                ep.setY(intersection.y - intersection.height - 5);
            }
            if(intersection.x + intersection.width < rec1.x + rec1.width) { //Intersects with left side
                System.out.println("Left side");
                ep.setX(intersection.x + intersection.width + 5);
            }
            if(intersection.y + intersection.height < rec1.y + rec1.height) { //Intersects with bottom side
                System.out.println("Bottom side");
                ep.setY(intersection.y + intersection.height + 5);
            }
        }
    }
}
