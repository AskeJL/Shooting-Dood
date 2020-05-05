package shoot.doode.bulletsystem;

import shoot.doode.commonweapon.Bullet;
import shoot.doode.common.data.Entity;
import shoot.doode.common.data.GameData;
import shoot.doode.common.data.World;
import shoot.doode.common.data.entityparts.LifePart;
import shoot.doode.common.data.entityparts.PositionPart;
import shoot.doode.common.data.entityparts.TimerPart;
import shoot.doode.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import shoot.doode.common.data.entityparts.PlayerMovingPart;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class BulletControlSystem implements IEntityProcessingService {

    private Entity bullet;
    private double delta = 0.01;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            //System.out.println("Bullet");
            
            PositionPart position = bullet.getPart(PositionPart.class);
            PlayerMovingPart moving = bullet.getPart(PlayerMovingPart.class);
            TimerPart time = bullet.getPart(TimerPart.class);
            
            //mpb.setUp(true);
            time.reduceExpiration(gameData.getDelta());
            LifePart life = bullet.getPart(LifePart.class);
            //If duration is exceeded, remove the bullet.
            if (time.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
            
            
            //System.out.println("Radians" + ppb.getRotation());
            if(position.getRotation() == 0){
                moving.setD(true);               
            }
            if(Math.abs(position.getRotation() - (float)Math.PI/2) < delta){
                moving.setW(true);              
            }
            if(Math.abs(position.getRotation() - (float)Math.PI) < delta){
                moving.setA(true);               
            }
            if(Math.abs(position.getRotation() - (float)(Math.PI+Math.PI/2)) < delta){
                moving.setS(true);               
            }
            if(Math.abs(position.getRotation() - (float)Math.PI/4 ) < delta){
                moving.setD(true);
                moving.setW(true);              
            }
            if(Math.abs(position.getRotation() - (float)Math.PI*3/4) < delta){
                moving.setW(true);
                moving.setA(true);              
            }
            if(Math.abs(position.getRotation() - (float)(Math.PI+Math.PI/4)) < delta){
                moving.setA(true);
                moving.setS(true);              
            }
            if(Math.abs(position.getRotation() - (float)(Math.PI*2-Math.PI/4)) < delta){
                moving.setS(true);
                moving.setD(true);              
            }
            
           
            position.process(gameData, bullet);
            moving.process(gameData, bullet);
            time.process(gameData, bullet);
            life.process(gameData, bullet);

            updateShape(bullet);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRotation();

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
