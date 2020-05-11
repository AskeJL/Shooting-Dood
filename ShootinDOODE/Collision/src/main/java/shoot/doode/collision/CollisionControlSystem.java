package shoot.doode.collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.CollidableEntity;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PlayerMovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ProjectilePart;
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
            if (!CollidableEntity.class.isAssignableFrom(e.getClass())) {
                continue;
            }

            for (Entity f : world.getEntities()) {
                // Get only Collidable entities
                if (!CollidableEntity.class.isAssignableFrom(f.getClass())) {
                    continue;
                }

                if (e.getID().equals(f.getID())) {
                    continue;
                }
                
                CollidableEntity collidableF = (CollidableEntity)f;
                CollidableEntity collidableE = (CollidableEntity)e;

                if (rectangleCollision(collidableF, collidableE)) {
                    boolean fIsBullet = f.getPart(ProjectilePart.class) != null;
                    boolean eIsBullet = e.getPart(ProjectilePart.class) != null;
                    
                    // Check if player and bullet interacting
                    if ((fIsBullet && e.getPart(PlayerMovingPart.class) != null) ||
                        (f.getPart(PlayerMovingPart.class) != null && eIsBullet)) {
                        continue;
                    }
                    
                    // Check if enemy and static collidable interacting
                    if ((collidableE.getIsStatic() && collidableF.getClass().getName().contains("Enemy")) ||
                        (collidableF.getIsStatic() && collidableE.getClass().getName().contains("Enemy"))) {
                        continue;
                    }
                    
                    System.out.println(collidableF.getClass().getName());
                    
                    LifePart lifePartF = f.getPart(LifePart.class);
                    LifePart lifePartE = e.getPart(LifePart.class);
                    
                    boolean removedEntity = false;
                    if (lifePartF != null && (!collidableE.getIsStatic() || fIsBullet)) {
                        ProjectilePart bullet = f.getPart(ProjectilePart.class);
                        lifePartF.setLife(lifePartF.getLife() - bullet.getDamage());
                        if (lifePartF.getLife() <= 0) {
                            world.removeEntity(f);
                            removedEntity = true;
                        }
                    }
                    if (lifePartE != null && (!collidableF.getIsStatic() || eIsBullet)) {
                        ProjectilePart bullet = e.getPart(ProjectilePart.class);
                        lifePartE.setLife(lifePartE.getLife() - bullet.getDamage());
                        if (lifePartE.getLife() <= 0) {
                            world.removeEntity(e);
                            removedEntity = true;
                        }
                    }
                    
                    if (removedEntity) {
                        return;
                    }
                    
                    handleCollisionOverlap((CollidableEntity) f, (CollidableEntity) e);
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
        if (Intersector.intersectRectangles(rec1, rec2, intersection)) {
            if (e.getIsStatic()) {
                return;
            }

            if (intersection.getHeight() < intersection.getWidth()) {
                if (intersection.getY() == ep.getY()) {
                    ep.setY(intersection.getY() + intersection.getHeight());
                }
                if (intersection.getY() > ep.getY()) {
                    ep.setY(intersection.getY() - e.getBoundaryHeight());
                }
            } else if (intersection.getWidth() < intersection.getHeight()) {
                if (intersection.getX() == ep.getX()) {
                    ep.setX(intersection.getX() + intersection.getWidth());
                }
                if (intersection.getX() > ep.getX()) {
                    ep.setX(intersection.getX() - e.getBoundaryWidth());
                }
            }
        }
    }
}
