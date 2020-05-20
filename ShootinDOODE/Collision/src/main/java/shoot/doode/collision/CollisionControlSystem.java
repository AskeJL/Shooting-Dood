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
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.services.IEntityProcessingService;
import shoot.doode.common.services.IPostEntityProcessingService;
import shoot.doode.common.services.IPowerUp;
import shoot.doode.common.services.IScoreGiver;

/**
 *
 * @author sande
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class)})
public class CollisionControlSystem implements IPostEntityProcessingService {

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

                CollidableEntity collidableF = (CollidableEntity) f;
                CollidableEntity collidableE = (CollidableEntity) e;

                //If they have the same toughness continue as they are of the same type or a bullet who got shot from that type
                if (collidableF.getToughness() == collidableE.getToughness()) {
                    continue;
                }

                if (rectangleCollision(collidableF, collidableE)) {
                    boolean fIsBullet = f.getPart(ProjectilePart.class) != null;
                    boolean eIsBullet = e.getPart(ProjectilePart.class) != null;
                    boolean fIsPowerUp = f instanceof IPowerUp;
                    boolean eIsPowerUp = e instanceof IPowerUp;

                    //If both are Bullets ignore
                    if (fIsBullet && eIsBullet) {
                        continue;
                    }

                    //If a power up and the player interacts use the apply power up method
                    //If one entity is a power up and the other is not ignore it
                    if (fIsPowerUp && e.getPart(PlayerMovingPart.class) != null) {
                        IPowerUp fAsPowerUp = (IPowerUp) f;
                        fAsPowerUp.applyPowerUp(e);
                        world.removeEntity(f);
                        continue;
                    } else if (fIsPowerUp) {
                        continue;
                    }

                    if (e.getPart(PlayerMovingPart.class) != null && eIsPowerUp) {
                        IPowerUp eAsPowerUp = (IPowerUp) e;
                        eAsPowerUp.applyPowerUp(f);
                        world.removeEntity(e);
                        continue;
                    } else if (eIsPowerUp) {
                        continue;
                    }

                    // Check if enemy and static collidable interacting
                    if ((collidableE.getIsStatic() && collidableF.getClass().getName().contains("Enemy"))
                            || (collidableF.getIsStatic() && collidableE.getClass().getName().contains("Enemy"))) {
                        continue;
                    }

                    //System.out.println(collidableF.getClass().getName());
                    LifePart lifePartF = f.getPart(LifePart.class);
                    LifePart lifePartE = e.getPart(LifePart.class);

                    boolean removedEntity = false;

                    //If something with a life part gets hit by a bullet take damage = to the bullets damage
                    //Remove the bullet and if the life gets = 0 remove the other entity
                    if (lifePartE != null && fIsBullet) {
                        ProjectilePart bullet = f.getPart(ProjectilePart.class);
                        lifePartE.setLife(lifePartE.getLife() - bullet.getDamage());
                        if (lifePartE.getLife() <= 0) {
                            if (e instanceof IScoreGiver) {
                                IScoreGiver eScoreGiver = (IScoreGiver) e;
                                gameData.setScore(gameData.getScore() + eScoreGiver.getScore());
                            }
                            world.removeEntity(e);
                            removedEntity = true;
                        }
                    }

                    if (lifePartF != null && eIsBullet) {
                        ProjectilePart bullet = e.getPart(ProjectilePart.class);
                        lifePartF.setLife(lifePartF.getLife() - bullet.getDamage());
                        if (lifePartF.getLife() <= 0) {
                            if (f instanceof IScoreGiver) {
                                IScoreGiver fScoreGiver = (IScoreGiver) f;
                                gameData.setScore(gameData.getScore() + fScoreGiver.getScore());
                            }
                            world.removeEntity(f);
                            removedEntity = true;
                        }
                    }

                    //If something with a lifepart hits a non static objekt the one with the lowest toughness takes 1 damage
                    if (lifePartE != null && !collidableF.getIsStatic()) {
                        if (collidableE.getToughness() < collidableF.getToughness()) {
                            lifePartE.setLife(lifePartE.getLife() - 1);
                            if (lifePartE.getLife() <= 0) {
                                world.removeEntity(e);
                                removedEntity = true;
                            }
                        }

                    }

                    if (lifePartF != null && !collidableE.getIsStatic()) {
                         if (collidableF.getToughness() < collidableE.getToughness()) {
                            lifePartF.setLife(lifePartF.getLife() - 1);
                            if (lifePartF.getLife() <= 0) {
                                world.removeEntity(f);
                                removedEntity = true;
                            }
                        }
                    }

                    //If a bullet hits a static objekt remove the bullet
                    if (collidableE.getIsStatic() && fIsBullet) {
                        world.removeEntity(f); //Rebund could be nice 
                        removedEntity = true;
                    }

                    if (collidableF.getIsStatic() && eIsBullet) {
                        world.removeEntity(e); //Rebund could be nice 
                        removedEntity = true;
                    }

                    if (removedEntity) {
                        return;
                    }

                    //If we are still here handle the physical collision
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
                //System.out.println(intersection.getHeight() + intersection.getWidth());
                if (intersection.getY() == ep.getY()) {
                    ep.setY(fp.getY() + (f.getBoundaryHeight() / 2) + (e.getBoundaryHeight() / 2)); // ep.setY(intersection.getY() + intersection.getHeight() - 15);
                }
                if (intersection.getY() > ep.getY()) {
                    ep.setY(fp.getY() - (f.getBoundaryHeight() / 2)); // ep.setY(intersection.getY() - e.getBoundaryHeight() - 2);
                }
            } else if (intersection.getWidth() < intersection.getHeight()) {
                //System.out.println(intersection.getHeight() + intersection.getWidth());
                if (intersection.getX() == ep.getX()) {
                    ep.setX(fp.getX() + (f.getBoundaryWidth() / 2) + (e.getBoundaryWidth() / 2)); // ep.setX(intersection.getX() + intersection.getWidth() - 15);
                }
                if (intersection.getX() > ep.getX()) {
                    ep.setX(fp.getX() - (f.getBoundaryWidth() / 2)); //ep.setX(intersection.getX() - e.getBoundaryWidth() - 2);
                }
            }
        }
    }
}
