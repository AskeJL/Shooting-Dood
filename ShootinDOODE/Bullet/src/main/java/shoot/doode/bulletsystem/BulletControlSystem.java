package shoot.doode.bulletsystem;

import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.MovingPart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.ProjectilePart;
import shoot.doode.common.data.entityparts.ShootingPart;
import shoot.doode.common.data.entityparts.TimerPart;
import shoot.doode.common.services.IEntityProcessingService;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.entityparts.PlayerMovingPart;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class BulletControlSystem implements IEntityProcessingService {

    private Entity bullet;

    @Override
    public void process(GameData gameData, World world) {
        //System.out.println("Bullet Process");
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShootingPart.class) != null) {

                ShootingPart shootingPart = entity.getPart(ShootingPart.class);
                //Shoot if isShooting is true, ie. space is pressed.
                if (shootingPart.isShooting()) {
                    //System.out.println("Bullet ShootingPart.isShooting");
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    //Add entity radius to initial position to avoid immideate collision.
                    bullet = createBullet(entity);
                    
                    PositionPart test = bullet.getPart(PositionPart.class);
                    System.out.println(test.getRadians());
                    
                    shootingPart.setIsShooting(false);
                    world.addEntity(bullet);
                }
            }
        }

        for (Entity b : world.getEntities(Bullet.class)) {
            //System.out.println("Bullet");
            PositionPart ppb = b.getPart(PositionPart.class);
            PlayerMovingPart mpb = b.getPart(PlayerMovingPart.class);
            TimerPart btp = b.getPart(TimerPart.class);
            //mpb.setUp(true);
            btp.reduceExpiration(gameData.getDelta());
            LifePart lpb = b.getPart(LifePart.class);
            //If duration is exceeded, remove the bullet.
            if (btp.getExpiration() < 0) {
                world.removeEntity(b);
            }
            //System.out.println("Radians" + ppb.getRadians());
            if(ppb.getRadians() == 0){
                mpb.setD(true);               
            }
            if(ppb.getRadians() == (float)Math.PI/2){
                mpb.setW(true);
            }
            if(ppb.getRadians() == (float)Math.PI){
                mpb.setA(true);
            }
            if(ppb.getRadians() == (float)Math.PI+(float)Math.PI/2){
                mpb.setS(true);
            }
            if(ppb.getRadians() == (float)Math.PI/4 ){
                mpb.setD(true);
                mpb.setW(true);
            }
            if(ppb.getRadians() == (float)Math.PI*3/4){
                mpb.setW(true);
                mpb.setA(true);
            }
            if(ppb.getRadians() == (float)Math.PI+(float)Math.PI/4){
                mpb.setA(true);
                mpb.setS(true);
            }
            if(ppb.getRadians() == (float)Math.PI*2-(float)Math.PI/4){
                mpb.setS(true);
                mpb.setD(true);
            }
            

            ppb.process(gameData, b);
            mpb.process(gameData, b);
            btp.process(gameData, b);
            lpb.process(gameData, b);

            updateShape(b);
        }
    }

    //Could potentially do some shenanigans with differing colours for differing sources.
    private Entity createBullet(Entity e) {
        PositionPart p = e.getPart(PositionPart.class);
        ShootingPart s = e.getPart(ShootingPart.class);
        Entity b = new Bullet();

        
        b.add(new PositionPart(p.getX(), p.getY(), p.getRadians()));
        System.out.println(p.getRadians());
        b.add(new PlayerMovingPart((float)4.5));
        b.add(new TimerPart(3));
        b.add(new LifePart(1));
        // Projectile Part only used for better collision detection     
        b.add(new ProjectilePart(s.getID()));
        b.setRadius(2);

        float[] colour = new float[4];
        colour[0] = 0.2f;
        colour[1] = 0.5f;
        colour[2] = 0.7f;
        colour[3] = 1.0f;

        b.setColour(colour);
        
        

        //System.out.println(x);
        //System.out.println(y);
        
        return b;
        
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
